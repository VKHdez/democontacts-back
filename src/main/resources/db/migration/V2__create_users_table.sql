CREATE TABLE users (
    id BIGINT IDENTITY(1,1) NOT NULL,
    persona_id BIGINT NOT NULL,
    username VARCHAR(50) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    CONSTRAINT PK_users PRIMARY KEY (id),
    CONSTRAINT UQ_users_persona_id UNIQUE (persona_id),
    CONSTRAINT UQ_users_email UNIQUE (email),
    CONSTRAINT FK_users_persona FOREIGN KEY (persona_id) REFERENCES personas (id)
);
