package com.vktechnologies.democontact.infraestructure.dto;

import com.vktechnologies.democontact.infraestructure.models.AddressModel;

public record GetAddressDTO(
	Long id,
	String street,
	String externalNumber,
	String interiorNumber,
	String country,
	String state,
	String city,
	String postalCode,
	boolean isNormal,
	boolean isBilling
) {
	public static GetAddressDTO from(AddressModel address)
	{
		return new GetAddressDTO(
			address.getId(),
			address.getStreet(),
			address.getExternalNumber(),
			address.getInteriorNumber(),
			address.getCountry(),
			address.getState(),
			address.getCity(),
			address.getPostalCode(),
			address.isNormal(),
			address.isBilling()
		);
	}
}
