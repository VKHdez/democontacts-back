package com.vktechnologies.democontact.application.user.usecase;

import org.springframework.stereotype.Component;

import com.vktechnologies.democontact.application.user.service.UserService;
import com.vktechnologies.democontact.infraestructure.dto.UpdateUserDTO;
import com.vktechnologies.democontact.infraestructure.models.UserModel;

@Component
public class UpdateUserUC {

	private final UserService userService;
	
	public UpdateUserUC(UserService userService) {
		this.userService = userService;
	}
	
	public UserModel execute(Long userId, UpdateUserDTO updateUserDTO) {
		UserModel userModel = userService.update(userId, updateUserDTO);
		return userModel;
	}
}
