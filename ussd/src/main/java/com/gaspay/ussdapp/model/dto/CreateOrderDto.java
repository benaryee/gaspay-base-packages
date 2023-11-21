package com.gaspay.ussdapp.model.dto;

import com.gaspay.ussdapp.model.payload.Address;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CreateOrderDto {
    private String customerMsisdn;
    private List<OrderItemDto> orderItemsDtoList;
    private BigDecimal totalAmount;
    private Address shippingAddress;

}
