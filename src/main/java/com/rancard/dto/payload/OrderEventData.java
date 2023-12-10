package com.rancard.dto.payload;

import com.rancard.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderEventData {
    private String message;
    private OrderStatus status;
    private OrderDto orderDto;
}
