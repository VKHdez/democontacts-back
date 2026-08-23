package com.vktechnologies.democontact.application.persona.usecase;

import org.springframework.stereotype.Component;

import com.vktechnologies.democontact.application.persona.service.PersonaService;
import com.vktechnologies.democontact.application.user.service.UserService;
import com.vktechnologies.democontact.infraestructure.models.PersonaModel;

import jakarta.transaction.Transactional;

@Component
public class ActivatePersonaUC
{
	private final PersonaService personaService;
	private final UserService userService;

	public ActivatePersonaUC(PersonaService personaService, UserService userService)
	{
		this.personaService = personaService;
		this.userService = userService;
	}

	@Transactional
	public PersonaModel execute(Long personaId)
	{
		PersonaModel persona = personaService.activate(personaId);

		// A persona is not required to have a user, so this is a no-op when none exists
		userService.activateByPersona(personaId);

		return persona;
	}
}
