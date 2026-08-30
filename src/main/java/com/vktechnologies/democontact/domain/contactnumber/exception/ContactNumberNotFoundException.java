package com.vktechnologies.democontact.domain.contactnumber.exception;

import org.springframework.http.HttpStatus;

import com.vktechnologies.democontact.domain.exception.DomainException;

public class ContactNumberNotFoundException
	extends DomainException
{
	public ContactNumberNotFoundException(Long contactNumberId)
	{
		super("Contact number: "+contactNumberId+" could not be found", HttpStatus.NOT_FOUND);
	}

	public ContactNumberNotFoundException(Long countryCodeId, String number)
	{
		super("Contact number: "+number+" (country code: "+countryCodeId+") could not be found", HttpStatus.NOT_FOUND);
	}

	public ContactNumberNotFoundException(Long contactNumberId, Long personaId)
	{
		super("Contact number: "+contactNumberId+" is not related to persona: "+personaId, HttpStatus.NOT_FOUND);
	}
}
