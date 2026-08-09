package com.vktechnologies.democontact.infraestructure.dto;

import java.time.LocalDate;

import com.vktechnologies.democontact.infraestructure.models.PersonaModel;

public record GetPersonaDTO(
	Long id,
	String name,
	String firstName,
	String lastName,
	GetGenderDTO gender,
	LocalDate birthDate
) {
	public static GetPersonaDTO from(PersonaModel persona)
	{
		return new GetPersonaDTO(
			persona.getId(),
			persona.getName(),
			persona.getFirstName(),
			persona.getLastName(),
			GetGenderDTO.from(persona.getGender()),
			persona.getBirthDate()
		);
	}
}
