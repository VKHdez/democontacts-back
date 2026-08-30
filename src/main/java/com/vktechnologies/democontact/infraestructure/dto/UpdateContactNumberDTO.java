package com.vktechnologies.democontact.infraestructure.dto;

import java.util.List;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateContactNumberDTO(
	Long countryCodeId,

	@Size(max = 20)
	@Pattern(regexp = "^[0-9]+$", message = "number solo puede contener numeros")
	String number,

	List<Long> phoneNumberTypeIds
) {
	// Normaliza antes de validar: recorta espacios en los campos de texto.
	public UpdateContactNumberDTO
	{
		number = number == null ? null : number.trim();
	}
}
