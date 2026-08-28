package com.vktechnologies.democontact.application.persona.usecase;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.vktechnologies.democontact.application.persona.service.PersonaService;
import com.vktechnologies.democontact.application.user.service.UserService;
import com.vktechnologies.democontact.infraestructure.models.PersonaModel;
import com.vktechnologies.democontact.infraestructure.models.UserModel;

import jakarta.transaction.Transactional;

@Component
public class DeletePersonaUC
{
	private final PersonaService personaService;
	private final UserService userService;

	private static final Logger logger = LoggerFactory.getLogger(DeletePersonaUC.class);

	public DeletePersonaUC(PersonaService personaService, UserService userService)
	{
		this.personaService = personaService;
		this.userService = userService;
	}

	@Transactional
	public void execute(Long personaId)
	{
		logger.info("--- Persona "+personaId+" will be deleted ----");

		PersonaModel personaModel = personaService.findPersona(personaId);
		Optional<UserModel> optionalUserModel = userService.findByPersona(personaModel);

		if( optionalUserModel.isPresent()) {
			UserModel userModel = optionalUserModel.get();
			logger.info("--- Related User "+userModel.getId()+" to Persona"+personaId+" will be deleted ----");
			userService.disable(userModel.getId());
		}else {
			logger.info("--- Related User to Persona"+personaId+" was not found ----");
		}

		logger.info("--- Persona "+personaId+" and its related content (emergency contacts, addresses) will be disabled ----");
		personaService.disableWithRelatedContent(personaId);
		logger.info("--- Persona "+personaId+" was deleted ----");
	}
}
