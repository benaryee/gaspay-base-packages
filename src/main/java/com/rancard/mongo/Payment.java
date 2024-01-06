/*(C) Gaspay App 2023 */
package com.rancard.mongo;

import com.rancard.dto.payload.PaymentDto;
import com.rancard.enums.PaymentStatus;
import com.rancard.enums.PaymentType;
import java.math.BigDecimal;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Payment extends BaseMongoModel {

    @Indexed private String paymentId;

    @Indexed private String extPaymentId;

    @Indexed private String walletId;
    private BigDecimal amount;
    private String currency;
    private PaymentStatus status;
    private PaymentType paymentType;

    @Indexed private String senderId;

    @Indexed private String recipientId;
    private String sessionId;

    public PaymentDto toDto() {
        return PaymentDto.builder()
                .paymentId(paymentId)
                .walletId(walletId)
                .amount(amount)
                .currency(currency)
                .senderId(senderId)
                .recipientId(recipientId)
                .status(status)
                .paymentType(paymentType)
                .sessionId(sessionId)
                .build();
    }

    public static Payment fromDto(PaymentDto paymentDto) {
        return Payment.builder()
                .paymentId(paymentDto.getPaymentId())
                .walletId(paymentDto.getWalletId())
                .amount(paymentDto.getAmount())
                .currency(paymentDto.getCurrency())
                .senderId(paymentDto.getSenderId())
                .recipientId(paymentDto.getRecipientId())
                .status(paymentDto.getStatus())
                .paymentType(paymentDto.getPaymentType())
                .sessionId(paymentDto.getSessionId())
                .build();
    }
}
