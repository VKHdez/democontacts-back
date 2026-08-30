package com.vktechnologies.democontact.presentation.rest.exception.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.vktechnologies.democontact.domain.exception.DomainException;
import com.vktechnologies.democontact.infraestructure.api.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler
{
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(DomainException.class)
	public ResponseEntity<ApiResponse<Void>> handleDomain(DomainException domainException)
	{
		return ResponseEntity.status(domainException.getStatus())
			.body(new ApiResponse<>(null, domainException.getMessage()));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<Void>> handleDefault(Exception exception)
	{
		logger.error("--- Unhandled exception reached GlobalExceptionHandler ----", exception);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.body(new ApiResponse<>(null, exception.getMessage()));
	}

}
