package com.vktechnologies.democontact.domain.address.exception;

import org.springframework.http.HttpStatus;

import com.vktechnologies.democontact.domain.exception.DomainException;

public class InvalidAddressTypeException
	extends DomainException
{
	public InvalidAddressTypeException(Long addressId)
	{
		super("Address: "+addressId+" must be isNormal, isBilling, or both", HttpStatus.BAD_REQUEST);
	}
}
