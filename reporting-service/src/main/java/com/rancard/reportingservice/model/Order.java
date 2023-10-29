package com.rancard.reportingservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor

public class Order {

    private String orderId;
    private List<OrderItem> items;
    private double totalAmount;
    private String customerMsisdn;
    private String orderStatus;
    private String shippingAddress;
}
