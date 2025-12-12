# Stage 1: Build Stage
FROM eclipse-temurin:21-jdk AS build

# Set the working directory
WORKDIR /app

# Copy the Gradle wrapper and related files
COPY gradlew gradlew
COPY gradle gradle
COPY build.gradle settings.gradle /app/

# Make gradlew executable (required!)
RUN chmod +x gradlew

# Download dependencies
RUN ./gradlew dependencies --no-daemon || true

# Copy the source code
COPY src src

# Build the application
RUN ./gradlew bootJar --no-daemon

# Stage 2: Production Image
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# Copy only the built JAR
COPY --from=build /app/build/libs/*.jar /app/app.jar

# Expose the port your Spring Boot app runs on
EXPOSE 3000

# Start the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
