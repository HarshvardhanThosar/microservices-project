package com.harshvardhanthosar.inventory_service.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterProductRequestBodyDTO {

    private String name;

    private String description;

    private BigDecimal price;

    private Boolean active;

}