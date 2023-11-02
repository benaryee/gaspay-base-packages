package com.rancard.ussdapp.model.dto;

import com.rancard.ussdapp.model.payload.Address;
import com.rancard.ussdapp.model.payload.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private String orderId;
    private List<OrderItem> items;
    private double totalAmount;
    private String customerMsisdn;
    private String orderStatus;
    private Address shippingAddress;
    private LocalDateTime _created;
}
