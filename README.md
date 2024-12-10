# Microservices Design 5

#### Ecommerce product and inventory services

#### Submitted by - Harshvardhan Sushilkumar Thosar - A00325776

#### Submitted to - Prof. Jackie Stewart

## Overview

This project is built around the e-commerce theme and consists of two microservices:

1. Product Service: Manages product data and provides APIs for product-related operations.
2. Inventory Service: Tracks inventory levels and integrates with product data for comprehensive e-commerce functionality.

The project employs a service registry for service discovery and load balancing, and an API Gateway to provide a unified interface for clients.

## Challenges and Complexity

One of the key challenges faced during the development was handling the database configuration in a distributed microservices architecture. Initially, an in-memory H2 database was used. However:

- Issue: Each microservice started with its own instance of the H2 database. This resulted in two isolated databases, causing inconsistencies and failures during service interactions.
- Solution: The database was reconfigured to run in server mode using Docker. This allows all microservices to connect to a single shared database, ensuring consistency and avoiding duplication.

This design choice enhances reliability and simplifies the data flow between services.

## Setting Up the Environment

Prerequisites

- Docker installed and running on your machine.
- JDK 23 and Maven installed for running Spring Boot applications locally.

## Steps to Run the Project

### 1. Start the H2 Database (Server Mode)

The H2 database must be booted in server mode for all services to connect to a shared instance. Run the following command:

```sh
docker-compose up -d
```

> **Note:** This is a critical step. The H2 database container must be up and running before starting any service.

### 2. Start the Service Registry

The service-registry is responsible for service discovery and load balancing. Start it using your preferred method:

```sh
./mvnw spring-boot:run -Dspring-boot.run.arguments=--spring.profiles.active=local
```

The registry must be running to allow other services to register themselves.

### 3. Start the API Gateway

The api-gateway acts as the entry point for clients, abstracting the complexity of interacting with individual services. Start it after the service-registry:

```sh
./mvnw spring-boot:run -Dspring-boot.run.arguments=--spring.profiles.active=local
```

### 4. Start the Product Service

The product-service initializes the master data (e.g., predefined products) and creates the necessary schemas in the shared database. Start it next:

```sh
./mvnw spring-boot:run -Dspring-boot.run.arguments=--spring.profiles.active=local
```

### 5. Start the Inventory Service

The inventory-service relies on the schemas and tables created by the product-service. Start it last:

```sh
./mvnw spring-boot:run -Dspring-boot.run.arguments=--spring.profiles.active=local
```

## Key Features

- Service Discovery: The service-registry ensures dynamic discovery and load balancing of services.
- API Gateway: Provides a single entry point, simplifying client interactions with multiple services.
- Shared Database: The H2 database in server mode ensures consistency across all services.

## Example Architecture Workflow:

1. A client interacts with the API Gateway to request a product.
2. The API Gateway routes the request to the product-service.
3. The inventory-service is queried to check stock availability.

## Additional Notes

- If you encounter any issues with services not being discoverable, check the service registry logs for errors.
- Ensure that the H2 database container remains running as it is critical for the project’s functionality.
- The services should be stopped in the reverse order (inventory → product → API Gateway → service registry) to prevent runtime issues.

This documentation provides a detailed guide to understand, set up, and run the project efficiently.

contact: harshvardhanthosar@gmail.com
