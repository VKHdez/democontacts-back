package com.vktechnologies.democontact.application.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vktechnologies.democontact.domain.user.exception.UserNotFoundException;
import com.vktechnologies.democontact.infraestructure.dto.CreateUserDTO;
import com.vktechnologies.democontact.infraestructure.dto.UpdateUserDTO;
import com.vktechnologies.democontact.infraestructure.models.PersonaModel;
import com.vktechnologies.democontact.infraestructure.models.UserModel;
import com.vktechnologies.democontact.infraestructure.persistence.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder)
	{
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public UserModel create(CreateUserDTO dto, PersonaModel persona)
	{
		UserModel user = new UserModel();
		user.setUsername(dto.username());
		user.setEmail(dto.email());
		user.setPassword(passwordEncoder.encode(dto.password()));
		user.setPersona(persona);

		return userRepository.save(user);
	}
	
	public UserModel update(Long userId, UpdateUserDTO dto)
	{
		UserModel userModel = userRepository.findById(userId)
			.orElseThrow(() -> new UserNotFoundException( userId ) );
		
		if( dto.username() != null && !dto.username().isEmpty() )
			userModel.setUsername(dto.username());
		
		if( dto.email() != null && !dto.email().isEmpty() )
			userModel.setEmail(dto.email());
		
		if( dto.password() != null && !dto.password().isEmpty() )
			userModel.setPassword(passwordEncoder.encode(dto.password()));
		
		return userRepository.save(userModel);
	}
}
