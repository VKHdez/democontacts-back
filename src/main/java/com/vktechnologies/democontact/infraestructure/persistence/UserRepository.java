package com.vktechnologies.democontact.infraestructure.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vktechnologies.democontact.infraestructure.models.PersonaModel;
import com.vktechnologies.democontact.infraestructure.models.UserModel;

public interface UserRepository extends JpaRepository<UserModel, Long> 
{
	Optional<UserModel> findByPersona(PersonaModel persona);
}
