package com.harshvardhanthosar.product_service.services;

import com.harshvardhanthosar.product_service.dtos.RegisterInventoryRequestBodyDTO;
import com.harshvardhanthosar.product_service.dtos.UpdateInventoryRequestBodyDTO;
import com.harshvardhanthosar.product_service.exceptions.ResourceAlreadyExistsException;
import com.harshvardhanthosar.product_service.exceptions.ResourceNotFoundException;
import com.harshvardhanthosar.product_service.models.Inventory;
import com.harshvardhanthosar.product_service.models.Product;
import com.harshvardhanthosar.product_service.repositories.InventoryRepository;
import com.harshvardhanthosar.product_service.repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InventoryService {

    final
    ProductRepository product_repository;
    final
    InventoryRepository inventory_repository;

    public InventoryService(ProductRepository product_repository, InventoryRepository inventory_repository) {
        this.product_repository = product_repository;
        this.inventory_repository = inventory_repository;
    }

    public Inventory register_inventory(RegisterInventoryRequestBodyDTO request_body) {
        int quantity = request_body.getQuantity();
        Long product_id = request_body.getProduct_id();

        Product product = product_repository.findById(product_id)
                .filter(p -> !p.getDeleted()) // Ensure product is not deleted
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + product_id + " not found or is deleted!"));

        // Check if an inventory already exists for the given product ID
        Optional<Inventory> found_inventory = inventory_repository.findByProduct_Id(product_id)
                .filter(i -> !i.getDeleted()); // Ensure inventory is not deleted

        if (found_inventory.isPresent()) {
            throw new ResourceAlreadyExistsException("Inventory for product with id " + product_id + " already exists!");
        }

        Inventory inventory = new Inventory();
        inventory.setQuantity(quantity);
        inventory.setProduct(product);
        inventory.setDeleted(false); // Ensure new inventory is not marked as deleted

        return inventory_repository.save(inventory);
    }

    public Inventory update_inventory(UpdateInventoryRequestBodyDTO request_body, Long id) {
        int quantity = request_body.getQuantity();
        Long product_id = request_body.getProduct_id();

        Inventory found_inventory = inventory_repository.findById(id)
                .filter(i -> !i.getDeleted()) // Ensure inventory is not deleted
                .orElseThrow(() -> new ResourceNotFoundException("Inventory with id " + id + " not found or is deleted!"));

        Product product = product_repository.findById(product_id)
                .filter(p -> !p.getDeleted()) // Ensure product is not deleted
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + product_id + " not found or is deleted!"));

        found_inventory.setQuantity(quantity);
        found_inventory.setProduct(product);

        return inventory_repository.save(found_inventory);
    }

    public Inventory delete_inventory(Long id) {
        Inventory found_inventory = inventory_repository.findById(id)
                .filter(i -> !i.getDeleted()) // Ensure inventory is not deleted
                .orElseThrow(() -> new ResourceNotFoundException("Inventory with id " + id + " not found or is deleted!"));

        // Perform soft delete
        found_inventory.setActive(false);
        found_inventory.setDeleted(true);

        return inventory_repository.save(found_inventory);
    }

    public Inventory get_inventory_by_id(Long id) {
        Inventory found_inventory = inventory_repository.findById(id)
                .filter(i -> !i.getDeleted()) // Ensure inventory is not deleted
                .orElseThrow(() -> new ResourceNotFoundException("Inventory with id " + id + " not found or is deleted!"));

        return found_inventory;
    }

    public Page<Inventory> get_inventories(int page, int limit) {
        PageRequest page_request = PageRequest.of(page - 1, limit);

        // Ensure only non-deleted inventories are fetched
        return inventory_repository.find_all_inventories(page_request);
    }

}
