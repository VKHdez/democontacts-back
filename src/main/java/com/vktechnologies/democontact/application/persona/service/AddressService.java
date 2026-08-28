package com.vktechnologies.democontact.application.persona.service;

import org.springframework.stereotype.Service;

import com.vktechnologies.democontact.domain.address.exception.AddressNotFoundException;
import com.vktechnologies.democontact.domain.address.exception.InvalidAddressTypeException;
import com.vktechnologies.democontact.infraestructure.dto.CreateAddressDTO;
import com.vktechnologies.democontact.infraestructure.dto.UpdateAddressDTO;
import com.vktechnologies.democontact.infraestructure.models.AddressModel;
import com.vktechnologies.democontact.infraestructure.models.PersonaModel;
import com.vktechnologies.democontact.infraestructure.persistence.AddressRepository;

/**
 * @author Ing. Victor Hdez. A <victor.hdezalvarez@gmail.com>
 */
@Service
public class AddressService {

	private final AddressRepository addressRepository;

	public AddressService(AddressRepository addressRepository)
	{
		this.addressRepository = addressRepository;
	}

	public AddressModel create(CreateAddressDTO dto, PersonaModel persona)
	{
		AddressModel address = new AddressModel();
		address.setPersona(persona);
		address.setStreet(dto.street());
		address.setExternalNumber(dto.externalNumber());
		address.setInteriorNumber(dto.interiorNumber());
		address.setCountry(dto.country());
		address.setState(dto.state());
		address.setCity(dto.city());
		address.setPostalCode(dto.postalCode());
		address.setNormal(dto.isNormal());
		address.setBilling(dto.isBilling());

		return addressRepository.save(address);
	}

	public AddressModel findAddress(Long addressId)
	{
		return addressRepository.findById(addressId)
				.orElseThrow(() -> new AddressNotFoundException(addressId));
	}

	public void validateBelongsToPersona(AddressModel address, PersonaModel persona)
	{
		if (!address.getPersona().getId().equals(persona.getId()))
			throw new AddressNotFoundException(address.getId(), persona.getId());
	}

	public AddressModel update(AddressModel oldAddress, UpdateAddressDTO dto)
	{
		if (dto.street() != null && !dto.street().isEmpty())
			oldAddress.setStreet(dto.street());

		if (dto.externalNumber() != null && !dto.externalNumber().isEmpty())
			oldAddress.setExternalNumber(dto.externalNumber());

		if (dto.interiorNumber() != null && !dto.interiorNumber().isEmpty())
			oldAddress.setInteriorNumber(dto.interiorNumber());

		if (dto.country() != null && !dto.country().isEmpty())
			oldAddress.setCountry(dto.country());

		if (dto.state() != null && !dto.state().isEmpty())
			oldAddress.setState(dto.state());

		if (dto.city() != null && !dto.city().isEmpty())
			oldAddress.setCity(dto.city());

		if (dto.postalCode() != null && !dto.postalCode().isEmpty())
			oldAddress.setPostalCode(dto.postalCode());

		if (dto.isNormal() != null)
			oldAddress.setNormal(dto.isNormal());

		if (dto.isBilling() != null)
			oldAddress.setBilling(dto.isBilling());

		if (!oldAddress.isNormal() && !oldAddress.isBilling())
			throw new InvalidAddressTypeException(oldAddress.getId());

		return addressRepository.save(oldAddress);
	}

	public void delete(AddressModel address)
	{
		addressRepository.delete(address);
	}

	public void disableAllByPersona(PersonaModel persona)
	{
		addressRepository.findByPersonaId(persona.getId())
			.forEach(addressRepository::delete);
	}

	public void enableAllByPersona(Long personaId)
	{
		addressRepository.activateByPersonaId(personaId);
	}
}
