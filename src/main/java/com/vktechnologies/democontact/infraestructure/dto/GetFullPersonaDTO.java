package com.vktechnologies.democontact.infraestructure.dto;

import java.time.LocalDate;
import java.util.List;

import com.vktechnologies.democontact.infraestructure.models.GenderModel;
import com.vktechnologies.democontact.infraestructure.models.PersonaModel;

public record GetFullPersonaDTO(
	Long id,
	String name,
	String firstName,
	String lastName,
	LocalDate birthDate,
	GetGenderDTO gender,
	List<GetPersonaDTO> emergencyContacts
) {
	public static GetFullPersonaDTO from(PersonaModel persona)
	{
		return new GetFullPersonaDTO(
			persona.getId(),
			persona.getName(),
			persona.getFirstName(),
			persona.getLastName(),
			persona.getBirthDate(),
			GetGenderDTO.from(persona.getGender()),
			persona.getEmergencyContacts().stream()
				.map(GetPersonaDTO::from)
				.toList()
		);
	}
}
