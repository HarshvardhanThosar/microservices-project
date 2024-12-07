package com.harshvardhanthosar.product_service.controllers;

import com.harshvardhanthosar.product_service.dtos.ResponseBodyDTO;
import com.harshvardhanthosar.product_service.dtos.UpdateProductRequestBodyDTO;
import com.harshvardhanthosar.product_service.exceptions.ResourceNotFoundException;
import com.harshvardhanthosar.product_service.models.Product;
import com.harshvardhanthosar.product_service.dtos.RegisterProductRequestBodyDTO;
import com.harshvardhanthosar.product_service.services.ProductService;
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
@RequestMapping("/products")
public class ProductController {

    private final ProductService product_service;

    public ProductController(ProductService product_service) {
        this.product_service = product_service;
    }

    private String build_page_url(String baseUrl, String search, int page, int limit) {
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

        if (search != null && !search.trim().isEmpty()) {
            builder.replaceQueryParam("search", search);
        } else {
            builder.replaceQueryParam("search");
        }

        return builder.build().encode().toUriString();
    }

    @PostMapping("/")
    public ResponseEntity<ResponseBodyDTO<Product>> register_product(@RequestBody RegisterProductRequestBodyDTO request_body) {
        Product registered_product = product_service.register_product(request_body);
        String message = "Product registered successfully!";
        HttpStatus status = HttpStatus.CREATED;
        ResponseBodyDTO<Product> response_body = new ResponseBodyDTO<>(message, registered_product, status.value(), null);

        return new ResponseEntity<>(response_body, status);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseBodyDTO<Product>> update_product_by_id(@RequestBody UpdateProductRequestBodyDTO request_body, @PathVariable Long id) {
        Product registered_product = product_service.update_product_by_id(request_body, id);
        String message = "Product updated successfully!";
        HttpStatus status = HttpStatus.OK;
        ResponseBodyDTO<Product> response_body = new ResponseBodyDTO<>(message, registered_product, status.value(), null);

        return new ResponseEntity<>(response_body, status);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseBodyDTO<Product>> delete_product_by_id(@PathVariable Long id) {
        Product deleted_product = product_service.delete_product_by_id(id);
        String message = "Product deleted successfully!";
        HttpStatus status = HttpStatus.OK;
        ResponseBodyDTO<Product> response_body = new ResponseBodyDTO<>(message, deleted_product, status.value(), null);

        return new ResponseEntity<>(response_body, status);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseBodyDTO<Product>> get_product_by_id(@PathVariable Long id) {
        Product product = product_service.get_product_by_id(id);
        String message = "Product fetched successfully!";
        HttpStatus status = HttpStatus.OK;
        ResponseBodyDTO<Product> response_body = new ResponseBodyDTO<>(message, product, status.value(), null);

        return new ResponseEntity<>(response_body, status);
    }

    @GetMapping("/")
    public ResponseEntity<ResponseBodyDTO<List<Product>>> get_products(
            @RequestParam(required = false, name = "search") String search,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "5") int limit) {
        Page<Product> products_page = product_service.get_products(search, page, limit);

        // generate metadata
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("current_page", products_page.getNumber() + 1);
        metadata.put("total_items", products_page.getTotalElements());
        metadata.put("total_pages", products_page.getTotalPages());
        metadata.put("limit", limit);

        // construct base URL
        String baseUrl = ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString();

        // generate prev_page and next_page URLs
        metadata.put("previous_page", (page > 1) ? build_page_url(baseUrl, search, page - 1, limit) : null);
        metadata.put("next_page",
                (page < products_page.getTotalPages()) ? build_page_url(baseUrl, search, page + 1, limit)
                        : null);

        List<Product> products = products_page.getContent();
        String message = "Products fetched successfully!";
        HttpStatus status = HttpStatus.OK;
        ResponseBodyDTO<List<Product>> response_body = new ResponseBodyDTO<>(message, products, status.value(), metadata);

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

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseBodyDTO<Object>> handleGeneralException(Exception e) {
        String message = "An unexpected error has occurred!";
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ResponseBodyDTO<Object> response_body = new ResponseBodyDTO<>(message, null, status.value(), null);

        return new ResponseEntity<>(response_body, status);
    }
}