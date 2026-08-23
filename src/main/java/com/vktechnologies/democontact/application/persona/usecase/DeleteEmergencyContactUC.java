package com.vktechnologies.democontact.application.persona.usecase;

import java.util.List;

import org.springframework.stereotype.Component;

import com.vktechnologies.democontact.application.persona.service.PersonaService;
import com.vktechnologies.democontact.infraestructure.models.PersonaModel;

@Component
public class DeleteEmergencyContactUC
{
	private final PersonaService personaService;
	
	public DeleteEmergencyContactUC(PersonaService personaService)
	{
		this.personaService = personaService;
	}
	
	public PersonaModel execute(Long personaId, Long emergencyContactId)
	{
		PersonaModel persona = this.personaService.findPersona(personaId);
		PersonaModel emergencyContact = this.personaService.findPersona(emergencyContactId);
		
		return this.personaService.deleteEmergencyContact(persona, emergencyContact);
	}

}
