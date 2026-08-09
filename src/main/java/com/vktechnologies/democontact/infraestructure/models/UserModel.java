package com.vktechnologies.democontact.infraestructure.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class UserModel {
	
	// Attributes

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 50)
	private String username;
	
	@Column(nullable = false, length = 255)
	private String email;
	
	@Column(nullable = false, length = 255)
	private String password;
	
	// Relations
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "persona_id", nullable = false, unique = true)
	private PersonaModel persona;
	
	// Setters & Getters

	public Long getId() { return id; }

	public String getUsername() { return username; }
	public void setUsername(String username) { this.username = username; }

	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }

	public String getPassword() { return password; }
	public void setPassword(String password) { this.password = password; }

	public PersonaModel getPersona() { return persona; }
	public void setPersona(PersonaModel persona) { this.persona = persona; }
}
