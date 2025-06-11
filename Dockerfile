# --- Build stage ---
FROM openjdk:17.0.2-jdk AS build
WORKDIR /app

# Copy Gradle wrapper and config files
COPY gradlew build.gradle.kts settings.gradle.kts ./
COPY gradle ./gradle

# Copy source
COPY src ./src

# Build the application
RUN chmod +x gradlew && ./gradlew bootJar --no-daemon

# --- Run stage ---
FROM openjdk:17.0.2-jdk
WORKDIR /app

# Copy the built JAR
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]