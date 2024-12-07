# Stage 1: Build the JAR with Maven and Java 23
FROM openjdk:23-ea-1-jdk-slim AS builder
WORKDIR /app

# Install Maven
RUN apt-get update && apt-get install -y maven

# Copy the application source code
COPY . .

# Build the application
RUN mvn clean package

# Stage 2: Run the application
FROM openjdk:23-ea-1-jdk-slim
WORKDIR /app

# Copy the built JAR file from the builder stage
COPY --from=builder /app/target/api-gateway-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the application runs on
EXPOSE 8080

# Set the entry point to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]