package com.vktechnologies.democontact.application.persona.usecase;

import org.springframework.stereotype.Component;

import com.vktechnologies.democontact.application.persona.service.PersonaService;
import com.vktechnologies.democontact.infraestructure.models.PersonaModel;

@Component
public class GetPersonaUC
{
	private final PersonaService personaService;
	
	public GetPersonaUC(PersonaService personaService)
	{
		this.personaService = personaService;
	}
	
	public PersonaModel execute(Long personaId)
	{
		return this.personaService.findPersona(personaId);
	}
}
