package com.gaspay.ussdapp.model.payload;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItem {
    private Long id;
    private String skuCode;
    private BigDecimal price;
    private Integer quantity;
}
