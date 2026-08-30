package com.vktechnologies.democontact.domain.contactnumber.exception;

import org.springframework.http.HttpStatus;

import com.vktechnologies.democontact.domain.exception.DomainException;

public class PhoneNumberTypeNotFoundException
	extends DomainException
{
	public PhoneNumberTypeNotFoundException(Long phoneNumberTypeId)
	{
		super("Phone number type: "+phoneNumberTypeId+" could not be found", HttpStatus.NOT_FOUND);
	}
}
