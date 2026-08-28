package com.vktechnologies.democontact.application.persona.usecase;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.vktechnologies.democontact.application.persona.service.AddressService;
import com.vktechnologies.democontact.application.persona.service.PersonaService;
import com.vktechnologies.democontact.infraestructure.models.AddressModel;
import com.vktechnologies.democontact.infraestructure.models.PersonaModel;

@Component
public class DeleteAddressUC
{
	private final PersonaService personaService;
	private final AddressService addressService;

	public DeleteAddressUC(PersonaService personaService, AddressService addressService)
	{
		this.personaService = personaService;
		this.addressService = addressService;
	}

	@Transactional
	public void execute(Long personaId, Long addressId)
	{
		PersonaModel persona = personaService.findPersona(personaId);
		AddressModel address = addressService.findAddress(addressId);
		addressService.validateBelongsToPersona(address, persona);

		addressService.delete(address);
	}
}
