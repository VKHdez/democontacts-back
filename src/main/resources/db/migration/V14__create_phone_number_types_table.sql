CREATE TABLE phone_number_types (
    id BIGINT IDENTITY(1,1) NOT NULL,
    name VARCHAR(20) NOT NULL,
    CONSTRAINT PK_phone_number_types PRIMARY KEY (id),
    CONSTRAINT UQ_phone_number_types_name UNIQUE (name)
);

INSERT INTO phone_number_types (name) VALUES ('PERSONAL'), ('HOME'), ('OFFICE');
