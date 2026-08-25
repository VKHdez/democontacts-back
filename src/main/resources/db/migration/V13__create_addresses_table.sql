CREATE TABLE addresses (
    id BIGINT IDENTITY(1,1) NOT NULL,
    persona_id BIGINT NOT NULL,
    street VARCHAR(150) NULL,
    external_number VARCHAR(20) NOT NULL,
    interior_number VARCHAR(20) NULL,
    country VARCHAR(100) NOT NULL,
    state VARCHAR(100) NOT NULL,
    city VARCHAR(100) NOT NULL,
    postal_code VARCHAR(20) NOT NULL,
    is_normal BIT NOT NULL CONSTRAINT DF_addresses_is_normal DEFAULT 0,
    is_billing BIT NOT NULL CONSTRAINT DF_addresses_is_billing DEFAULT 0,
    created_at DATETIME2 NOT NULL CONSTRAINT DF_addresses_created_at DEFAULT SYSUTCDATETIME(),
    updated_at DATETIME2 NOT NULL CONSTRAINT DF_addresses_updated_at DEFAULT SYSUTCDATETIME(),
    deleted BIT NOT NULL CONSTRAINT DF_addresses_deleted DEFAULT 0,
    CONSTRAINT PK_addresses PRIMARY KEY (id),
    CONSTRAINT FK_addresses_persona FOREIGN KEY (persona_id) REFERENCES personas (id),
    CONSTRAINT CK_addresses_is_normal_or_billing CHECK (is_normal = 1 OR is_billing = 1)
);
