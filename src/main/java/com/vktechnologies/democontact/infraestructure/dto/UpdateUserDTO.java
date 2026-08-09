package com.vktechnologies.democontact.infraestructure.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateUserDTO(
	@Size(min = 3, max = 50)
	@Pattern(regexp = "^[a-zA-Z0-9._-]+$", message = "username solo puede contener letras, numeros, punto, guion y guion bajo")
	String username,
	
	@Size(max = 255)
	@Email
	@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "email con formato invalido")
	String email,

	@Size(min = 8, max = 255)
	String password
) {
	
	public UpdateUserDTO{
		username = username == null ? null : username.trim();
		email = email == null ? null : email.trim().toLowerCase();
	}

}
