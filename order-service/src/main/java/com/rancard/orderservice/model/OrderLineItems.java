package com.rancard.orderservice.model;

import lombok.*;


import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderLineItems {

    private Long id;
    private String skuCode;
    private BigDecimal price;
    private Integer quantity;
}
