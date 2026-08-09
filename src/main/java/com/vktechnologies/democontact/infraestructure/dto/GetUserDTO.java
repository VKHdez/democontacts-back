package com.vktechnologies.democontact.infraestructure.dto;

import com.vktechnologies.democontact.infraestructure.models.UserModel;

public record GetUserDTO(
	Long id,
	String username,
	String email,
	GetPersonaDTO persona
) {
	public static GetUserDTO from(UserModel user)
	{
		return new GetUserDTO(
			user.getId(),
			user.getUsername(),
			user.getEmail(),
			GetPersonaDTO.from(user.getPersona())
		);
	}
}
