package com.vktechnologies.democontact.presentation.rest.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.vktechnologies.democontact.domain.exception.DomainException;
import com.vktechnologies.democontact.infraestructure.api.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler
{
	@ExceptionHandler(DomainException.class)
	public ResponseEntity<ApiResponse<Void>> handleDomain(DomainException domainException)
	{
		return ResponseEntity.status(domainException.getStatus())
			.body(new ApiResponse<>(null, domainException.getMessage()));
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<Void>> handleDefault(Exception exception)
	{
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.body(new ApiResponse<>(null, exception.getMessage()));
	}

}
