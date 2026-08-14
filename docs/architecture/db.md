# Database design

This document describes the database tables that back the entities defined in `docs/product/entities.md`, and how each business rule is enforced at the database level.

Primary keys: `BIGINT IDENTITY(1,1)` on every table.

This first pass covers `users`, `personas`, and the persona-to-persona emergency-contact relationship. Address and Contact Number tables will be added in a later pass.

## users

| column | type | constraints |
|---|---|---|
| id | BIGINT | PK, IDENTITY |
| persona_id | BIGINT | NOT NULL, UNIQUE, FK → personas.id |
| username | VARCHAR | NOT NULL |
| email | VARCHAR | NOT NULL, UNIQUE |
| password | VARCHAR | NOT NULL |
| created_at | DATETIME2 | NOT NULL, default `SYSUTCDATETIME()` |
| updated_at | DATETIME2 | NOT NULL, default `SYSUTCDATETIME()` |
| deleted | BIT | NOT NULL, default `0` |

Rules, and how they're enforced:

- A user can be related to only one persona → `persona_id` has a UNIQUE constraint.
- The email must be unique → UNIQUE constraint on `email`.
- Users are soft-deleted, not physically removed → `deleted` flag, toggled instead of a `DELETE`.

## personas

| column | type | constraints |
|---|---|---|
| id | BIGINT | PK, IDENTITY |
| name | VARCHAR | NOT NULL |
| first_name | VARCHAR | NOT NULL |
| last_name | VARCHAR | NOT NULL |
| gender_id | BIGINT | NOT NULL, FK → genders.id |
| birth_date | DATE | NULL |
| created_at | DATETIME2 | NOT NULL, default `SYSUTCDATETIME()` |
| updated_at | DATETIME2 | NOT NULL, default `SYSUTCDATETIME()` |
| deleted | BIT | NOT NULL, default `0` |

Rules:

- `name`, `first_name`, `last_name`, `gender_id` are mandatory (NOT NULL); `birth_date` is optional (NULL).
- Gender is a fixed catalog, not free text → `gender_id` FK to `genders`, not a VARCHAR column.
- A persona can have multiple addresses and numbers — not modeled yet in this doc; will be added when the Address/Contact Number tables are documented.
- A persona can have other, already-registered personas as emergency contacts → modeled below via `persona_emergency_contacts`.
- Personas are soft-deleted, not physically removed → `deleted` flag, toggled instead of a `DELETE`.

## genders

Catalog table, seeded via migration.

| column | type | constraints |
|---|---|---|
| id | BIGINT | PK, IDENTITY |
| name | VARCHAR | NOT NULL, UNIQUE |

Seed data: `MASCULINO`, `FEMENINO`, `OTRO`.

## persona_emergency_contacts

Self-referencing many-to-many join table.

| column | type | constraints |
|---|---|---|
| persona_id | BIGINT | PK (composite), FK → personas.id |
| emergency_contact_persona_id | BIGINT | PK (composite), FK → personas.id |

Rules:

- Composite PK on both columns prevents the same emergency contact being registered twice for the same persona.
- CHECK `persona_id <> emergency_contact_persona_id` — a persona cannot be its own emergency contact.
- Both columns FK to `personas.id`, so an emergency contact must already be a registered persona.
- Directional: row (A, B) means "B is an emergency contact of A" (not necessarily symmetric).
