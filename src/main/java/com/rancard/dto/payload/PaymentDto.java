/*(C) Gaspay App 2024 */
package com.rancard.dto.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rancard.enums.PaymentStatus;
import com.rancard.enums.PaymentType;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.rancard.mongo.Payment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentDto<S, R> {
    private String id;
    private String paymentId;
    private String walletId;
    private BigDecimal amount;
    private String currency;
    private PaymentStatus status;
    private PaymentType paymentType;
    private String sessionId;
    private S sender;
    private R recipient;
    private LocalDateTime createdAt;
    
    public static <S, R> PaymentDto<S, R> from(Payment<S, R> payment) {
        return PaymentDto.<S, R>builder()
                .id(payment.getIdString())
                .paymentId(payment.getPaymentId())
                .walletId(payment.getWalletId())
                .amount(payment.getAmount())
                .currency(payment.getCurrency())
                .sender(payment.getSender())
                .recipient(payment.getRecipient())
                .status(payment.getStatus())
                .paymentType(payment.getPaymentType())
                .createdAt(payment.getCreated())
                .sessionId(payment.getSessionId())
                .build();
    }
}
