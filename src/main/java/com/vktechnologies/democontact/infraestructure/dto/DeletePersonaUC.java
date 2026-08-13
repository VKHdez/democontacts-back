package com.vktechnologies.democontact.infraestructure.dto;

import org.springframework.stereotype.Component;

import com.vktechnologies.democontact.application.persona.service.PersonaService;

@Component
public class DeletePersonaUC
{
	private final PersonaService personaService;
	
	public DeletePersonaUC(PersonaService personaService)
	{
		this.personaService = personaService;
	}
	
	public void execute(Long personaId)
	{
		personaService.disable(personaId);
	}
}
