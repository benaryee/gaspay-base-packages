package com.rancard.ussdapp.model.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemDto {
    private Long id;
    private String size;
    private String skuCode;
    private BigDecimal price;
    private float quantity;
}
