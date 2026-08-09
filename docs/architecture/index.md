# Architecture

Democontact is a typical layered REST API built with **Spring Boot** and **Java 21**, storing its data in a **SQL Server** database hosted in the cloud (Azure SQL Database).

## Tech stack

- **Language/runtime:** Java 21
- **Framework:** Spring Boot (Spring Web MVC, Spring Data JPA, Spring Validation)
- **Database:** SQL Server, accessed via the `mssql-jdbc` driver, hosted in the cloud
- **Schema migrations:** Flyway (`flyway-sqlserver`), migrations versioned under `src/main/resources/db/migration`
- **Build tool:** Maven (via the Maven Wrapper)

## Layers

A typical request flows through the following layers:

1. **Presentation (REST controllers)** — `com.vktechnologies.democontact.presentation.rest.v{n}.controllers`
   Exposes versioned HTTP endpoints, handles request/response mapping, and delegates to the domain/service layer. Input validation is enforced here using Spring Validation (`@Valid` / Bean Validation annotations on request DTOs).

2. **Domain / Service layer**
   Contains business logic — e.g. rules for managing people, their personal data, and associated addresses. Coordinates persistence operations and enforces invariants that don't belong in the controller or the database.

3. **Persistence layer**
   Spring Data JPA repositories and entities mapped to SQL Server tables via the `SQLServerDialect`. `ddl-auto` is set to `validate`, meaning Hibernate never generates or alters schema — all schema changes must be expressed as Flyway migrations instead.

4. **Database (Cloud SQL Server)**
   The application connects to a SQL Server instance hosted in the cloud over JDBC (`mssql-jdbc`), with TLS encryption enabled on the connection. Flyway applies versioned migration scripts against this database on application startup to keep the schema in sync with the codebase.

## Configuration

Connection details, JPA settings, and Flyway settings live in `src/main/resources/application.yaml`. Environment-specific overrides (e.g. dev/staging/prod database endpoints) would typically use Spring profiles (`application-{profile}.yaml`) rather than hardcoding values, though this project currently uses a single default configuration file.

## Database design

See [db.md](./db.md) for the table definitions and how each entity rule from `docs/product/entities.md` is enforced at the database level.

## Conventions to follow when extending this app

- New REST endpoints go under a versioned `presentation.rest.v{n}.controllers` package.
- New tables/columns are introduced via a new Flyway migration script, never via Hibernate auto-DDL.
- Business logic belongs in a service/domain layer, not in controllers or repositories.
