# STARWARS FAN API
API for displays data from a third-party API [SWAPI](https://swapi.dev) 

## Description
REST API built with Spring Boot 3, exposing data from the public SWAPI with additional filtering and pagination features.
Includes full test coverage (unit + integration), Swagger UI, and Docker support.

## Project Objectives

- Expose REST endpoints (`/api/people`, `/api/planets`) with pagination, search, and sorting.
- Internal SWAPI client using `WebClient`.
- Unit tests for controllers and services.
- Integration tests using WireMock with Testcontainers.
- Docker Compose to run the application locally.

##  Requirements

- Java 21
- Maven 3.9+
- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)

---

## Build project
```bash
mvn clean install
```
## Run unit Test
```bash
mvn clean test
```

## Run Integration Test
```bash
mvn verify
```

## Run project with Maven ()
```bash
mvn spring-boot:run
```

## Run project with Docker ()¡
```bash
docker-compose up --build
```
#### API → http://localhost:8080/api
#### WIREMOCK → http://localhost:8081/api

## Swagger / OpenAPI Documentation

#### Swagger UI	http://localhost:8080/swagger-ui/index.html
#### OpenAPI JSON	http://localhost:8080/v3/api-docs
