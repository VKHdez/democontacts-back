package com.vktechnologies.democontact.application.persona.usecase;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.vktechnologies.democontact.application.persona.service.ContactNumberService;
import com.vktechnologies.democontact.application.persona.service.PersonaService;
import com.vktechnologies.democontact.infraestructure.models.ContactNumberModel;
import com.vktechnologies.democontact.infraestructure.models.PersonaModel;

@Component
public class DeleteContactNumberUC
{
	private final PersonaService personaService;
	private final ContactNumberService contactNumberService;

	public DeleteContactNumberUC(PersonaService personaService, ContactNumberService contactNumberService)
	{
		this.personaService = personaService;
		this.contactNumberService = contactNumberService;
	}

	@Transactional
	public void execute(Long personaId, Long contactNumberId)
	{
		PersonaModel persona = personaService.findPersona(personaId);
		ContactNumberModel contactNumber = contactNumberService.findContactNumber(contactNumberId);
		contactNumberService.validateBelongsToPersona(contactNumber, persona);

		contactNumberService.disable(contactNumber);
	}
}
