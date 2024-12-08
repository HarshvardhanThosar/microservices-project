package com.harshvardhanthosar.inventory_service.dtos;

/**
 * A generic response body DTO using Java 23 features.
 *
 * @param message  Message describing the result
 * @param data     Data payload
 * @param status   HTTP status code
 * @param metadata Additional metadata
 */
public record ResponseBodyDTO<T>(
        String message,
        T data,
        int status,
        Object metadata
) {
    // Additional methods or utilities can be added here if needed
}