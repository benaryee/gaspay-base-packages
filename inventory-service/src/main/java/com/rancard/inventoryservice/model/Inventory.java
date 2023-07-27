package com.rancard.inventoryservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Inventory {

    private String skuCode;
    private Integer quantity;
}
