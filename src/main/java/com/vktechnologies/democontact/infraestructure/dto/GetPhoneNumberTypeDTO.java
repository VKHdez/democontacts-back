package com.vktechnologies.democontact.infraestructure.dto;

import com.vktechnologies.democontact.infraestructure.models.PhoneNumberTypeModel;

public record GetPhoneNumberTypeDTO(Long id, String name) {
	public static GetPhoneNumberTypeDTO from(PhoneNumberTypeModel phoneNumberType)
	{
		return new GetPhoneNumberTypeDTO(phoneNumberType.getId(), phoneNumberType.getName());
	}
}
