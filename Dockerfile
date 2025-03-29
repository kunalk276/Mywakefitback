# Stage 1: Build
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Run
FROM openjdk:21-jdk-slim
WORKDIR /app
# Copy the JAR from the build stage to the final image
COPY --from=builder /app/target/wakefitfurniture-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the application using the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]
