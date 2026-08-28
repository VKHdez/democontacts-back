package com.vktechnologies.democontact.presentation.rest.v1.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vktechnologies.democontact.application.persona.usecase.ActivatePersonaUC;
import com.vktechnologies.democontact.application.persona.usecase.AddAddressUC;
import com.vktechnologies.democontact.application.persona.usecase.AddEmergencyContactUC;
import com.vktechnologies.democontact.application.persona.usecase.DeleteAddressUC;
import com.vktechnologies.democontact.application.persona.usecase.DeleteEmergencyContactUC;
import com.vktechnologies.democontact.application.persona.usecase.DeletePersonaUC;
import com.vktechnologies.democontact.application.persona.usecase.GetPersonaUC;
import com.vktechnologies.democontact.application.persona.usecase.UpdateAddressUC;
import com.vktechnologies.democontact.application.persona.usecase.UpdatePersonaUC;
import com.vktechnologies.democontact.infraestructure.api.ApiResponse;
import com.vktechnologies.democontact.infraestructure.dto.CreateAddressDTO;
import com.vktechnologies.democontact.infraestructure.dto.CreatePersonaDTO;
import com.vktechnologies.democontact.infraestructure.dto.GetAddressDTO;
import com.vktechnologies.democontact.infraestructure.dto.GetFullPersonaDTO;
import com.vktechnologies.democontact.infraestructure.dto.GetPersonaDTO;
import com.vktechnologies.democontact.infraestructure.dto.UpdateAddressDTO;
import com.vktechnologies.democontact.infraestructure.models.AddressModel;
import com.vktechnologies.democontact.infraestructure.models.PersonaModel;
import com.vktechnologies.democontact.presentation.rest.ApiPaths;

import jakarta.validation.Valid;

/**
 * @author Ing. Victor Hdez. A <victor.hdezalvarez@gmail.com>
 */
@RestController
@RequestMapping(ApiPaths.v1 + "/persons")
public class PersonaController {
	
	private final UpdatePersonaUC updatePersonaUC;
	private final DeletePersonaUC deletePersonaUC;
	private final ActivatePersonaUC activatePersonaUC;
	private final GetPersonaUC getPersonaUC;
	// Emergency Contacts
	private final AddEmergencyContactUC addEmergencyContactUC;
	private final DeleteEmergencyContactUC deleteEmergencyContactUC;
	// Addresses
	private final AddAddressUC addAddressUC;
	private final UpdateAddressUC updateAddressUC;
	private final DeleteAddressUC deleteAddressUC;

	public PersonaController(
		UpdatePersonaUC updatePersonaUC,
		DeletePersonaUC deletePersonaUC,
		ActivatePersonaUC activatePersonaUC,
		GetPersonaUC getPersonaUC,
		AddEmergencyContactUC addEmergencyContactUC,
		DeleteEmergencyContactUC deleteEmergencyContactUC,
		AddAddressUC addAddressUC,
		UpdateAddressUC updateAddressUC,
		DeleteAddressUC deleteAddressUC
	) {
		this.updatePersonaUC = updatePersonaUC;
		this.deletePersonaUC = deletePersonaUC;
		this.activatePersonaUC = activatePersonaUC;
		this.getPersonaUC = getPersonaUC;
		this.addEmergencyContactUC = addEmergencyContactUC;
		this.deleteEmergencyContactUC = deleteEmergencyContactUC;
		this.addAddressUC = addAddressUC;
		this.updateAddressUC = updateAddressUC;
		this.deleteAddressUC = deleteAddressUC;
	}
	
	@GetMapping("/{personaId}")
	public ResponseEntity<ApiResponse<GetFullPersonaDTO>> view(
		@PathVariable Long personaId
	) {
		PersonaModel persona = getPersonaUC.execute(personaId);
		return ResponseEntity.ok( new ApiResponse<>(
			GetFullPersonaDTO.from(persona), 
			"Persona was retrieved succesfully"
		));
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
	
	@PutMapping("/{personaId}/activate")
	public ResponseEntity<ApiResponse<GetPersonaDTO>> activate(
		@PathVariable Long personaId
	)
	{
		PersonaModel persona = activatePersonaUC.execute(personaId);
		return ResponseEntity.ok(new ApiResponse<>(GetPersonaDTO.from(persona), "Persona activated succesfully"));
	}
	
	// EMERGENCY CONTACTS 
	
	@PutMapping("/{personaId}/{emergencyContactId}")
	public ResponseEntity<ApiResponse<PersonaModel>> addEmergencyContact(
		@PathVariable Long personaId,
		@PathVariable Long emergencyContactId
	){
		PersonaModel persona = this.addEmergencyContactUC.execute(personaId, emergencyContactId);
		return ResponseEntity.ok(new ApiResponse<>(
			persona,
			"Emergency contact associated to persona"
		));
	}
	
	@DeleteMapping("/{personaId}/{emergencyContactId}")
	public ResponseEntity<ApiResponse<PersonaModel>> deleteEmergencyContact(
		@PathVariable Long personaId,
		@PathVariable Long emergencyContactId
	){
		PersonaModel persona = this.deleteEmergencyContactUC.execute(personaId, emergencyContactId);
		return ResponseEntity.ok( new ApiResponse<>(
			persona,
			"Emergency contact removed"
		));
	}
	
	// ADDRESSES
	
	@PostMapping("/{personaId}/address")
	public ResponseEntity<ApiResponse<GetAddressDTO>> addAddress(
		@PathVariable Long personaId,
		@Valid @RequestBody CreateAddressDTO dto
	){
		AddressModel address = this.addAddressUC.execute(personaId, dto);
		return ResponseEntity.ok( new ApiResponse<>(
			GetAddressDTO.from(address),
			"Address related to persona"
		));
	}

	@PutMapping("/{personaId}/address/{addressId}")
	public ResponseEntity<ApiResponse<GetAddressDTO>> updateAddress(
		@PathVariable Long personaId,
		@PathVariable Long addressId,
		@Valid @RequestBody UpdateAddressDTO dto
	){
		AddressModel address = this.updateAddressUC.execute(personaId, addressId, dto);
		return ResponseEntity.ok( new ApiResponse<>(
			GetAddressDTO.from(address),
			"Address updated succesfully"
		));
	}

	@DeleteMapping("/{personaId}/address/{addressId}")
	public ResponseEntity<ApiResponse<Void>> deleteAddress(
		@PathVariable Long personaId,
		@PathVariable Long addressId
	){
		this.deleteAddressUC.execute(personaId, addressId);
		return ResponseEntity.ok( new ApiResponse<>(
			null,
			"Address deleted succesfully"
		));
	}
}
