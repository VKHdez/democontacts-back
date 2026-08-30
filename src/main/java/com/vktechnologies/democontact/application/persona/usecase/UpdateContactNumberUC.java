package com.vktechnologies.democontact.application.persona.usecase;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.vktechnologies.democontact.application.persona.service.ContactNumberService;
import com.vktechnologies.democontact.application.persona.service.PersonaService;
import com.vktechnologies.democontact.infraestructure.dto.UpdateContactNumberDTO;
import com.vktechnologies.democontact.infraestructure.models.ContactNumberModel;
import com.vktechnologies.democontact.infraestructure.models.PersonaModel;

@Component
public class UpdateContactNumberUC
{
	private final PersonaService personaService;
	private final ContactNumberService contactNumberService;

	public UpdateContactNumberUC(PersonaService personaService, ContactNumberService contactNumberService)
	{
		this.personaService = personaService;
		this.contactNumberService = contactNumberService;
	}

	@Transactional
	public ContactNumberModel execute(Long personaId, Long contactNumberId, UpdateContactNumberDTO dto)
	{
		PersonaModel persona = personaService.findPersona(personaId);

		ContactNumberModel contactNumber = contactNumberService.findContactNumber(contactNumberId);
		contactNumberService.validateBelongsToPersona(contactNumber, persona);

		return contactNumberService.update(contactNumber, dto);
	}
}
