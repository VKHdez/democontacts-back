package com.vktechnologies.democontact.domain.user.exception;

import com.vktechnologies.democontact.domain.exception.DomainException;

public class UserNotFoundException extends DomainException {
	
	int errorCode = 404;

	public UserNotFoundException(Long userId) {
		super("The user: "+userId+" could not be found");
	}
}
