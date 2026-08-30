package com.vktechnologies.democontact.domain.contactnumber.exception;

import org.springframework.http.HttpStatus;

import com.vktechnologies.democontact.domain.exception.DomainException;

public class CountryCodeNotFoundException
	extends DomainException
{
	public CountryCodeNotFoundException(Long countryCodeId)
	{
		super("Country code: "+countryCodeId+" could not be found", HttpStatus.NOT_FOUND);
	}
}
