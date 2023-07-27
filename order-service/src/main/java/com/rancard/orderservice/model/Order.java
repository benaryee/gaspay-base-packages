package com.rancard.orderservice.model;

import lombok.*;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private String orderNumber;
    private List<OrderLineItems> orderLineItemsList;
}
