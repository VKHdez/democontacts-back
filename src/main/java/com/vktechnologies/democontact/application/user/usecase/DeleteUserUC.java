package com.vktechnologies.democontact.application.user.usecase;

import org.springframework.stereotype.Component;

import com.vktechnologies.democontact.application.persona.service.PersonaService;
import com.vktechnologies.democontact.application.user.service.UserService;
import com.vktechnologies.democontact.infraestructure.models.UserModel;

import jakarta.transaction.Transactional;

@Component
/**
 * This Use Case disable the user and related persona
 * 
 * @author Ing. Victor Hdez. <victor.hdezalvarez@gmail.com>
 */
public class DeleteUserUC {

	private final UserService userService;
	private final PersonaService personaService;
	
	public DeleteUserUC(UserService userService, PersonaService personaService)
	{
		this.userService = userService;
		this.personaService = personaService;
	}
	
	@Transactional
	public void execute(Long userId)
	{
		UserModel user = this.getUser(userId);
		
		// Disabling the user first
		userService.disable(userId);
		// Disabling the persona
		personaService.disable(user.getPersona().getId());
	}
	
	private UserModel getUser(Long userId)
	{
		return userService.find(userId);
	}
}