ALTER TABLE dbo.users
ADD deleted BIT NOT NULL CONSTRAINT DF_users_deleted DEFAULT 0;
