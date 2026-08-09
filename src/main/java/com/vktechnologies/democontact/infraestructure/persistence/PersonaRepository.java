package com.vktechnologies.democontact.infraestructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vktechnologies.democontact.infraestructure.models.PersonaModel;

public interface PersonaRepository extends JpaRepository<PersonaModel, Long> {
}
