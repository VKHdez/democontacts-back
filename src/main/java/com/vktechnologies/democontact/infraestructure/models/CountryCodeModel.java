package com.vktechnologies.democontact.infraestructure.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "country_codes")
public class CountryCodeModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "country_name", nullable = false, length = 100, unique = true)
	private String countryName;

	@Column(name = "iso_code", nullable = false, length = 2, unique = true)
	private String isoCode;

	@Column(name = "dial_code", nullable = false, length = 5)
	private String dialCode;

	public Long getId() { return id; }
	public String getCountryName() { return countryName; }
	public String getIsoCode() { return isoCode; }
	public String getDialCode() { return dialCode; }
}
