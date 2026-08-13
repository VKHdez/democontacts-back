package com.vktechnologies.democontact.domain.user.exception;

import org.springframework.http.HttpStatus;

import com.vktechnologies.democontact.domain.exception.DomainException;

public class UserNotDisabledException extends DomainException {

	public UserNotDisabledException(Long userId)
	{
		super("User: "+userId+" could not be disabled", HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
