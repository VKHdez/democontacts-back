package com.vktechnologies.democontact.infraestructure.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vktechnologies.democontact.infraestructure.models.ContactNumberModel;

public interface ContactNumberRepository extends JpaRepository<ContactNumberModel, Long> {

	Optional<ContactNumberModel> findByCountryCodeIdAndNumber(Long countryCodeId, String number);

	// Valida si existe un registro soft-borrado
	@Query(value = """
		SELECT CASE WHEN EXISTS (
			SELECT 1 FROM contact_numbers
			WHERE country_code_id = :countryCodeId
				AND number = :number
				AND deleted = 1
		) THEN 1 ELSE 0 END
	""", nativeQuery = true)
	int existsDeletedContactNumber(
		@Param("countryCodeId") Long countryCodeId,
		@Param("number") String number
	);

	@Modifying(clearAutomatically = true)
	@Query(value = """
		UPDATE contact_numbers
		SET deleted = 0, persona_id = :personaId
		WHERE country_code_id = :countryCodeId
			AND number = :number
	""", nativeQuery = true)
	void reactivateContactNumber(
		@Param("countryCodeId") Long countryCodeId,
		@Param("number") String number,
		@Param("personaId") Long personaId
	);

	// Reactiva los vinculos numero<->tipo que esten soft-borrados y sigan siendo requeridos
	@Modifying(clearAutomatically = true)
	@Query(value = """
		UPDATE contact_number_phone_types
		SET deleted = 0
		WHERE contact_number_id = :contactNumberId
			AND phone_number_type_id IN (:phoneNumberTypeIds)
			AND deleted = 1
	""", nativeQuery = true)
	void reactivatePhoneTypes(
		@Param("contactNumberId") Long contactNumberId,
		@Param("phoneNumberTypeIds") List<Long> phoneNumberTypeIds
	);

	// Soft-borra los vinculos activos que ya no esten en la lista deseada
	@Modifying(clearAutomatically = true)
	@Query(value = """
		UPDATE contact_number_phone_types
		SET deleted = 1
		WHERE contact_number_id = :contactNumberId
			AND phone_number_type_id NOT IN (:phoneNumberTypeIds)
			AND deleted = 0
	""", nativeQuery = true)
	void deactivateOtherPhoneTypes(
		@Param("contactNumberId") Long contactNumberId,
		@Param("phoneNumberTypeIds") List<Long> phoneNumberTypeIds
	);

	// Inserta los pares numero<->tipo que no tienen ninguna fila todavia (ni activa ni borrada)
	@Modifying(clearAutomatically = true)
	@Query(value = """
		INSERT INTO contact_number_phone_types (contact_number_id, phone_number_type_id, deleted)
		SELECT :contactNumberId, pt.id, 0
		FROM phone_number_types pt
		WHERE pt.id IN (:phoneNumberTypeIds)
			AND NOT EXISTS (
				SELECT 1 FROM contact_number_phone_types cnpt
				WHERE cnpt.contact_number_id = :contactNumberId
					AND cnpt.phone_number_type_id = pt.id
			)
	""", nativeQuery = true)
	void insertMissingPhoneTypes(
		@Param("contactNumberId") Long contactNumberId,
		@Param("phoneNumberTypeIds") List<Long> phoneNumberTypeIds
	);

	@Modifying(clearAutomatically = true)
	@Query(value = """
		UPDATE contact_numbers
		SET deleted = 1
		WHERE persona_id = :personaId
	""", nativeQuery = true)
	void deactivateByPersonaId(@Param("personaId") Long personaId);

	@Modifying(clearAutomatically = true)
	@Query(value = """
		UPDATE contact_numbers
		SET deleted = 0
		WHERE persona_id = :personaId
	""", nativeQuery = true)
	void activateByPersonaId(@Param("personaId") Long personaId);
}
