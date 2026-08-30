package com.vktechnologies.democontact.infraestructure.dto;

import com.vktechnologies.democontact.infraestructure.models.CountryCodeModel;

public record GetCountryCodeDTO(Long id, String countryName, String isoCode, String dialCode) {
	public static GetCountryCodeDTO from(CountryCodeModel countryCode)
	{
		return new GetCountryCodeDTO(
			countryCode.getId(),
			countryCode.getCountryName(),
			countryCode.getIsoCode(),
			countryCode.getDialCode()
		);
	}
}
