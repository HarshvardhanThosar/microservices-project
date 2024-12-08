package com.harshvardhanthosar.inventory_service.services;

import com.harshvardhanthosar.inventory_service.dtos.RegisterInventoryRequestBodyDTO;
import com.harshvardhanthosar.inventory_service.dtos.ResponseBodyDTO;
import com.harshvardhanthosar.inventory_service.dtos.UpdateInventoryRequestBodyDTO;
import com.harshvardhanthosar.inventory_service.exceptions.ResourceAlreadyExistsException;
import com.harshvardhanthosar.inventory_service.exceptions.ResourceNotFoundException;
import com.harshvardhanthosar.inventory_service.models.Inventory;
import com.harshvardhanthosar.inventory_service.models.Product;
import com.harshvardhanthosar.inventory_service.openfeign_client.ProductClientInterface;
import com.harshvardhanthosar.inventory_service.repositories.InventoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InventoryService {

    final
    InventoryRepository inventory_repository;

    final
    ProductClientInterface product_client_interface;

    public InventoryService(InventoryRepository inventory_repository, ProductClientInterface product_client_interface) {
        this.inventory_repository = inventory_repository;
        this.product_client_interface = product_client_interface;
    }

    public Inventory register_inventory(RegisterInventoryRequestBodyDTO request_body) {
        int quantity = request_body.getQuantity();
        Long product_id = request_body.getProduct_id();

        try {
            ResponseBodyDTO<Product> fetch_product_by_id_response = product_client_interface.get_product_by_id(product_id).getBody();
            Product product = fetch_product_by_id_response.data();

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
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Product with id " + product_id + " not found or is deleted!");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Inventory update_inventory(UpdateInventoryRequestBodyDTO request_body, Long id) {
        int quantity = request_body.getQuantity();
        Long product_id = request_body.getProduct_id();

        Inventory found_inventory = inventory_repository.findById(id)
                .filter(i -> !i.getDeleted()) // Ensure inventory is not deleted
                .orElseThrow(() -> new ResourceNotFoundException("Inventory with id " + id + " not found or is deleted!"));

        try {
            ResponseBodyDTO<Product> fetch_product_by_id_response = product_client_interface.get_product_by_id(product_id).getBody();
            Product product = fetch_product_by_id_response.data();

            found_inventory.setQuantity(quantity);
            found_inventory.setProduct(product);

            return inventory_repository.save(found_inventory);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Product with id " + product_id + " not found or is deleted!");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
