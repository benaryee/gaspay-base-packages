package com.rancard.basepackages.event;

import com.rancard.basepackages.dto.OrderDto;
import com.rancard.basepackages.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderEvent {
    private String message;
    private OrderStatus status;
    private OrderDto orderDto;
}
