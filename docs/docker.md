# Run JobPilot with Docker

## Quick start

Install Docker Engine with the Compose plugin, or Docker Desktop. No host Java,
Maven, PostgreSQL or Chromium installation is needed.

```bash
cp .env.example .env
```

In `.env`, set `POSTGRES_PASSWORD` to the output of `openssl rand -hex 32`, and
`JWT_SECRET` to the output of `openssl rand -base64 32`. Keep the file private
(`chmod 600 .env`). The JWT key must be Base64-encoded and at least 32 random bytes.
`JWT_EXPIRATION` is in **milliseconds**; its default is one hour.

```bash
docker compose up --build -d --wait --wait-timeout 180
docker compose ps
docker compose logs -f app
```

The API is at `http://localhost:8080`. This is a backend; `/` does not serve a
website. Use the registration and login endpoints documented in the README.
The first build downloads Maven dependencies, Chromium and Linux packages and
can take several minutes. Subsequent builds reuse dependency and image caches.

## Services and configuration

`app` builds from source using Java 21 and runs a packaged JAR as user 10001.
It includes Chromium and its Linux dependencies, installed with the exact
Playwright dependency resolved from `pom.xml`. Maven and the JDK stay in the
build stage. Compose provides an init process and 1 GiB of shared memory for
the browser. Playwright currently launches Chromium with its default settings;
this setup does not enable Chromium's optional sandbox.

`db` runs PostgreSQL 17 with a named volume, `postgres-data`. Its port is private
to the Compose network. The app connects to hostname `db`, and waits for its
health check. App readiness also checks its database connection. Management
port 8081 binds to container loopback and is not published. Inspect it with:

```bash
docker compose exec app curl -fsS http://127.0.0.1:8081/actuator/health/readiness
```

`.env.example` lists the supported settings. Change `APP_PORT` for a host port
conflict. `APP_BIND_ADDRESS=127.0.0.1` limits access to this computer; choose
`0.0.0.0` when you deliberately want access from other machines.

The `docker` Spring profile overrides datasource and JWT settings without
changing `application.properties`. Run the image outside Compose by passing
`SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`,
`SPRING_DATASOURCE_PASSWORD`, `JWT_SECRET`, and optionally `JWT_EXPIRATION`. The image activates
the Docker profile by default. Use `JAVA_TOOL_OPTIONS` for JVM options, and
Spring's standard environment variables for additional application settings.
To add another profile, set `SPRING_PROFILES_ACTIVE=docker,extra` in a Compose
override; keep `docker` active so the container configuration remains applied.

The scheduled job imports still run every two hours and require outbound HTTPS.
The current app schedules imports in every replica; do not scale `app` beyond
one instance until scheduling is coordinated across replicas.

## Everyday operations

```bash
# Rebuild after source or pom.xml changes.
docker compose up -d --build --wait --wait-timeout 180

# Stop and remove containers/network; keep database data.
docker compose down

# Open a PostgreSQL shell.
docker compose exec db sh -c 'psql -U "$POSTGRES_USER" -d "$POSTGRES_DB"'

# Back up data before changing database versions or making destructive changes.
docker compose exec -T db sh -c 'pg_dump -U "$POSTGRES_USER" -d "$POSTGRES_DB"' > jobpilot.sql

# Restore into a suitable empty database.
docker compose exec -T db sh -c 'psql -v ON_ERROR_STOP=1 -U "$POSTGRES_USER" -d "$POSTGRES_DB"' < jobpilot.sql
```

`docker compose down --volumes` permanently deletes this stack's database.
The initial database name, user and password apply only to a **new** volume.
Changing them in `.env` does not update existing database credentials. Change
them using PostgreSQL and update the application together, or explicitly reset
disposable development data.

Image builds skip integration tests because they require a running database.
To run the existing test suite without host Java/Maven, build the `build` stage
and run Maven in that image on this stack's network (use test credentials and
a separate test database). Keep test configuration separate from real data.

## Extending the stack

Use `compose.override.yaml` for local additions; Compose loads it automatically.
For example, to connect a desktop SQL client without exposing PostgreSQL to the
network:

```yaml
services:
  db:
    ports:
      - "127.0.0.1:5432:5432"
```

New services such as Redis or a worker can join the existing default network
and use service names as hostnames. Add their own volumes and health checks;
add `depends_on: {service: {condition: service_healthy}}` only where needed.
There are no fixed container names, host paths, or externally shared networks.
Use `docker compose -p another-name ...` for isolated copies with different
`APP_PORT` values. Rebuild when upgrading Playwright; the browser installation
automatically follows its Maven dependency version.

For deployment-specific configuration, put changes in a separate file and run
`docker compose -f compose.yaml -f compose.production.yaml up -d --wait`.
Create that file for your deployment; it is not included as a generic template.
Build and publish a versioned image in CI, set `APP_IMAGE` to its registry tag
or digest, and deploy with `docker compose up -d --no-build --pull always --wait`.
Pin base images and PostgreSQL to reviewed digests for reproducible releases.

This is a production foundation, not a complete production deployment: configure
TLS/reverse proxy, resource limits, secret delivery, backups, monitoring and
schema migrations for your environment. `DDL_AUTO=update` preserves current
development behavior; switch to `validate` only after introducing migrations.
Environment credentials are visible to users with container-engine access;
for production use an orchestrator secret store or Spring config-tree files.

## Docker Desktop on Linux

Docker Engine (`default` context) and Docker Desktop (`desktop-linux` context)
use different daemons, images and volumes. `docker context ls` shows the current
selection. Use one consistently; changing contexts does not move your data.
Desktop requires accepting its terms in the first-run UI.

## References

- [Docker Compose startup ordering](https://docs.docker.com/compose/how-tos/startup-order/)
- [Playwright Java Docker guidance](https://playwright.dev/java/docs/docker)
- [Spring Boot health endpoints](https://docs.spring.io/spring-boot/reference/actuator/endpoints.html)
