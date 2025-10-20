# CarRental

Spring Boot application for managing car rentals.

## Features
- CRUD operations for Rentals, and Customers
- Validation for overlapping rentals
- H2 in-memory database
- Swagger UI documentation

## Technologies
- Java 21
- Spring Boot 3.5
- Spring Data JPA
- Lombok
- H2 Database
- Swagger (SpringDoc OpenAPI)

## How to run
1. Clone the repository:
   git clone https://github.com/michalkrol/carrental.git<br>
   cd carrental
2. Run the application:<br>
   ```bash ./mvnw spring-boot:run```<br>
   or, if you use local Maven<br>
   ```mvn spring-boot:run```<br>
   or using run configurations in IDE
4. After startup, you can access:<br>
   Swagger UI → http://localhost:8080/swagger-ui/index.html<br>
   OpenAPI JSON → http://localhost:8080/v3/api-docs<br>
   H2 Console → http://localhost:8080/h2-console<br>

## Unit Tests
Run all unit tests:<br>
```./mvnw test```<br>
or<br>
```mvn test```<br>
Unit tests are located in:<br>
```src/test/java/com/michalkrol/carrental/```
