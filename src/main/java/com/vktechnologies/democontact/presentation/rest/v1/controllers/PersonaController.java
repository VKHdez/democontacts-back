package com.vktechnologies.democontact.presentation.rest.v1.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vktechnologies.democontact.application.persona.usecase.UpdatePersonaUC;
import com.vktechnologies.democontact.infraestructure.api.ApiResponse;
import com.vktechnologies.democontact.infraestructure.dto.CreatePersonaDTO;
import com.vktechnologies.democontact.infraestructure.dto.DeletePersonaUC;
import com.vktechnologies.democontact.infraestructure.dto.GetPersonaDTO;
import com.vktechnologies.democontact.infraestructure.models.PersonaModel;
import com.vktechnologies.democontact.presentation.rest.ApiPaths;

/**
 * @author Ing. Victor Hdez. A <victor.hdezalvarez@gmail.com>
 */
@RestController
@RequestMapping(ApiPaths.v1 + "/persons")
public class PersonaController {
	
	private final UpdatePersonaUC updatePersonaUC;
	private final DeletePersonaUC deletePersonaUC;
	
	public PersonaController(
		UpdatePersonaUC updatePersonaUC,
		DeletePersonaUC deletePersonaUC
	) {
		this.updatePersonaUC = updatePersonaUC;
		this.deletePersonaUC = deletePersonaUC;
	}

	@PutMapping("/{personaId}")
	public ResponseEntity<ApiResponse<GetPersonaDTO>> update(
		@PathVariable Long personaId,
		@RequestBody CreatePersonaDTO updatePersonaDTO
	)
	{
		PersonaModel updatedPersona = updatePersonaUC.execute(personaId, updatePersonaDTO);
		return ResponseEntity.ok(new ApiResponse<>(GetPersonaDTO.from(updatedPersona), "Persona was updated succesfully"));
	}
	
	@DeleteMapping("/{personaId}")
	public ResponseEntity<ApiResponse<Void>> delete(
		@PathVariable Long personaId
	)
	{
		deletePersonaUC.execute(personaId);
		return ResponseEntity.ok(new ApiResponse<>(null, "Persona deleted succesfully"));
	}
}
