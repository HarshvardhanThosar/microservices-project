package com.harshvardhanthosar.inventory_service.services;

import com.harshvardhanthosar.inventory_service.dtos.RegisterInventoryRequestBodyDTO;
import com.harshvardhanthosar.inventory_service.dtos.UpdateInventoryRequestBodyDTO;
import com.harshvardhanthosar.inventory_service.exceptions.ResourceAlreadyExistsException;
import com.harshvardhanthosar.inventory_service.exceptions.ResourceNotFoundException;
import com.harshvardhanthosar.inventory_service.models.Inventory;
import com.harshvardhanthosar.inventory_service.models.Product;
import com.harshvardhanthosar.inventory_service.repositories.InventoryRepository;
import com.harshvardhanthosar.inventory_service.repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InventoryService {

    final
    InventoryRepository inventory_repository;

    final
    ProductRepository product_repository;

    public InventoryService(InventoryRepository inventory_repository, ProductRepository product_repository) {
        this.inventory_repository = inventory_repository;
        this.product_repository = product_repository;
    }

    public Inventory register_inventory(RegisterInventoryRequestBodyDTO request_body) {
        int quantity = request_body.getQuantity();
        Long product_id = request_body.getProduct_id();

        // Check if an inventory already exists for the given product ID
        Optional<Inventory> found_inventory = inventory_repository.findByProduct_Id(product_id)
                .filter(i -> !i.getDeleted()); // Ensure inventory is not deleted

        if (found_inventory.isPresent()) {
            throw new ResourceAlreadyExistsException("Inventory for product with id " + product_id + " already exists!");
        }

        Product found_product_by_id = product_repository.findById(product_id).orElseThrow(() -> new ResourceNotFoundException("Product with id " + product_id + " not found or is deleted!"));

        Inventory inventory = new Inventory();
        inventory.setQuantity(quantity);
        inventory.setProduct(found_product_by_id);
        inventory.setDeleted(false); // Ensure new inventory is not marked as deleted

        return inventory_repository.save(inventory);

    }

    public Inventory update_inventory(UpdateInventoryRequestBodyDTO request_body, Long id) {
        int quantity = request_body.getQuantity();
        Long product_id = request_body.getProduct_id();

        Inventory found_inventory = inventory_repository.findById(id)
                .filter(i -> !i.getDeleted()) // Ensure inventory is not deleted
                .orElseThrow(() -> new ResourceNotFoundException("Inventory with id " + id + " not found or is deleted!"));

        Product found_product_by_id = product_repository.findById(product_id).orElseThrow(() -> new ResourceNotFoundException("Product with id " + product_id + " not found or is deleted!"));

        found_inventory.setQuantity(quantity);
        found_inventory.setProduct(found_product_by_id);

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
