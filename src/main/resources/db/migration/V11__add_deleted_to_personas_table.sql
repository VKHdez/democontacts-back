ALTER TABLE dbo.personas
ADD deleted BIT NOT NULL CONSTRAINT DF_personas_deleted DEFAULT 0;
