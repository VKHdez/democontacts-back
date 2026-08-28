package com.vktechnologies.democontact.infraestructure.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateAddressDTO(
	@Size(max = 150)
	String street,

	@NotBlank
	@Size(max = 20)
	String externalNumber,

	@Size(max = 20)
	String interiorNumber,

	@NotBlank
	@Size(max = 100)
	String country,

	@NotBlank
	@Size(max = 100)
	String state,

	@NotBlank
	@Size(max = 100)
	String city,

	@NotBlank
	@Size(max = 20)
	@Pattern(regexp = "^[A-Za-z0-9 -]+$", message = "postalCode solo puede contener letras, numeros, espacios y guion")
	String postalCode,

	boolean isNormal,

	boolean isBilling
) {
	// Normaliza antes de validar: recorta espacios en los campos de texto.
	public CreateAddressDTO {
		street = street == null ? null : street.trim();
		externalNumber = externalNumber == null ? null : externalNumber.trim();
		interiorNumber = interiorNumber == null ? null : interiorNumber.trim();
		country = country == null ? null : country.trim();
		state = state == null ? null : state.trim();
		city = city == null ? null : city.trim();
		postalCode = postalCode == null ? null : postalCode.trim();
	}

	@AssertTrue(message = "isNormal o isBilling debe ser true")
	public boolean isNormalOrBilling()
	{
		return isNormal || isBilling;
	}
}
