package com.rancard.event;

import com.rancard.dto.payload.OrderDto;
import com.rancard.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderEvent {
    private String message;
    private OrderStatus status;
    private OrderDto orderDto;
}
