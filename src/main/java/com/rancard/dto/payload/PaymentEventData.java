package com.rancard.dto.payload;

import com.rancard.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentEventData {
    private String message;
    private PaymentStatus status;
    private PaymentDto paymentDto;
}
