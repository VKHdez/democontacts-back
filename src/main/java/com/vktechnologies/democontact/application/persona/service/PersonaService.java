package com.vktechnologies.democontact.application.persona.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.vktechnologies.democontact.domain.persona.exception.PersonaNotFoundException;
import com.vktechnologies.democontact.domain.persona.exception.SelfEmergencyContactException;
import com.vktechnologies.democontact.infraestructure.dto.CreatePersonaDTO;
import com.vktechnologies.democontact.infraestructure.models.GenderModel;
import com.vktechnologies.democontact.infraestructure.models.PersonaModel;
import com.vktechnologies.democontact.infraestructure.models.UserModel;
import com.vktechnologies.democontact.infraestructure.persistence.GenderRepository;
import com.vktechnologies.democontact.infraestructure.persistence.PersonaRepository;
import com.vktechnologies.democontact.infraestructure.persistence.UserRepository;

/**
 * @author Ing. Victor Hdez. A <victor.hdezalvarez@gmail.com>
 */
@Service
public class PersonaService {

	private final PersonaRepository personaRepository;
	private final UserRepository userRepository;
	private final GenderRepository genderRepository;
	private final AddressService addressService;
	private final ContactNumberService contactNumberService;

	public PersonaService(
		PersonaRepository personaRepository,
		UserRepository userRepository,
		GenderRepository genderRepository,
		AddressService addressService,
		ContactNumberService contactNumberService
	)
	{
		this.personaRepository = personaRepository;
		this.genderRepository = genderRepository;
		this.userRepository = userRepository;
		this.addressService = addressService;
		this.contactNumberService = contactNumberService;
	}
	
	public PersonaModel findPersona(Long personaId)
	{
		return personaRepository.findById(personaId)
				.orElseThrow(() -> new PersonaNotFoundException( personaId ) );
	}

	/**
	 * NOTA: no valida que la persona exista, retorna un proxy de referencia (getReferenceById).
	 * Si el personaId no existe, el error se manifestará hasta el flush/commit (FK violation).
	 */
	public PersonaModel getReference(Long personaId)
	{
		return personaRepository.getReferenceById(personaId);
	}

	public PersonaModel create(CreatePersonaDTO dto)
	{
		GenderModel gender = genderRepository.getReferenceById(dto.genderId());

		PersonaModel persona = new PersonaModel();
		persona.setName(dto.name());
		persona.setFirstName(dto.firstName());
		persona.setLastName(dto.lastName());
		persona.setGender(gender);
		persona.setBirthDate(dto.birthDate());

		return personaRepository.save(persona);
	}
	
	public PersonaModel update(PersonaModel oldPersona, CreatePersonaDTO dto)
	{
		// 1.- Load gender catalog
		GenderModel gender = genderRepository.getReferenceById(dto.genderId());
		// 2.- Validate fields to be updated
		
		if(dto.name() != null && !dto.name().isEmpty() )
			oldPersona.setName(dto.name()); 
		
		if(dto.firstName() != null && !dto.firstName().isEmpty() )
			oldPersona.setFirstName(dto.firstName());
		
		if(dto.lastName() != null && !dto.lastName().isEmpty() )
			oldPersona.setLastName(dto.lastName());
		
		if(dto.birthDate() != null )
			oldPersona.setBirthDate(dto.birthDate());
		
		if(dto.genderId() != null && dto.genderId() > 0)
			oldPersona.setGender(gender);
		
		return personaRepository.save(oldPersona);
	}
	
	public void disable(Long personaId)
	{
		PersonaModel personaModel = personaRepository.findById(personaId)
			.orElseThrow(() -> new PersonaNotFoundException( personaId ) );
		personaRepository.delete(personaModel);
	}

	public PersonaModel activate(Long personaId)
	{
		int updated = personaRepository.activateById(personaId);

		if (updated == 0)
			throw new PersonaNotFoundException(personaId);

		return findPersona(personaId);
	}

	public void disableEmergencyContacts(Long personaId)
	{
		personaRepository.disableEmergencyContactsByPersonaId(personaId);
	}

	// Deshabilita la persona junto con emergency contacts, addresses y contact numbers
	public void disableWithRelatedContent(Long personaId)
	{
		PersonaModel persona = findPersona(personaId);

		disableEmergencyContacts(personaId);
		addressService.disableAllByPersona(persona);
		contactNumberService.disableAllByPersona(persona);
		disable(personaId);
	}

	public void activateEmergencyContacts(Long personaId)
	{
		personaRepository.activateEmergencyContactsByPersonaId(personaId);
	}

	// Contraparte de disableWithRelatedContent: reactiva persona, emergency contacts, addresses y contact numbers
	public PersonaModel activateWithRelatedContent(Long personaId)
	{
		PersonaModel persona = activate(personaId);

		activateEmergencyContacts(personaId);
		addressService.enableAllByPersona(personaId);
		contactNumberService.enableAllByPersona(personaId);

		return persona;
	}
	
	/**
	 * 
	 * @param personaModel
	 * @param emergencyContactId
	 * @return boolean True: if the emergency contact is already assigned, false otherwise
	 */
	public boolean emergencyContactIsAssigned(PersonaModel personaModel, PersonaModel emergencyContact)
	{
		return personaModel.getEmergencyContacts().contains(emergencyContact);
	}
	
	/**
	 * 
	 * @param personaModel
	 * @param emergencyContact
	 * @return PersonaModel related contacts updated
	 */
	public PersonaModel addEmergencyContact(PersonaModel personaModel, PersonaModel emergencyContact)
	{
		if( personaModel.getId().equals(emergencyContact.getId()))
			throw new SelfEmergencyContactException(personaModel.getId());

		if( this.emergencyContactIsAssigned(personaModel, emergencyContact))
			return personaModel;
		
		// Spring 
		int existsDeletedRecord = personaRepository.existsDeletedEmergencyContact(personaModel.getId(), emergencyContact.getId());
		if(existsDeletedRecord == 1) {
			personaRepository.reactivateEmergencyContact(personaModel.getId(), emergencyContact.getId());
			return this.findPersona(personaModel.getId());
		}
		
		personaModel.getEmergencyContacts().add(emergencyContact);
		return personaRepository.save(personaModel);
	}
	
	/**
	 * 
	 * @param personaModel
	 * @param emergencyContact
	 * @return
	 */
	public PersonaModel deleteEmergencyContact(PersonaModel personaModel, PersonaModel emergencyContact)
	{
		if( !this.emergencyContactIsAssigned(personaModel, emergencyContact))
			return personaModel;
		
		personaModel.getEmergencyContacts().remove(emergencyContact);
		return personaRepository.save(personaModel);
	}
}
