# Task Management System (Spring Boot)

A minimal Spring Boot backend showcasing clean API structure, authentication with JWT, layered architecture, and H2 in-memory database.

### Tech Stack
- **Java 17**
- **Spring Boot 3** (Web, Security, Validation, Data JPA)
- **H2** in-memory database
- **JWT** for stateless authentication

### Project Structure
- `config` — security configuration, filters
- `controller` — REST controllers (e.g., authentication)
- `dto` — request/response models
- `model` — JPA entities (`User`, `Task`)
- `repository` — Spring Data JPA repositories
- `service` — business logic (auth, user details, JWT helpers)

### Configuration
Configured via `src/main/resources/application.properties`:
- H2 database (in-memory) with console enabled
- JWT secret and token expiration

Update these as needed:
```properties
# H2
spring.datasource.url=jdbc:h2:mem:taskdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
spring.jpa.hibernate.ddl-auto=update
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# JWT (set a long, random secret for local dev)
app.jwt.secret=change-this-secret-to-a-long-random-string
app.jwt.expiration-ms=3600000
```

### Run Locally
Prerequisites: Java 17

```bash
# From project root
./mvnw clean spring-boot:run
```

Application starts on `http://localhost:8080`.

H2 console available at `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:taskdb`).

### Authentication Flow
Current available endpoints for auth:

- `POST /auth/register` — Create a new user
  - Body:
    ```json
    { "email": "user@example.com", "password": "mypassword", "name": "John Doe" }
    ```
  - Response: `200 OK`

- `POST /auth/login` — Obtain JWT access token
  - Body:
    ```json
    { "email": "user@example.com", "password": "mypassword" }
    ```
  - Response:
    ```json
    { "accessToken": "<JWT>" }
    ```

Use the token in the `Authorization` header to access protected endpoints when available:
```
Authorization: Bearer <JWT>
```

### Example curl
```bash
# Register
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{"email":"user@example.com","password":"mypassword","name":"John Doe"}'

# Login (returns { accessToken })
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"user@example.com","password":"mypassword"}'
```

### Notes
- Passwords are hashed using BCrypt.
- Security is configured for stateless JWT authentication.

