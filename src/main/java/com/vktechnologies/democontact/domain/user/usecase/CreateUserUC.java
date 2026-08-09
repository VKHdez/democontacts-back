package com.vktechnologies.democontact.domain.user.usecase;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.vktechnologies.democontact.domain.persona.service.PersonaService;
import com.vktechnologies.democontact.domain.user.service.UserService;
import com.vktechnologies.democontact.infraestructure.dto.CreateUserDTO;
import com.vktechnologies.democontact.infraestructure.models.PersonaModel;
import com.vktechnologies.democontact.infraestructure.models.UserModel;

@Component
public class CreateUserUC {

	private final PersonaService personaService;
	private final UserService userService;

	public CreateUserUC(PersonaService personaService, UserService userService)
	{
		this.personaService = personaService;
		this.userService = userService;
	}

	@Transactional
	public UserModel execute(CreateUserDTO dto)
	{
		PersonaModel persona = personaService.create(dto.persona());

		return userService.create(dto, persona);
	}
}
