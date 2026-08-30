ALTER TABLE dbo.contact_number_phone_types
ADD deleted BIT NOT NULL CONSTRAINT DF_contact_number_phone_types_deleted DEFAULT 0;
