package com.vktechnologies.democontact.infraestructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vktechnologies.democontact.infraestructure.models.AddressModel;

public interface AddressRepository extends JpaRepository<AddressModel, Long> {

}
