package com.vktechnologies.democontact.infraestructure.persistence;

import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.vktechnologies.democontact.infraestructure.models.PhoneNumberTypeModel;

public interface PhoneNumberTypeRepository extends JpaRepository<PhoneNumberTypeModel, Long> {

	// NOTA: phone_number_types es un catalogo sembrado e inmutable (sin columna deleted),
	// se cachea solo el fetch del objeto, no ningun estado.
	@Override
	@Cacheable("phoneNumberTypes")
	Optional<PhoneNumberTypeModel> findById(Long id);

	@Cacheable("phoneNumberTypes")
	Optional<PhoneNumberTypeModel> findByName(String name);
}
