package com.vktechnologies.democontact.application.persona.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.vktechnologies.democontact.domain.persona.exception.PersonaNotFoundException;
import com.vktechnologies.democontact.infraestructure.dto.CreatePersonaDTO;
import com.vktechnologies.democontact.infraestructure.models.GenderModel;
import com.vktechnologies.democontact.infraestructure.models.PersonaModel;
import com.vktechnologies.democontact.infraestructure.models.UserModel;
import com.vktechnologies.democontact.infraestructure.persistence.GenderRepository;
import com.vktechnologies.democontact.infraestructure.persistence.PersonaRepository;
import com.vktechnologies.democontact.infraestructure.persistence.UserRepository;

/**
 * @author Ing. Victor Hdez. A <victor.hdezalvarez@gmail.com>
 */
@Service
public class PersonaService {

	private final PersonaRepository personaRepository;
	private final UserRepository userRepository;
	private final GenderRepository genderRepository;

	public PersonaService(
		PersonaRepository personaRepository, 
		UserRepository userRepository,
		GenderRepository genderRepository
	)
	{
		this.personaRepository = personaRepository;
		this.genderRepository = genderRepository;
		this.userRepository = userRepository;
	}
	
	public PersonaModel findPersona(Long personaId)
	{
		return personaRepository.findById(personaId)
				.orElseThrow(() -> new PersonaNotFoundException( personaId ) );
	}

	public PersonaModel create(CreatePersonaDTO dto)
	{
		GenderModel gender = genderRepository.getReferenceById(dto.genderId());

		PersonaModel persona = new PersonaModel();
		persona.setName(dto.name());
		persona.setFirstName(dto.firstName());
		persona.setLastName(dto.lastName());
		persona.setGender(gender);
		persona.setBirthDate(dto.birthDate());

		return personaRepository.save(persona);
	}
	
	public PersonaModel update(PersonaModel oldPersona, CreatePersonaDTO dto)
	{
		// 1.- Load gender catalog
		GenderModel gender = genderRepository.getReferenceById(dto.genderId());
		// 2.- Validate fields to be updated
		
		if(dto.name() != null && !dto.name().isEmpty() )
			oldPersona.setName(dto.name()); 
		
		if(dto.firstName() != null && !dto.firstName().isEmpty() )
			oldPersona.setFirstName(dto.firstName());
		
		if(dto.lastName() != null && !dto.lastName().isEmpty() )
			oldPersona.setLastName(dto.lastName());
		
		if(dto.birthDate() != null )
			oldPersona.setBirthDate(dto.birthDate());
		
		if(dto.genderId() != null && dto.genderId() > 0)
			oldPersona.setGender(gender);
		
		return personaRepository.save(oldPersona);
	}
	
	public void disable(Long personaId)
	{
		PersonaModel personaModel = personaRepository.findById(personaId)
			.orElseThrow(() -> new PersonaNotFoundException( personaId ) );
	
		personaRepository.delete(personaModel);
	}
}
