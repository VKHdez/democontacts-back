package com.vktechnologies.democontact.domain.user.exception;

import org.springframework.http.HttpStatus;

import com.vktechnologies.democontact.domain.exception.DomainException;

public class UserNotFoundException extends DomainException {

	public UserNotFoundException(Long userId) {
		super("The user: "+userId+" could not be found", HttpStatus.NOT_FOUND);
	}
}
