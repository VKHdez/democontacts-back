package com.vktechnologies.democontact.domain.user.exception;

import com.vktechnologies.democontact.domain.exception.DomainException;

public class UserNotDisabledException extends DomainException {

	public int errorCode = 500;
	
	public UserNotDisabledException(Long userId)
	{
		super("User: "+userId+" could not be disabled");
	}
}
