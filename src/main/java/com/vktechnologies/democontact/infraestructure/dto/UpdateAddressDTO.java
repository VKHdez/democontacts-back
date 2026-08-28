package com.vktechnologies.democontact.infraestructure.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateAddressDTO(
	@Size(max = 150)
	String street,

	@Size(max = 20)
	String externalNumber,

	@Size(max = 20)
	String interiorNumber,

	@Size(max = 100)
	String country,

	@Size(max = 100)
	String state,

	@Size(max = 100)
	String city,

	@Size(max = 20)
	@Pattern(regexp = "^[A-Za-z0-9 -]+$", message = "postalCode solo puede contener letras, numeros, espacios y guion")
	String postalCode,

	Boolean isNormal,

	Boolean isBilling
) {
	// Normaliza antes de validar: recorta espacios en los campos de texto.
	public UpdateAddressDTO
	{
		street = street == null ? null : street.trim();
		externalNumber = externalNumber == null ? null : externalNumber.trim();
		interiorNumber = interiorNumber == null ? null : interiorNumber.trim();
		country = country == null ? null : country.trim();
		state = state == null ? null : state.trim();
		city = city == null ? null : city.trim();
		postalCode = postalCode == null ? null : postalCode.trim();
	}

	/**
	 * Solo detecta el caso donde ambos vienen explicitos en false en el mismo request.
	 * Si alguno viene null (no se esta tocando), no hay suficiente informacion aqui
	 * para saber el estado final -- eso se valida en AddressService.update sobre la entidad ya mergeada.
	 */
	@AssertTrue(message = "isNormal o isBilling debe ser true si ambos se envian")
	public boolean isNormalOrBilling()
	{
		if (isNormal != null && isBilling != null)
			return isNormal || isBilling;

		return true;
	}
}
