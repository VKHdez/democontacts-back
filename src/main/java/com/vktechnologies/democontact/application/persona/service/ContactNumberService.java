package com.vktechnologies.democontact.application.persona.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.vktechnologies.democontact.domain.contactnumber.exception.ContactNumberNotFoundException;
import com.vktechnologies.democontact.domain.contactnumber.exception.CountryCodeNotFoundException;
import com.vktechnologies.democontact.domain.contactnumber.exception.PhoneNumberTypeNotFoundException;
import com.vktechnologies.democontact.infraestructure.dto.CreateContactNumberDTO;
import com.vktechnologies.democontact.infraestructure.dto.UpdateContactNumberDTO;
import com.vktechnologies.democontact.infraestructure.models.ContactNumberModel;
import com.vktechnologies.democontact.infraestructure.models.CountryCodeModel;
import com.vktechnologies.democontact.infraestructure.models.PersonaModel;
import com.vktechnologies.democontact.infraestructure.models.PhoneNumberTypeModel;
import com.vktechnologies.democontact.infraestructure.persistence.ContactNumberRepository;
import com.vktechnologies.democontact.infraestructure.persistence.CountryCodeRepository;
import com.vktechnologies.democontact.infraestructure.persistence.PhoneNumberTypeRepository;

/**
 * @author Ing. Victor Hdez. A <victor.hdezalvarez@gmail.com>
 */
@Service
public class ContactNumberService {

	private static final String NO_TYPE_NAME = "SIN TIPO";
	private static final Logger logger = LoggerFactory.getLogger(ContactNumberService.class);

	private final ContactNumberRepository contactNumberRepository;
	private final CountryCodeRepository countryCodeRepository;
	private final PhoneNumberTypeRepository phoneNumberTypeRepository;

	public ContactNumberService(
		ContactNumberRepository contactNumberRepository,
		CountryCodeRepository countryCodeRepository,
		PhoneNumberTypeRepository phoneNumberTypeRepository
	)
	{
		this.contactNumberRepository = contactNumberRepository;
		this.countryCodeRepository = countryCodeRepository;
		this.phoneNumberTypeRepository = phoneNumberTypeRepository;
	}

	public ContactNumberModel create(CreateContactNumberDTO dto, PersonaModel persona)
	{
		// NOTA: number+countryCode son unicos globalmente (ver entities.md), por lo que un
		// registro previamente soft-borrado se reactiva en vez de intentar un INSERT duplicado.
		int existsDeleted = contactNumberRepository.existsDeletedContactNumber(dto.countryCodeId(), dto.number());
		if (existsDeleted == 1)
			return reactivate(dto, persona);

		CountryCodeModel countryCode = countryCodeRepository.findById(dto.countryCodeId())
				.orElseThrow(() -> new CountryCodeNotFoundException(dto.countryCodeId()));

		List<PhoneNumberTypeModel> phoneNumberTypes = resolvePhoneNumberTypes(dto.phoneNumberTypeIds());

		ContactNumberModel contactNumber = new ContactNumberModel();
		contactNumber.setPersona(persona);
		contactNumber.setCountryCode(countryCode);
		contactNumber.setNumber(dto.number());
		contactNumber.setPhoneNumberTypes(phoneNumberTypes);

		return contactNumberRepository.save(contactNumber);
	}

	private ContactNumberModel reactivate(CreateContactNumberDTO dto, PersonaModel persona)
	{
		contactNumberRepository.reactivateContactNumber(dto.countryCodeId(), dto.number(), persona.getId());

		ContactNumberModel contactNumber = contactNumberRepository.findByCountryCodeIdAndNumber(dto.countryCodeId(), dto.number())
				.orElseThrow(() -> new ContactNumberNotFoundException(dto.countryCodeId(), dto.number()));

		contactNumber = reconcilePhoneNumberTypes(contactNumber.getId(), dto.phoneNumberTypeIds());

		return contactNumberRepository.save(contactNumber);
	}

	// Reconcilia phoneNumberTypes via SQL nativo, nunca tocando la coleccion @ManyToMany en Java
	// (es un Bag: cualquier mutacion la recrea completa y choca con el soft-delete). Usado por reactivate() y update().
	private ContactNumberModel reconcilePhoneNumberTypes(Long contactNumberId, List<Long> phoneNumberTypeIds)
	{
		logger.info("--- [reconcilePhoneNumberTypes] incoming phoneNumberTypeIds: "+phoneNumberTypeIds+" ----");

		List<Long> requestedTypeIds = resolvePhoneNumberTypes(phoneNumberTypeIds).stream()
				.map(PhoneNumberTypeModel::getId)
				.toList();
		logger.info("--- [reconcilePhoneNumberTypes] resolved phoneNumberType ids: "+requestedTypeIds+" ----");

		contactNumberRepository.reactivatePhoneTypes(contactNumberId, requestedTypeIds);
		contactNumberRepository.deactivateOtherPhoneTypes(contactNumberId, requestedTypeIds);
		contactNumberRepository.insertMissingPhoneTypes(contactNumberId, requestedTypeIds);

		logger.info("--- [reconcilePhoneNumberTypes] searching contact number id: "+contactNumberId+" ----");
		ContactNumberModel contactNumber = contactNumberRepository.findById(contactNumberId)
				.orElseThrow(() -> new ContactNumberNotFoundException(contactNumberId));

		logger.info("--- [reconcilePhoneNumberTypes] current phoneNumberTypes on contact number "+contactNumberId+": "
				+contactNumber.getPhoneNumberTypes().stream().map(PhoneNumberTypeModel::getId).toList()+" ----");

		return contactNumber;
	}


	// Siempre retorna ArrayList mutable, nunca List.of()/toList() -- Hibernate necesita clear() sobre esto en el merge del @ManyToMany.
	private List<PhoneNumberTypeModel> resolvePhoneNumberTypes(List<Long> phoneNumberTypeIds)
	{
		if (phoneNumberTypeIds.isEmpty()){
			List<PhoneNumberTypeModel> noType = new ArrayList<>();
			noType.add(findNoTypePhoneNumberType());
			return noType;
		}

		return phoneNumberTypeIds.stream()
				.map(
					phoneNumberTypeId -> phoneNumberTypeRepository.findById(phoneNumberTypeId)
						.orElseThrow(() -> new PhoneNumberTypeNotFoundException(phoneNumberTypeId))
				)
				.collect(Collectors.toCollection(ArrayList::new));
	}

	// "SIN TIPO" es un valor de catalogo sembrado (V19); si falta en la DB es un problema de datos, no de usuario.
	private PhoneNumberTypeModel findNoTypePhoneNumberType()
	{
		return phoneNumberTypeRepository.findByName(NO_TYPE_NAME)
				.orElseThrow(() -> new IllegalStateException("Seed catalog value '"+NO_TYPE_NAME+"' is missing from phone_number_types"));
	}

	public ContactNumberModel findContactNumber(Long contactNumberId)
	{
		return contactNumberRepository.findById(contactNumberId)
				.orElseThrow(() -> new ContactNumberNotFoundException(contactNumberId));
	}

	public void validateBelongsToPersona(ContactNumberModel contactNumber, PersonaModel persona)
	{
		if (!contactNumber.getPersona().getId().equals(persona.getId()))
			throw new ContactNumberNotFoundException(contactNumber.getId(), persona.getId());
	}

	public ContactNumberModel update(ContactNumberModel oldContactNumber, UpdateContactNumberDTO dto)
	{
		ContactNumberModel contactNumber = dto.phoneNumberTypeIds() != null
			? reconcilePhoneNumberTypes(oldContactNumber.getId(), dto.phoneNumberTypeIds())
			: oldContactNumber;

		if (dto.countryCodeId() != null){
			CountryCodeModel countryCode = countryCodeRepository.findById(dto.countryCodeId())
					.orElseThrow(() -> new CountryCodeNotFoundException(dto.countryCodeId()));

			contactNumber.setCountryCode(countryCode);
		}

		if (dto.number() != null && !dto.number().isEmpty())
			contactNumber.setNumber(dto.number());

		return contactNumberRepository.save(contactNumber);
	}
}
