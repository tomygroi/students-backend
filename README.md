# students-api

API REST simple con Spring Boot 4, H2 y Lombok. CRUD de `Student` en capas
(controller, service, repository, entity y dto).

## Requisitos
- Java 17+
- Maven 3.9+

## Ejecutar
```bash
mvn spring-boot:run
```
La API queda en `http://localhost:8080/api/students`.

## Endpoints
| Método | URL                  | Descripción            |
|--------|----------------------|------------------------|
| GET    | /api/students        | Listar estudiantes     |
| GET    | /api/students/{id}   | Obtener por id         |
| POST   | /api/students        | Crear estudiante       |
| PUT    | /api/students/{id}   | Actualizar estudiante  |
| DELETE | /api/students/{id}   | Eliminar estudiante    |

## Ejemplo
```bash
curl -X POST http://localhost:8080/api/students \
  -H "Content-Type: application/json" \
  -d '{"firstName":"Ana","lastName":"Pérez","email":"ana@mail.com","age":20}'
```

## Estructura
```
com.example.studentsapi
├── controller   StudentController
├── service      StudentService / StudentServiceImpl
├── repository   StudentRepository
├── entity       Student
├── dto          StudentDto
└── exception    ResourceNotFoundException / GlobalExceptionHandler
```
