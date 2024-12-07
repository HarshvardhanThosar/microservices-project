package com.harshvardhanthosar.product_service.controllers;

import com.harshvardhanthosar.product_service.dtos.RegisterInventoryRequestBodyDTO;
import com.harshvardhanthosar.product_service.dtos.ResponseBodyDTO;
import com.harshvardhanthosar.product_service.dtos.UpdateInventoryRequestBodyDTO;
import com.harshvardhanthosar.product_service.exceptions.ResourceAlreadyExistsException;
import com.harshvardhanthosar.product_service.exceptions.ResourceNotFoundException;
import com.harshvardhanthosar.product_service.models.Inventory;
import com.harshvardhanthosar.product_service.services.InventoryService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/inventories")
public class InventoryController {

    final InventoryService inventory_service;

    public InventoryController(InventoryService inventory_service) {
        this.inventory_service = inventory_service;
    }

    private String build_page_url(String baseUrl, int page, int limit) {
        UriComponentsBuilder builder = ServletUriComponentsBuilder
                .fromUriString(baseUrl);

        if (page > 0) {
            builder.replaceQueryParam("page", page);
        } else {
            builder.replaceQueryParam("page");
        }

        if (limit > 0) {
            builder.replaceQueryParam("limit", limit);
        } else {
            builder.replaceQueryParam("limit");
        }

        return builder.build().encode().toUriString();
    }

    @PostMapping("/")
    public ResponseEntity<ResponseBodyDTO<Inventory>> register_inventory(@RequestBody RegisterInventoryRequestBodyDTO request_body) {
        Inventory data = inventory_service.register_inventory(request_body);
        String message = "Inventories registered successfully!";
        HttpStatus status = HttpStatus.OK;
        ResponseBodyDTO<Inventory> response_body = new ResponseBodyDTO<>(message, data, status.value(), null);

        return new ResponseEntity<>(response_body, status);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseBodyDTO<Inventory>> update_inventory(@RequestBody UpdateInventoryRequestBodyDTO request_body, @PathVariable Long id) {
        Inventory data = inventory_service.update_inventory(request_body, id);
        String message = "Inventories updated successfully!";
        HttpStatus status = HttpStatus.OK;
        ResponseBodyDTO<Inventory> response_body = new ResponseBodyDTO<>(message, data, status.value(), null);

        return new ResponseEntity<>(response_body, status);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseBodyDTO<Inventory>> delete_inventory(@PathVariable Long id) {
        Inventory data = inventory_service.delete_inventory(id);
        String message = "Inventories deleted successfully!";
        HttpStatus status = HttpStatus.OK;
        ResponseBodyDTO<Inventory> response_body = new ResponseBodyDTO<>(message, data, status.value(), null);

        return new ResponseEntity<>(response_body, status);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseBodyDTO<Inventory>> get_inventory_by_id(@PathVariable Long id) {
        Inventory data = inventory_service.get_inventory_by_id(id);
        String message = "Inventories fetched successfully!";
        HttpStatus status = HttpStatus.OK;
        ResponseBodyDTO<Inventory> response_body = new ResponseBodyDTO<>(message, data, status.value(), null);

        return new ResponseEntity<>(response_body, status);
    }

    @GetMapping("/")
    public ResponseEntity<ResponseBodyDTO<List<Inventory>>> get_inventories(
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "5") int limit
    ) {
        Page<Inventory> inventories_page = inventory_service.get_inventories(page, limit);

        // generate metadata
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("current_page", inventories_page.getNumber() + 1);
        metadata.put("total_items", inventories_page.getTotalElements());
        metadata.put("total_pages", inventories_page.getTotalPages());
        metadata.put("limit", limit);

        // construct base URL
        String baseUrl = ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString();

        // generate prev_page and next_page URLs
        metadata.put("previous_page", (page > 1) ? build_page_url(baseUrl, page - 1, limit) : null);
        metadata.put("next_page",
                (page < inventories_page.getTotalPages()) ? build_page_url(baseUrl, page + 1, limit)
                        : null);

        List<Inventory> inventories = inventories_page.getContent();
        String message = "Inventories fetched successfully!";
        HttpStatus status = HttpStatus.OK;
        ResponseBodyDTO<List<Inventory>> response_body = new ResponseBodyDTO<>(message, inventories, status.value(), metadata);

        return new ResponseEntity<>(response_body, status);
    }

    // Exception Handlers
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseBodyDTO<Object>> handleResourceNotFoundException(ResourceNotFoundException e) {
        String message = e.getMessage();
        HttpStatus status = HttpStatus.NOT_FOUND;
        ResponseBodyDTO<Object> response_body = new ResponseBodyDTO<>(message, null, status.value(), null);

        return new ResponseEntity<>(response_body, status);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ResponseBodyDTO<Object>> handleResourceAlreadyExistsException(ResourceAlreadyExistsException e) {
        String message = e.getMessage();
        HttpStatus status = HttpStatus.CONFLICT;
        ResponseBodyDTO<Object> response_body = new ResponseBodyDTO<>(message, null, status.value(), null);

        return new ResponseEntity<>(response_body, status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseBodyDTO<Object>> handleGeneralException(Exception e) {
        String message = "An unexpected error has occurred!";
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ResponseBodyDTO<Object> response_body = new ResponseBodyDTO<>(message, null, status.value(), null);

        return new ResponseEntity<>(response_body, status);
    }

}