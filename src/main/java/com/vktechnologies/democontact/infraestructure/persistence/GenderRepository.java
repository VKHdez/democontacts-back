package com.vktechnologies.democontact.infraestructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vktechnologies.democontact.infraestructure.models.GenderModel;

public interface GenderRepository extends JpaRepository<GenderModel, Long> {
}
