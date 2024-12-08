package com.harshvardhanthosar.inventory_service.openfeign_client;

import com.harshvardhanthosar.inventory_service.dtos.RegisterProductRequestBodyDTO;
import com.harshvardhanthosar.inventory_service.dtos.ResponseBodyDTO;
import com.harshvardhanthosar.inventory_service.dtos.UpdateProductRequestBodyDTO;
import com.harshvardhanthosar.inventory_service.models.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("PRODUCT-SERVICE")
public interface ProductClientInterface {

    @PostMapping("/")
    public ResponseEntity<ResponseBodyDTO<Product>> register_product(@RequestBody RegisterProductRequestBodyDTO request_body);

    @PutMapping("/{id}")
    public ResponseEntity<ResponseBodyDTO<Product>> update_product_by_id(@RequestBody UpdateProductRequestBodyDTO request_body, @PathVariable Long id);

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseBodyDTO<Product>> delete_product_by_id(@PathVariable Long id);

    @GetMapping("/{id}")
    public ResponseEntity<ResponseBodyDTO<Product>> get_product_by_id(@PathVariable Long id);

    @GetMapping("/")
    public ResponseEntity<ResponseBodyDTO<List<Product>>> get_products(
            @RequestParam(required = false, name = "search") String search,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "5") int limit);

}
