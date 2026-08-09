package com.vktechnologies.democontact.infraestructure.dto;

import com.vktechnologies.democontact.infraestructure.models.GenderModel;

public record GetGenderDTO(Long id, String name) {
	public static GetGenderDTO from(GenderModel gender)
	{
		return new GetGenderDTO(gender.getId(), gender.getName());
	}
}
