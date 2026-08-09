CREATE TABLE genders (
    id BIGINT IDENTITY(1,1) NOT NULL,
    name VARCHAR(20) NOT NULL,
    CONSTRAINT PK_genders PRIMARY KEY (id),
    CONSTRAINT UQ_genders_name UNIQUE (name)
);

INSERT INTO genders (name) VALUES ('MASCULINO'), ('FEMENINO'), ('OTRO');
