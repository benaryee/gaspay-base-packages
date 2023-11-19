package com.rancard.basepackages.dto;


import com.rancard.basepackages.payload.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private String customerMsisdn;
    private List<OrderItemDto> orderItemsDtoList;
    private Address shippingAddress;
}
