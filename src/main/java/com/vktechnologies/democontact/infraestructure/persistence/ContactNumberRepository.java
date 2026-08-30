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

	/**
	 * This functions validates if there exists a current soft-deleted register in DB
	 */
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

	/**
	 * Reactiva de una sola pasada, via IN, cualquier vinculo numero<->tipo que ya
	 * existiera soft-borrado en contact_number_phone_types -- evita que Hibernate
	 * intente un INSERT duplicado sobre esa PK compuesta. Un id que no tenga vinculo
	 * soft-borrado simplemente no matchea ninguna fila (no-op para ese id).
	 */
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

	/**
	 * Soft-borra cualquier vinculo activo que ya NO este en la lista deseada.
	 */
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

	/**
	 * Inserta unicamente los pares numero<->tipo que no tienen NINGUNA fila todavia
	 * (ni activa ni soft-borrada) -- los que ya existen soft-borrados los maneja
	 * reactivatePhoneTypes, nunca ambos a la vez para el mismo par.
	 */
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
}
