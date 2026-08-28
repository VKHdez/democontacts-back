package com.vktechnologies.democontact.domain.address.exception;

import org.springframework.http.HttpStatus;

import com.vktechnologies.democontact.domain.exception.DomainException;

public class AddressNotFoundException
	extends DomainException
{
	public AddressNotFoundException(Long addressId)
	{
		super("Address: "+addressId+" could not be found", HttpStatus.NOT_FOUND);
	}

	public AddressNotFoundException(Long addressId, Long personaId)
	{
		super("Address: "+addressId+" is not related to persona: "+personaId, HttpStatus.NOT_FOUND);
	}
}
