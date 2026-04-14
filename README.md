# Employee Service API

REST API para gestión de empleados desarrollada con Spring Boot 3.

## Tecnologías
- Java 21
- Spring Boot 3
- Spring Data JPA
- H2 Database (en memoria)
- Swagger / OpenAPI 3
- JUnit 5 + Mockito

## Endpoints principales

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | /api/v1/empleados | Listar todos |
| GET | /api/v1/empleados/{id} | Obtener por ID |
| POST | /api/v1/empleados | Crear empleado |
| PUT | /api/v1/empleados/{id} | Actualizar empleado |
| DELETE | /api/v1/empleados/{id} | Eliminar empleado |
| GET | /api/v1/empleados/buscar?termino= | Buscar por nombre |
| GET | /api/v1/empleados/estado/{estado} | Filtrar por estado |

## Cómo ejecutar

```bash
./mvnw spring-boot:run
```

Swagger UI disponible en: http://localhost:8080/swagger-ui.html

## Pruebas

```bash
./mvnw test
```

5 pruebas unitarias con JUnit 5 y Mockito.