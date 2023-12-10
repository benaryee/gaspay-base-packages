package com.rancard.mongo;


import com.rancard.dto.payload.OrderDto;
import com.rancard.enums.OrderStatus;
import com.rancard.payload.Address;
import com.rancard.payload.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;



@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Document
public class Order extends BaseMongoModel {

    private String orderId;
    private List<OrderItem> items;
    private double totalAmount;
    private String customerMsisdn;
    private OrderStatus orderStatus;
    private Address shippingAddress;
    private String agentId;
    private String paymentId;

    public OrderDto toDto() {
        return OrderDto.builder()
                .orderId(orderId)
                .items(items)
                .totalAmount(totalAmount)
                .customerMsisdn(customerMsisdn)
                .orderStatus(orderStatus)
                .shippingAddress(shippingAddress)
                .createdAt(created)
                .agentId(agentId)
                .build();
    }

    public static Order fromDto(OrderDto orderDto) {
        return Order.builder()
                .orderId(orderDto.getOrderId())
                .items(orderDto.getItems())
                .totalAmount(orderDto.getTotalAmount())
                .customerMsisdn(orderDto.getCustomerMsisdn())
                .orderStatus(orderDto.getOrderStatus())
                .shippingAddress(orderDto.getShippingAddress())
                .created(orderDto.getCreatedAt())
                .agentId(orderDto.getAgentId())
                .build();
    }
}
