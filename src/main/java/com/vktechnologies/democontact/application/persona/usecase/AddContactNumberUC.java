package com.vktechnologies.democontact.application.persona.usecase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.vktechnologies.democontact.application.persona.service.ContactNumberService;
import com.vktechnologies.democontact.application.persona.service.PersonaService;
import com.vktechnologies.democontact.infraestructure.dto.CreateContactNumberDTO;
import com.vktechnologies.democontact.infraestructure.models.ContactNumberModel;
import com.vktechnologies.democontact.infraestructure.models.PersonaModel;

@Component
public class AddContactNumberUC
{
	private final PersonaService personaService;
	private final ContactNumberService contactNumberService;

	private static final Logger logger = LoggerFactory.getLogger(AddContactNumberUC.class);

	public AddContactNumberUC(PersonaService personaService, ContactNumberService contactNumberService)
	{
		this.personaService = personaService;
		this.contactNumberService = contactNumberService;
	}

	@Transactional
	public ContactNumberModel execute(Long personaId, CreateContactNumberDTO dto)
	{
		long start = System.currentTimeMillis();

		long personaLookupStart = System.currentTimeMillis();
		PersonaModel persona = personaService.findPersona(personaId);
		logger.info("--- [AddContactNumberUC] persona lookup (DB) took "+(System.currentTimeMillis() - personaLookupStart)+"ms ----");

		long createStart = System.currentTimeMillis();
		ContactNumberModel contactNumber = contactNumberService.create(dto, persona);
		logger.info("--- [AddContactNumberUC] contactNumberService.create (validation + DB) took "+(System.currentTimeMillis() - createStart)+"ms ----");

		logger.info("--- [AddContactNumberUC] execute total (java + DB) took "+(System.currentTimeMillis() - start)+"ms ----");
		return contactNumber;
	}

}
