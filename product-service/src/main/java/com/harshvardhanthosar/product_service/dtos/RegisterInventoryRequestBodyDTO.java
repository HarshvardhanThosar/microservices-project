package com.harshvardhanthosar.product_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterInventoryRequestBodyDTO {

    private Long product_id;

    private int quantity;

}
