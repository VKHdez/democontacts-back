ALTER TABLE dbo.persona_emergency_contacts
ADD deleted BIT NOT NULL CONSTRAINT DF_persona_emergency_contacts_deleted DEFAULT 0;
