# Inventory API

[Daniel Duran](https://www.dan1el64.com/) · Java 17 · Spring Boot 3.4 · PostgreSQL / H2

An independent product-inventory demonstration with a layered controller/service/repository architecture. Includes request validation, OpenAPI, explicit error responses, service tests and HTTP contract tests. Pair it with the [Angular frontend](https://github.com/dan1el64/Inventario_frontend).

![Inventory interface](https://raw.githubusercontent.com/dan1el64/Inventario_frontend/main/docs/screenshots/inventory-desktop.png)

## Complete local demo

Install Docker with Compose and Git, then:

```sh
git clone https://github.com/dan1el64/Inventario_backend.git inventory-backend
cd inventory-backend
./scripts/demo.sh
```

The script downloads the public frontend into sibling folder `inventory-frontend` if absent, generates an ignored local-only database password and starts PostgreSQL, API and Nginx/Angular. No cloud account, real credentials or external database is used. Docker must be running; the first build downloads images and dependencies. Requires OpenSSL for local password generation.

- Interface: http://localhost:4200
- API: http://localhost:8080/api/v1/products
- Swagger: http://localhost:8080/swagger-ui/index.html
- Health: http://localhost:8080/actuator/health

Stop with `docker compose --profile demo down`. Local products persist in a Docker volume. To deliberately discard all demo products, use `docker compose --profile demo down -v`.

For manual configuration, copy `.env.example` to `.env` and choose a local password. Clone the frontend into the documented sibling path, then run `docker compose --profile demo up --build --wait`.

## Run without Docker

Requires JDK 17. H2 is an ephemeral local database seeded with three synthetic products:

```sh
./gradlew test bootJar --no-daemon
java -jar build/libs/inventory-app.jar --server.address=127.0.0.1
```

Start the frontend with `npm ci --ignore-scripts && npm start` in its own checkout. The development proxy forwards `/api` to localhost:8080. Browser requests use the same contract as the composed demo.

## API contract

| Method | Path | Outcome |
| --- | --- | --- |
| GET | /api/v1/products | List products |
| GET | /api/v1/products/{id} | Product or 404 |
| POST | /api/v1/products | Create, 201 |
| PUT | /api/v1/products/{id} | Replace editable product fields or 404 |
| DELETE | /api/v1/products/{id} | Delete or 404 |

Create/update body:

```json
{"name":"Sensor","category":"Laboratorio","price":12.5,"stock":3}
```

Name/category must be nonblank, price greater than zero and stock a nonnegative integer. The server assigns IDs; supplied IDs cannot overwrite an existing product during creation. Responses use `{ "success": true, "message": "...", "data": ... }`. Validation and malformed JSON return 400; missing products return 404. Error bodies include `success`, `message`, `status`, `statusCode` and `timestamp`.

## Verification

`./gradlew test` currently includes 12 tests: six service tests, five HTTP tests and one application-context test. They cover valid CRUD, missing products, malformed JSON, required fields and rejected updates preserving stored values. [GitHub Actions](https://github.com/dan1el64/Inventario_backend/actions) additionally builds the container and checks the PostgreSQL profile over HTTP. Consult the actual workflow result rather than inferring success from a badge.

The `prod` profile reads `DB_URL`, `DB_USER` and `DB_PASSWORD`; Docker supplies these consistently. It uses schema auto-update for demonstration only. This is not a hardened production API: no authentication, authorization, migrations or multi-user isolation. Do not expose the local demo publicly. For a separate ports-and-adapters example, see [Hexagonal Architecture Labs](https://github.com/dan1el64/hexagonal-architecture-labs).

MIT license.
