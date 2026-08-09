package com.vktechnologies.democontact.infraestructure.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreatePersonaDTO(
	@NotBlank
	@Size(max = 100)
	@Pattern(regexp = "^[\\p{L} '.-]+$", message = "name solo puede contener letras, espacios, apostrofe, punto y guion")
	String name,

	@NotBlank
	@Size(max = 100)
	@Pattern(regexp = "^[\\p{L} '.-]+$", message = "firstName solo puede contener letras, espacios, apostrofe, punto y guion")
	String firstName,

	@NotBlank
	@Size(max = 100)
	@Pattern(regexp = "^[\\p{L} '.-]+$", message = "lastName solo puede contener letras, espacios, apostrofe, punto y guion")
	String lastName,

	@NotNull
	Long genderId,

	@Past
	LocalDate birthDate
) {
	// Normaliza antes de validar: recorta espacios en los campos de texto.
	public CreatePersonaDTO {
		name = name == null ? null : name.trim();
		firstName = firstName == null ? null : firstName.trim();
		lastName = lastName == null ? null : lastName.trim();
	}
}
