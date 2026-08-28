package com.vktechnologies.democontact.application.persona.usecase;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.vktechnologies.democontact.application.persona.service.AddressService;
import com.vktechnologies.democontact.application.persona.service.PersonaService;
import com.vktechnologies.democontact.infraestructure.dto.UpdateAddressDTO;
import com.vktechnologies.democontact.infraestructure.models.AddressModel;
import com.vktechnologies.democontact.infraestructure.models.PersonaModel;

@Component
public class UpdateAddressUC
{
	private final PersonaService personaService;
	private final AddressService addressService;

	public UpdateAddressUC(PersonaService personaService, AddressService addressService)
	{
		this.personaService = personaService;
		this.addressService = addressService;
	}

	@Transactional
	public AddressModel execute(Long personaId, Long addressId, UpdateAddressDTO dto)
	{
		PersonaModel persona = personaService.findPersona(personaId);
		AddressModel address = addressService.findAddress(addressId);
		addressService.validateBelongsToPersona(address, persona);

		return addressService.update(address, dto);
	}
}
