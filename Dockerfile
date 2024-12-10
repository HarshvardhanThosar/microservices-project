# Stage 1: Build all services
FROM maven:3.8.6-openjdk-23 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Create runtime container
FROM openjdk:23-jdk-slim
WORKDIR /app

# Copy built JARs for each service
COPY --from=builder /app/inventory-service/target/*.jar inventory-service.jar
COPY --from=builder /app/product-service/target/*.jar product-service.jar
COPY --from=builder /app/api-gateway/target/*.jar api-gateway.jar
COPY --from=builder /app/service-registry/target/*.jar service-registry.jar

# Expose ports
EXPOSE 8052 8053 8761 8080

# Command to start all services (optional; usually handled by `docker-compose`)
CMD ["sh", "-c", "java -jar service-registry.jar & java -jar api-gateway.jar & java -jar product-service.jar & java -jar inventory-service.jar"]