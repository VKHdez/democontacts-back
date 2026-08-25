package com.vktechnologies.democontact.application.persona.usecase;

import org.springframework.stereotype.Component;

import com.vktechnologies.democontact.application.persona.service.PersonaService;

@Component
public class AddAddressUC
{
	private final PersonaService personaService;
	
	public AddAddressUC(PersonaService personaService)
	{
		this.personaService = personaService;
	}

}
