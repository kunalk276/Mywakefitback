# Use a base image with Java
FROM openjdk:21-jdk-slim

# Set working directory
WORKDIR /app

# Copy the jar file
COPY target/wakefitfurniture-0.0.1-SNAPSHOT.jar app.jar

# Expose port (default for Spring Boot)
EXPOSE 8080

# Run the jar
ENTRYPOINT ["java", "-jar", "app.jar"]
