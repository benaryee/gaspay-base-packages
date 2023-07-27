package com.rancard.order.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private String orderNumber;
    private List<OrderLineItems> orderLineItemsList;
}
