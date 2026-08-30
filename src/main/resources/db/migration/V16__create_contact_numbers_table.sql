CREATE TABLE contact_numbers (
    id BIGINT IDENTITY(1,1) NOT NULL,
    persona_id BIGINT NOT NULL,
    country_code_id BIGINT NOT NULL,
    number VARCHAR(20) NOT NULL,
    created_at DATETIME2 NOT NULL CONSTRAINT DF_contact_numbers_created_at DEFAULT SYSUTCDATETIME(),
    updated_at DATETIME2 NOT NULL CONSTRAINT DF_contact_numbers_updated_at DEFAULT SYSUTCDATETIME(),
    deleted BIT NOT NULL CONSTRAINT DF_contact_numbers_deleted DEFAULT 0,
    CONSTRAINT PK_contact_numbers PRIMARY KEY (id),
    CONSTRAINT FK_contact_numbers_persona FOREIGN KEY (persona_id) REFERENCES personas (id),
    CONSTRAINT FK_contact_numbers_country_code FOREIGN KEY (country_code_id) REFERENCES country_codes (id),
    CONSTRAINT UQ_contact_numbers_number UNIQUE (country_code_id, number)
);
