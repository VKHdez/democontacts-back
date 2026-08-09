package com.vktechnologies.democontact.infraestructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vktechnologies.democontact.infraestructure.models.UserModel;

public interface UserRepository extends JpaRepository<UserModel, Long> {
}
