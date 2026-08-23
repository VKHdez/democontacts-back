package com.vktechnologies.democontact.infraestructure.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vktechnologies.democontact.infraestructure.models.PersonaModel;
import com.vktechnologies.democontact.infraestructure.models.UserModel;

public interface UserRepository extends JpaRepository<UserModel, Long>
{
	Optional<UserModel> findByPersona(PersonaModel persona);

	@Modifying(clearAutomatically = true)
	@Query(value = """
		UPDATE users
		SET deleted = 0
		WHERE persona_id = :personaId
	""", nativeQuery = true)
	int activateByPersonaId(@Param("personaId") Long personaId);
}
