package com.rancard.ussdapp.model.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CreateOrderDto {
    private String customerMsisdn;
    private List<OrderItemDto> orderItemsDtoList;
    private BigDecimal totalAmount;
}
