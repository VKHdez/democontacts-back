package com.vktechnologies.democontact.application.user.usecase;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.vktechnologies.democontact.domain.user.exception.UserNotFoundException;
import com.vktechnologies.democontact.infraestructure.models.UserModel;
import com.vktechnologies.democontact.infraestructure.persistence.UserRepository;

@Component
public class GetUserUC {

	private final UserRepository userRepository;

	public GetUserUC(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public UserModel execute(Long userId) {
		UserModel userModel = userRepository.findById(userId)
			.orElseThrow( ()-> new UserNotFoundException(userId) );
		return userModel;
	}
}
