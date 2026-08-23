package com.vktechnologies.democontact.infraestructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vktechnologies.democontact.infraestructure.models.PersonaModel;

public interface PersonaRepository extends JpaRepository<PersonaModel, Long> {

	@Modifying(clearAutomatically = true)
	@Query(value = """
		UPDATE personas
		SET deleted = 0
		WHERE id = :personaId
	""", nativeQuery = true)
	int activateById(@Param("personaId") Long personaId);

	/**
	 * This functions validates if there exists a current register in DB
	 * @param personaId
	 * @param emergencyContactId
	 * @return
	 */
	@Query( value = """
	SELECT CASE WHEN EXISTS (
		SELECT 1 FROM persona_emergency_contacts
		WHERE persona_id = :personaId
			AND emergency_contact_persona_id = :emergencyContactId
	) THEN 1 ELSE 0 END	
	""", nativeQuery = true)
	int existsDeletedEmergencyContact(
		@Param("personaId") Long personaId, 
		@Param("emergencyContactId") Long emergencyContactId
	);
	
	@Modifying(clearAutomatically = true)
	@Query(value = """
		UPDATE persona_emergency_contacts
		SET deleted = 0
		WHERE persona_id = :personaId
			AND emergency_contact_persona_id = :emergencyContactId
	""", nativeQuery = true)
	void reactivateEmergencyContact(
		@Param("personaId") Long personaId, 
		@Param("emergencyContactId") Long emergencyContactId
	);
}
