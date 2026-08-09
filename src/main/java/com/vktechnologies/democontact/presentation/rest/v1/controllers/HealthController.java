package com.vktechnologies.democontact.presentation.rest.v1.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vktechnologies.democontact.infraestructure.api.ApiResponse;

@RestController
public class HealthController {

	@GetMapping("/api/v1/health")
	public ApiResponse<String[]> health()
	{
		//return "API de Contactos Funcionando correctamente";
		String[] responseData = {"Contacts API Works perfectly"};
		return new ApiResponse<String[]>(responseData, "report completed successfully");
	}
}
