package com.rancard.basepackages.mongo;


import com.rancard.basepackages.dto.OrderDto;
import com.rancard.basepackages.enums.OrderStatus;
import com.rancard.basepackages.payload.Address;
import com.rancard.basepackages.payload.OrderItem;
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

    public OrderDto toDto() {
        return OrderDto.builder()
                .orderId(orderId)
                .items(items)
                .totalAmount(totalAmount)
                .customerMsisdn(customerMsisdn)
                .orderStatus(orderStatus)
                .shippingAddress(shippingAddress)
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
                .agentId(orderDto.getAgentId())
                .build();
    }
}
