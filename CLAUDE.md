# Documentation

- `docs/product/index.md` — what this app is (a learning-purposes Contacts API managing people, their data, and addresses)
- `docs/architecture/index.md` — architecture of the Spring Boot / Java 21 API and its Cloud SQL Server-backed data layer

# Commands

Use the Maven wrapper (`mvnw.cmd` on Windows / `mvnw` on Unix shells) so the build uses the pinned Maven version.

```
# Build (compile + test)
mvnw.cmd clean package

# Run the app locally (requires DB_HOST, DB_NAME, DB_USER, DB_PASSWORD env vars set)
mvnw.cmd spring-boot:run

# Run the full test suite
mvnw.cmd test

# Run a single test class
mvnw.cmd test -Dtest=DemocontactApplicationTests

# Run a single test method
mvnw.cmd test -Dtest=DemocontactApplicationTests#contextLoads
```

There is no separate lint step configured (no Checkstyle/Spotless plugin in `pom.xml`).

## Database connection

`src/main/resources/application.yaml` reads the SQL Server host, port, database name, username, and password from environment variables (`DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_USER`, `DB_PASSWORD`) rather than hardcoding them. Never put real values for these back into `application.yaml` or any other tracked file.

For local development, `src/main/resources/application-local.yaml` holds the real connection details directly (it's gitignored — see `.gitignore`'s "Sensitive configuration" section). It's only loaded when the `local` Spring profile is active:
```powershell
$env:SPRING_PROFILES_ACTIVE = "local"
mvnw.cmd spring-boot:run
```
