package com.rancard.event;

import com.rancard.dto.payload.OrderDto;
import com.rancard.dto.payload.PaymentDto;
import com.rancard.enums.OrderStatus;
import com.rancard.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentEvent {
    private String message;
    private PaymentStatus status;
    private PaymentDto paymentDto;
}
