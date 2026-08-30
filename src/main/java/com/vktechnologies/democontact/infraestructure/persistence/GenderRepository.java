package com.vktechnologies.democontact.infraestructure.persistence;

import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.vktechnologies.democontact.infraestructure.models.GenderModel;

public interface GenderRepository extends JpaRepository<GenderModel, Long> {

	// NOTA: genders es un catalogo sembrado e inmutable (sin columna deleted),
	// se cachea solo el fetch del objeto, no ningun estado.
	@Override
	@Cacheable("genders")
	Optional<GenderModel> findById(Long id);
}
