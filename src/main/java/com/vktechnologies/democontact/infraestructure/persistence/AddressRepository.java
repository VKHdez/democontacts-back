package com.vktechnologies.democontact.infraestructure.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vktechnologies.democontact.infraestructure.models.AddressModel;

public interface AddressRepository extends JpaRepository<AddressModel, Long> {

	List<AddressModel> findByPersonaId(Long personaId);

	@Modifying(clearAutomatically = true)
	@Query(value = """
		UPDATE addresses
		SET deleted = 0
		WHERE persona_id = :personaId
	""", nativeQuery = true)
	void activateByPersonaId(@Param("personaId") Long personaId);
}
