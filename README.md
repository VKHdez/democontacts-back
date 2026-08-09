# Democontact

A learning-purposes **Contacts API** — manages people (personas), their user accounts, and (eventually) their addresses and phone numbers. See [docs/product/index.md](docs/product/index.md) for the domain and [docs/architecture/index.md](docs/architecture/index.md) for the full architecture writeup.

The purpose of this project is to onboard into **Spring Boot** by mapping concepts already known from PHP frameworks like **Laravel** and **Yii2** onto their Spring equivalents.

## Coming from Laravel / Yii2

| Laravel / Yii2 | Spring Boot (this project) |
|---|---|
| Eloquent Model / ActiveRecord | JPA Entity (`infraestructure.models`, `@Entity`) |
| Migrations (`php artisan migrate`) | Flyway migrations (`src/main/resources/db/migration`) |
| Repository/Service classes (manual) | `JpaRepository` (free CRUD) + `@Service` classes (`domain.*.service`) |
| FormRequest validation | Request DTOs with Bean Validation (`@Valid`, `@NotBlank`, `@Pattern`, ...) |
| Service Container / DI | Spring `ApplicationContext` — beans (`@Component`, `@Service`, `@Repository`, `@RestController`) injected via constructor |
| Route + Controller | `@RestController` + `@GetMapping`/`@PostMapping` |
| `.env` | Environment variables read in `application.yaml`, plus `application-local.yaml` (gitignored) for local dev |
| `artisan` commands | Maven Wrapper commands (`mvnw.cmd` / `mvnw`) |

## Stack

- **Language/runtime:** Java 21
- **Framework:** Spring Boot — Spring Web MVC, Spring Data JPA, Spring Validation
- **Database:** SQL Server (JDBC via `mssql-jdbc`)
- **Migrations:** Flyway
- **Build tool:** Maven (via the Maven Wrapper)

## Architecture

Layered architecture, with the domain layer following **Clean Architecture** / **DDD**-inspired conventions: business logic (use cases, services) is isolated from delivery mechanisms (REST controllers) and from persistence details (JPA entities/repositories) — each layer only depends inward.

- `presentation.rest.v{n}.controllers` — REST controllers, request/response mapping, input validation.
- `domain.{context}.usecase` / `domain.{context}.service` — business logic and orchestration, framework-agnostic where possible.
- `infraestructure.persistence` — Spring Data JPA repositories.
- `infraestructure.models` — JPA entities mapped to the database schema.
- `infraestructure.dto` — request/response DTOs, decoupled from the entities.

Full details in [docs/architecture/index.md](docs/architecture/index.md).

## Getting started

### Prerequisites

- JDK 21
- A reachable SQL Server database — **provisioning and credentials are on you**, this repo does not include or manage a database instance.

### Database

Connection details are read from environment variables (`DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_USER`, `DB_PASSWORD`) in `src/main/resources/application.yaml` — never hardcode real values there.

For local development, create `src/main/resources/application-local.yaml` (gitignored) with your real connection details, and activate it with the `local` Spring profile.

Schema changes are managed entirely through Flyway migrations under `src/main/resources/db/migration` — Hibernate never auto-generates or alters the schema (`ddl-auto: validate`).

### Commands

Use the Maven Wrapper (`mvnw.cmd` on Windows / `mvnw` on Unix shells):

```
# Build (compile + test)
mvnw.cmd clean package

# Run locally with the `local` profile (reads application-local.yaml)
$env:SPRING_PROFILES_ACTIVE = "local"
mvnw.cmd spring-boot:run

# Run the full test suite
mvnw.cmd test

# Run a single test class
mvnw.cmd test -Dtest=DemocontactApplicationTests
```

## Documentation

- [docs/product/index.md](docs/product/index.md) — what the app does, domain entities and business rules
- [docs/architecture/index.md](docs/architecture/index.md) — layers, tech stack, conventions
- [docs/architecture/db.md](docs/architecture/db.md) — database schema
