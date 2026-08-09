package com.vktechnologies.democontact.infraestructure.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateUserDTO(
	@NotBlank
	@Size(min = 3, max = 50)
	@Pattern(regexp = "^[a-zA-Z0-9._-]+$", message = "username solo puede contener letras, numeros, punto, guion y guion bajo")
	String username,

	@NotBlank
	@Size(max = 255)
	@Email
	@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "email con formato invalido")
	String email,

	@NotBlank
	@Size(min = 8, max = 255)
	String password,

	@NotNull
	@Valid
	CreatePersonaDTO persona
) {
	// Normaliza antes de validar: recorta espacios y homogeneiza el email.
	public CreateUserDTO {
		username = username == null ? null : username.trim();
		email = email == null ? null : email.trim().toLowerCase();
	}
}
