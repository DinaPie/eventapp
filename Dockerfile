# Use OpenJDK base image
FROM openjdk:17-jdk-slim

# Set the JAR file location
ARG JAR_FILE=target/*.jar

# Copy the jar file into the container
COPY ${JAR_FILE} app.jar

# Run the jar file
ENTRYPOINT ["java", "-jar", "/app.jar"]