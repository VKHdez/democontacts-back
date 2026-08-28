package com.vktechnologies.democontact.infraestructure.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vktechnologies.democontact.infraestructure.models.AddressModel;

public interface AddressRepository extends JpaRepository<AddressModel, Long> {

	List<AddressModel> findByPersonaId(Long personaId);
}
