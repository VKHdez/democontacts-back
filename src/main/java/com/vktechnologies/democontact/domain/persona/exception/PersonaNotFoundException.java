package com.vktechnologies.democontact.domain.persona.exception;

import org.springframework.http.HttpStatus;

import com.vktechnologies.democontact.domain.exception.DomainException;

public class PersonaNotFoundException
	extends DomainException
{
	public PersonaNotFoundException(Long personaId)
	{
		super("Persona: "+personaId+" could not be found", HttpStatus.NOT_FOUND);
	}
}
