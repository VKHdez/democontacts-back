package com.vktechnologies.democontact.infraestructure.models;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SoftDelete;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "addresses")
@SoftDelete
public class AddressModel {

	// Attributes

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 150)
	private String street;

	@Column(name = "external_number", nullable = false, length = 20)
	private String externalNumber;

	@Column(name = "interior_number", length = 20)
	private String interiorNumber;

	@Column(nullable = false, length = 100)
	private String country;

	@Column(nullable = false, length = 100)
	private String state;

	@Column(nullable = false, length = 100)
	private String city;

	@Column(name = "postal_code", nullable = false, length = 20)
	private String postalCode;

	@Column(name = "is_normal", nullable = false)
	private boolean isNormal;

	@Column(name = "is_billing", nullable = false)
	private boolean isBilling;

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

	// Setters & Getters

	public Long getId() { return id; }

	public String getStreet() { return street; }
	public void setStreet(String street) { this.street = street; }

	public String getExternalNumber() { return externalNumber; }
	public void setExternalNumber(String externalNumber) { this.externalNumber = externalNumber; }

	public String getInteriorNumber() { return interiorNumber; }
	public void setInteriorNumber(String interiorNumber) { this.interiorNumber = interiorNumber; }

	public String getCountry() { return country; }
	public void setCountry(String country) { this.country = country; }

	public String getState() { return state; }
	public void setState(String state) { this.state = state; }

	public String getCity() { return city; }
	public void setCity(String city) { this.city = city; }

	public String getPostalCode() { return postalCode; }
	public void setPostalCode(String postalCode) { this.postalCode = postalCode; }

	public boolean isNormal() { return isNormal; }
	public void setNormal(boolean isNormal) { this.isNormal = isNormal; }

	public boolean isBilling() { return isBilling; }
	public void setBilling(boolean isBilling) { this.isBilling = isBilling; }

	public LocalDateTime getCreatedAt() { return createdAt; }
	public LocalDateTime getUpdatedAt() { return updatedAt; }

	public PersonaModel getPersona() { return persona; }
	public void setPersona(PersonaModel persona) { this.persona = persona; }
}
