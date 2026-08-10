package com.vktechnologies.democontact.application.persona.service;

import org.springframework.stereotype.Service;

import com.vktechnologies.democontact.domain.user.exception.UserNotFoundException;
import com.vktechnologies.democontact.infraestructure.dto.CreatePersonaDTO;
import com.vktechnologies.democontact.infraestructure.models.GenderModel;
import com.vktechnologies.democontact.infraestructure.models.PersonaModel;
import com.vktechnologies.democontact.infraestructure.persistence.GenderRepository;
import com.vktechnologies.democontact.infraestructure.persistence.PersonaRepository;

@Service
public class PersonaService {

	private final PersonaRepository personaRepository;
	private final GenderRepository genderRepository;

	public PersonaService(PersonaRepository personaRepository, GenderRepository genderRepository)
	{
		this.personaRepository = personaRepository;
		this.genderRepository = genderRepository;
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
	
	public void disable(Long personaId)
	{
		PersonaModel personaModel = personaRepository.findById(personaId)
			.orElseThrow(() -> new UserNotFoundException( personaId ) );
		
		personaRepository.delete(personaModel);
	}
}
