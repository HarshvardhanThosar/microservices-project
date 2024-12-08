package com.harshvardhanthosar.inventory_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateInventoryRequestBodyDTO {

    private Long product_id;

    private int quantity;
    
}
