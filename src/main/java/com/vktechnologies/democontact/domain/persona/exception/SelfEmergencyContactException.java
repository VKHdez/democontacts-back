package com.vktechnologies.democontact.domain.persona.exception;

import org.springframework.http.HttpStatus;

import com.vktechnologies.democontact.domain.exception.DomainException;

public class SelfEmergencyContactException
	extends DomainException
{
	public SelfEmergencyContactException(Long personaId)
	{
		super("Persona: "+personaId+" cannot be its own emergency contact", HttpStatus.BAD_REQUEST);
	}
}
