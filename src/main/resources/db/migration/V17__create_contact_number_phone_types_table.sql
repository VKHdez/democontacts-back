CREATE TABLE contact_number_phone_types (
    contact_number_id BIGINT NOT NULL,
    phone_number_type_id BIGINT NOT NULL,
    CONSTRAINT PK_contact_number_phone_types PRIMARY KEY (contact_number_id, phone_number_type_id),
    CONSTRAINT FK_cnpt_contact_number FOREIGN KEY (contact_number_id) REFERENCES contact_numbers (id),
    CONSTRAINT FK_cnpt_phone_number_type FOREIGN KEY (phone_number_type_id) REFERENCES phone_number_types (id)
);
