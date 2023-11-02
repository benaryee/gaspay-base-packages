package com.rancard.order.model;


import com.rancard.order.model.enums.OrderStatus;
import com.rancard.order.model.mongo.Address;
import com.rancard.order.model.mongo.BaseMongoModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Order extends BaseMongoModel {

    private String orderId;
    private List<OrderItem> items;
    private double totalAmount;
    private String customerMsisdn;
    private OrderStatus orderStatus;
    private Address shippingAddress;
    private String agentId;
}
