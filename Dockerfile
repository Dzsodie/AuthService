FROM ubuntu:latest
LABEL authors="zsuzsannamakara"
FROM openjdk:17
WORKDIR /app
COPY target/order-service.jar auth-service.jar
ENTRYPOINT ["java", "-jar", "auth-service.jar"]