package com.vktechnologies.democontact.presentation.rest.v1.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import com.vktechnologies.democontact.application.user.usecase.CreateUserUC;
import com.vktechnologies.democontact.application.user.usecase.GetUserUC;
import com.vktechnologies.democontact.application.user.usecase.UpdateUserUC;
import com.vktechnologies.democontact.infraestructure.api.ApiResponse;
import com.vktechnologies.democontact.infraestructure.dto.CreateUserDTO;
import com.vktechnologies.democontact.infraestructure.dto.GetUserDTO;
import com.vktechnologies.democontact.infraestructure.dto.UpdateUserDTO;
import com.vktechnologies.democontact.infraestructure.models.UserModel;
import com.vktechnologies.democontact.presentation.rest.ApiPaths;

@RestController
@RequestMapping(ApiPaths.v1 + "/users")
public class UserController {

	private final CreateUserUC createUserUC;
	private final GetUserUC getUserUC;
	private final UpdateUserUC updateUserUC;

	public UserController(
		CreateUserUC createUserUC, 
		GetUserUC getUserUC,
		UpdateUserUC updateUserUC
	)
	{
		this.createUserUC = createUserUC;
		this.getUserUC = getUserUC;
		this.updateUserUC = updateUserUC;
	}

	@PostMapping("")
	public ResponseEntity<ApiResponse<GetUserDTO>> create(@Valid @RequestBody CreateUserDTO request)
	{
		UserModel user = createUserUC.execute(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(GetUserDTO.from(user), "User created"));
	}

	@GetMapping("/{userId}")
	public ResponseEntity<ApiResponse<GetUserDTO>> view(@PathVariable Long userId)
	{
		UserModel user = getUserUC.execute(userId);
		return ResponseEntity.status(HttpStatus.resolve(200)).body(
			new ApiResponse<>(GetUserDTO.from(user), "User found")
		);
	}

	@PutMapping("/{userId}")
	public ResponseEntity<ApiResponse<GetUserDTO>> update(
		@PathVariable Long userId,
		@Valid @RequestBody UpdateUserDTO request
	)
	{
		UserModel user = updateUserUC.execute(userId, request);
		return ResponseEntity.status(HttpStatus.resolve(200)).body(
				new ApiResponse<>(GetUserDTO.from(user), "User retrieved")
		);
	}

	public String delete()
	{
		return null;
	}
}