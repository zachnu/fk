# Use a base image with a Java Runtime Environment (JRE)
FROM openjdk:17.0.2-jdk

# Expose the port your Spring Boot application listens on (default is 8080)
EXPOSE 8080

# Define an argument for the JAR file name
ARG JAR_FILE=target/*.jar

# Copy the JAR file from your build context into the container
COPY ${JAR_FILE} app.jar

# Set the entry point to run the Spring Boot application
ENTRYPOINT ["java","-jar","/app.jar"]