package com.vktechnologies.democontact.application.persona.usecase;

import org.springframework.stereotype.Component;

import com.vktechnologies.democontact.application.persona.service.PersonaService;
import com.vktechnologies.democontact.infraestructure.dto.CreatePersonaDTO;
import com.vktechnologies.democontact.infraestructure.models.PersonaModel;

/**
 * @author Ing. Victor Hdez. A <victor.hdezalvarez@gmail.com>
 */
@Component
public class UpdatePersonaUC {
	
	private final PersonaService personaService;

	public UpdatePersonaUC(PersonaService personaService)
	{
		this.personaService = personaService;
	}
	
	public PersonaModel execute(Long personaId, CreatePersonaDTO updatePersonaDTO)
	{
		PersonaModel personaModel = this.personaService.findPersona(personaId);
		return this.personaService.update(personaModel, updatePersonaDTO);
	}
}
