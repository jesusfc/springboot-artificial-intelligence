# Description: Dockerfile for the Spring Boot application
FROM eclipse-temurin:21-jdk

# Set metadata

LABEL maintainer="jfcaraballo@gmail.com"
LABEL version="0.0.1"
LABEL description="Dockerfile for the Spring Boot application"

# Set the working directory
WORKDIR /application
COPY /target/*.jar /application/app.jar

# Set Expose port, just for information
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
