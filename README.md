[License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)
![Java 17](https://img.shields.io/badge/Java-17-007396?style=for-the-badge&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.7+-6DB33F?style=for-the-badge&logo=springboot)
![Spring Security](https://img.shields.io/badge/Spring%20Security-Secure-6DB33F?style=for-the-badge&logo=springsecurity)
![Spring Cloud Config](https://img.shields.io/badge/Spring%20Cloud%20Config-Client-6DB33F?style=for-the-badge&logo=spring)
![Spring Data JPA](https://img.shields.io/badge/Spring%20Data%20JPA-Repository-6DB33F?style=for-the-badge&logo=spring)
![Spring Boot Validation](https://img.shields.io/badge/Spring%20Boot-Validation-6DB33F?style=for-the-badge&logo=spring)
![Postgres SQL](https://img.shields.io/badge/PostgreSQL-Database-336791?style=for-the-badge&logo=postgresql)
![Mockito](https://img.shields.io/badge/Mockito-Testing-green?style=for-the-badge&logo=java)
![JUnit](https://img.shields.io/badge/JUnit-5-25A162?style=for-the-badge&logo=junit5)
![Logging](https://img.shields.io/badge/Logging-SLF4J%20%2F%20Logback-blue?style=for-the-badge&logo=java)
![Swagger](https://img.shields.io/badge/Swagger-API%20Docs-green?style=for-the-badge&logo=swagger)

# Auth Service
## Introduction
This is the authentication service with Java 17 and Spring Boot. This service works together with the order, order processing, notification, and config services in a microservice architecture.
## Purpose
This project is a demo for the Melita interview process, it's main purpose is to showcase microservice architecture design and Spring Cloud Config Client usage with RabbitMQ messaging.
## Features
- issues JWT tokens 
- validates authentication 
- provides user details
## Service structure
   ```
   auth-service/
   ├── src/main/java/com/melita/authservice
   │   │   ├── config/
   │   │   ├── controller/
   │   │   ├── model/
   │   │   ├── repository/
   │   │   ├── security/
   │   │   ├── service/
   │   ├─── resources/
   │       ├── application.yml
   │       ├── bootstrap.yml
   ├──src/test/java/com/melita/authservice
   ```
## Installation
1. Pre-requisites
   Install Java17, PostgreSQL DB. Test the DB connection.
2. Clone the repository from GitHub.
    ```shell
    git clone  https://github.com/Dzsodie/AuthService.git
    ```
## Starting the application
1. First start the Config Service https://github.com/Dzsodie/ConfigService.git .
2. Check if the properties are loaded from config service properly.
   http://localhost:8888/AuthService/default
3. Start the service with the following command.
    ```shell
    mvn spring-boot:run
    ```
## API documentation
Swagger documentation is available at: http://localhost:8080/swagger/index.html after the application started successfully.
## Logging and monitoring
SLF4J is used for logging.
Log aggregation is not yet implemented, but Datadog offers an easy to implement solution with user-friendly monitoring interface.
## Testing
1. Mockito and JUnit5 is used for the unit testing.
2. Test coverage needs to be improved. Coverage report can be reached with this command.
    ```shell
    mvn jacoco:report
    ```
3. Tests can be run with the following command.
    ```shell
    mvn test
    ```
## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
