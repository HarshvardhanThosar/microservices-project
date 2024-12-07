package com.harshvardhanthosar.product_service.services;

import com.harshvardhanthosar.product_service.dtos.UpdateProductRequestBodyDTO;
import com.harshvardhanthosar.product_service.exceptions.ResourceNotFoundException;
import com.harshvardhanthosar.product_service.models.Product;
import com.harshvardhanthosar.product_service.dtos.RegisterProductRequestBodyDTO;
import com.harshvardhanthosar.product_service.repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ProductService {

    private final ProductRepository product_repository;

    public ProductService(ProductRepository product_repository) {
        this.product_repository = product_repository;
    }

    public Product register_product(RegisterProductRequestBodyDTO request_body) {
        String name = request_body.getName();
        String description = request_body.getDescription();
        BigDecimal price = request_body.getPrice();
        Boolean available = request_body.getActive();

        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setActive(available);
        product.setDeleted(false); // Ensure product is not marked as deleted on registration.

        return product_repository.save(product);
    }

    public Product update_product_by_id(UpdateProductRequestBodyDTO request_body, Long id) {
        Product found_product = product_repository
                .findById(id)
                .filter(product -> !product.getDeleted()) // Ensure not deleted.
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found!"));

        String name = request_body.getName();
        String description = request_body.getDescription();
        BigDecimal price = request_body.getPrice();
        Boolean available = request_body.getActive();

        found_product.setName(name);
        found_product.setDescription(description);
        found_product.setPrice(price);
        found_product.setActive(available);

        return product_repository.save(found_product);
    }

    public Product delete_product_by_id(Long id) {
        Product found_product = product_repository
                .findById(id)
                .filter(product -> !product.getDeleted()) // Ensure not deleted.
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found!"));

        // Perform soft delete
        found_product.setActive(false);
        found_product.setDeleted(true);

        return product_repository.save(found_product);
    }

    public Product get_product_by_id(Long id) {
        Product found_product = product_repository
                .findById(id)
                .filter(product -> !product.getDeleted()) // Ensure not deleted.
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found!"));

        return found_product;
    }

    public Page<Product> get_products(String search, int page, int limit) {
        PageRequest page_request = PageRequest.of(page - 1, limit);

        Page<Product> products = product_repository
                .search_by_name_or_description(search, page_request);

        return products;
    }
    
}