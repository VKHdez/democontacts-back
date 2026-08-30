package com.vktechnologies.democontact.infraestructure.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateContactNumberDTO(
	@NotNull
	Long countryCodeId,

	@NotBlank
	@Size(max = 20)
	@Pattern(regexp = "^[0-9]+$", message = "number solo puede contener numeros")
	String number,

	@NotEmpty
	List<Long> phoneNumberTypeIds
) {
	// Normaliza antes de validar: recorta espacios en los campos de texto.
	public CreateContactNumberDTO {
		number = number == null ? null : number.trim();
	}
}
