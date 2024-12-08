package com.harshvardhanthosar.inventory_service.repositories;

import com.harshvardhanthosar.inventory_service.models.Inventory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    @Query("SELECT i FROM Inventory i WHERE i.deleted = false")
    Page<Inventory> find_all_inventories(Pageable pageable);

    /**
     * Finds an Inventory by the associated Product ID.
     *
     * @param productId the ID of the associated Product
     * @return an Optional containing the Inventory if found, or empty otherwise
     */
    Optional<Inventory> findByProduct_Id(Long productId);

}
