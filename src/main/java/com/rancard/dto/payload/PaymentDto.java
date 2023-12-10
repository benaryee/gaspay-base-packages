package com.rancard.dto.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rancard.enums.PaymentStatus;
import com.rancard.enums.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentDto {
    private String paymentId;
    private String walletId;
    private BigDecimal amount;
    private String currency;
    private PaymentStatus status;
    private PaymentType paymentType;
    private String sessionId;
    private String senderId;
    private String recipientId;
}
