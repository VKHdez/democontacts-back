CREATE TABLE persona_emergency_contacts (
    persona_id BIGINT NOT NULL,
    emergency_contact_persona_id BIGINT NOT NULL,
    CONSTRAINT PK_persona_emergency_contacts PRIMARY KEY (persona_id, emergency_contact_persona_id),
    CONSTRAINT FK_pec_persona FOREIGN KEY (persona_id) REFERENCES personas (id),
    CONSTRAINT FK_pec_emergency_contact_persona FOREIGN KEY (emergency_contact_persona_id) REFERENCES personas (id),
    CONSTRAINT CK_pec_no_self_reference CHECK (persona_id <> emergency_contact_persona_id)
);
