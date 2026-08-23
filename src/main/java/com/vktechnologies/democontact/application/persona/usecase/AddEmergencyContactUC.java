package com.vktechnologies.democontact.application.persona.usecase;

import java.util.List;

import org.springframework.stereotype.Component;

import com.vktechnologies.democontact.application.persona.service.PersonaService;
import com.vktechnologies.democontact.infraestructure.models.PersonaModel;

import jakarta.transaction.Transactional;

@Component
public class AddEmergencyContactUC
{
	private final PersonaService personaService;
	
	public AddEmergencyContactUC(PersonaService personaService)
	{
		this.personaService = personaService;
	}
	
	@Transactional
	public PersonaModel execute(Long personaId, Long emergencyContactId)
	{
		PersonaModel persona = this.personaService.findPersona(personaId);
		PersonaModel emergencyContact = this.personaService.findPersona(emergencyContactId);
		
		return this.personaService.addEmergencyContact(persona, emergencyContact);
	}

}
