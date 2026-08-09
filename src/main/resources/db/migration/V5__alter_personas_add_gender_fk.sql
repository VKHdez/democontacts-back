ALTER TABLE personas
    DROP COLUMN gender;

ALTER TABLE personas
    ADD gender_id BIGINT NOT NULL
        CONSTRAINT FK_personas_gender REFERENCES genders (id);
