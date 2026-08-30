package com.vktechnologies.democontact.infraestructure.dto;

import java.util.List;

import com.vktechnologies.democontact.infraestructure.models.ContactNumberModel;

public record GetContactNumberDTO(
	Long id,
	GetCountryCodeDTO countryCode,
	String number,
	List<GetPhoneNumberTypeDTO> phoneNumberTypes
) {
	public static GetContactNumberDTO from(ContactNumberModel contactNumber)
	{
		return new GetContactNumberDTO(
			contactNumber.getId(),
			GetCountryCodeDTO.from(contactNumber.getCountryCode()),
			contactNumber.getNumber(),
			contactNumber.getPhoneNumberTypes().stream().map(GetPhoneNumberTypeDTO::from).toList()
		);
	}
}
