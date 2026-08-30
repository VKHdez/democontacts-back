package com.vktechnologies.democontact.infraestructure.persistence;

import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.vktechnologies.democontact.infraestructure.models.CountryCodeModel;

public interface CountryCodeRepository extends JpaRepository<CountryCodeModel, Long> {

	// NOTA: country_codes es un catalogo sembrado e inmutable (sin columna deleted),
	// se cachea solo el fetch del objeto, no ningun estado.
	@Override
	@Cacheable("countryCodes")
	Optional<CountryCodeModel> findById(Long id);
}
