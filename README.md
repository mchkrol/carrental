# CarRental

Spring Boot application for managing car rentals.

## Features
- CRUD operations for Cars, Rentals, and Customers
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
   git clone https://github.com/michalkrol/carrental.git
   cd carrental
2. Run the application:
   ./mvnw spring-boot:run
   # or, if you use local Maven
   mvn spring-boot:run
3. After startup, you can access:
   Swagger UI → http://localhost:8080/swagger-ui/index.html
   OpenAPI JSON → http://localhost:8080/v3/api-docs
   H2 Console → http://localhost:8080/h2-console
   (JDBC URL: jdbc:h2:mem:testdb, User: sa, Password: empty)

## Unit Tests
Run all unit tests:
./mvnw test
# or
mvn test
Unit tests are located in:
src/test/java/com/michalkrol/carrental/
