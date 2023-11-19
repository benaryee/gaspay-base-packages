package com.rancard.basepackages.dto;

import com.rancard.basepackages.enums.OrderStatus;
import com.rancard.basepackages.payload.Address;
import com.rancard.basepackages.payload.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto implements Serializable {
    private String orderId;
    private List<OrderItem> items;
    private double totalAmount;
    private String customerMsisdn;
    private OrderStatus orderStatus;
    private Address shippingAddress;
    private String agentId;
    private LocalDateTime createdAt;
}
