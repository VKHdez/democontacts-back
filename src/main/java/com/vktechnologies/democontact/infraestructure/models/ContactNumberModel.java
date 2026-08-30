package com.vktechnologies.democontact.infraestructure.models;

import java.time.LocalDateTime;
import java.util.List;

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
@Table(name = "contact_numbers")
@SoftDelete
public class ContactNumberModel {

	// Attributes

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 20)
	private String number;

	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at", nullable = false)
	private LocalDateTime updatedAt;

	// Relations

	@ManyToOne
	@JoinColumn(name = "persona_id", nullable = false)
	private PersonaModel persona;

	@ManyToOne
	@JoinColumn(name = "country_code_id", nullable = false)
	private CountryCodeModel countryCode;

	@SoftDelete
	@ManyToMany
	@JoinTable(
		name = "contact_number_phone_types",
		joinColumns = @JoinColumn(name = "contact_number_id"),
		inverseJoinColumns = @JoinColumn(name = "phone_number_type_id")
	)
	private List<PhoneNumberTypeModel> phoneNumberTypes;

	// Setters & Getters

	public Long getId() { return id; }

	public String getNumber() { return number; }
	public void setNumber(String number) { this.number = number; }

	public LocalDateTime getCreatedAt() { return createdAt; }
	public LocalDateTime getUpdatedAt() { return updatedAt; }

	public PersonaModel getPersona() { return persona; }
	public void setPersona(PersonaModel persona) { this.persona = persona; }

	public CountryCodeModel getCountryCode() { return countryCode; }
	public void setCountryCode(CountryCodeModel countryCode) { this.countryCode = countryCode; }

	public List<PhoneNumberTypeModel> getPhoneNumberTypes() { return phoneNumberTypes; }
	public void setPhoneNumberTypes(List<PhoneNumberTypeModel> phoneNumberTypes) { this.phoneNumberTypes = phoneNumberTypes; }
}
