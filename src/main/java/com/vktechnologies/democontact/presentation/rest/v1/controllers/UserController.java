package com.vktechnologies.democontact.presentation.rest.v1.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import com.vktechnologies.democontact.domain.user.usecase.CreateUserUC;
import com.vktechnologies.democontact.infraestructure.api.ApiResponse;
import com.vktechnologies.democontact.infraestructure.dto.CreateUserDTO;
import com.vktechnologies.democontact.infraestructure.dto.GetUserDTO;
import com.vktechnologies.democontact.infraestructure.models.UserModel;

@RestController
public class UserController {

	private final CreateUserUC createUserUC;

	public UserController(CreateUserUC createUserUC)
	{
		this.createUserUC = createUserUC;
	}

	@GetMapping("/api/v1/users")
	public String index()
	{
		return null;
	}

	@PostMapping("/api/v1/user")
	public ResponseEntity<ApiResponse<GetUserDTO>> create(@Valid @RequestBody CreateUserDTO request)
	{
		UserModel user = createUserUC.execute(request);

		return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(GetUserDTO.from(user), "Usuario creado"));
	}

	public UserModel view()
	{
		return null;
	}

	public UserModel update()
	{
		return null;
	}

	public String delete()
	{
		return null;
	}
}