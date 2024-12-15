# Stage 1: Build Stage
FROM openjdk:21-jdk-slim AS build

# Set the working directory
WORKDIR /app

# Copy the Gradle wrapper and other Gradle files, if using Gradle (or replace with Maven files if using Maven)
COPY gradlew gradlew
COPY gradle gradle
COPY build.gradle settings.gradle /app/

# Download dependencies, helps with Docker caching
RUN ./gradlew dependencies --no-daemon || true

# Copy the source code
COPY src src

# Build the application
RUN ./gradlew bootJar --no-daemon

# Stage 2: Production Image
FROM eclipse-temurin:21-jre-alpine

# Set working directory
WORKDIR /app

# Copy only the built JAR from the build stage
COPY --from=build /app/build/libs/pwm-backend-0.0.1.jar /app/app.jar

# Expose the port your Spring Boot app will run on
EXPOSE 8080

# Set the command to run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
