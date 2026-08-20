package com.vktechnologies.democontact.infraestructure.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SoftDelete;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "personas")
@SoftDelete
public class PersonaModel {

	// Attributes

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 100)
	private String name;

	@Column(name = "first_name", nullable = false, length = 100)
	private String firstName;

	@Column(name = "last_name", nullable = false, length = 100)
	private String lastName;

	
	@Column(name = "birth_date")
	private LocalDate birthDate;

	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at", nullable = false)
	private LocalDateTime updatedAt;
	
	@ManyToOne
	@JoinColumn(name = "gender_id", nullable = false)
	private GenderModel gender;
	
	@ManyToMany
	@JoinTable(
		name = "persona_emergency_contacts",
		joinColumns = @JoinColumn(name = "persona_id"),
		inverseJoinColumns = @JoinColumn(name = "emergency_contact_persona_id")
	)
	private List<PersonaModel> emergencyContacts;

	// Setters & Getters

	public Long getId() { return id; }

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public String getFirstName() { return firstName; }
	public void setFirstName(String firstName) { this.firstName = firstName; }

	public String getLastName() { return lastName; }
	public void setLastName(String lastName) { this.lastName = lastName; }

	public GenderModel getGender() { return gender; }
	public void setGender(GenderModel gender) { this.gender = gender; }

	public LocalDate getBirthDate() { return birthDate; }
	public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }

	public LocalDateTime getCreatedAt() { return createdAt; }
	public LocalDateTime getUpdatedAt() { return updatedAt; }
	
	public List <PersonaModel> getEmergencyContacts() { return this.emergencyContacts; }
	public void setEmergencyContacts( List<PersonaModel> emergencyContacts) {this.emergencyContacts = emergencyContacts; }
}
