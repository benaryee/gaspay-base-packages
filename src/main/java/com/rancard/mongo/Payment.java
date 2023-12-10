package com.rancard.mongo;


import com.rancard.dto.payload.PaymentDto;
import com.rancard.enums.PaymentStatus;
import com.rancard.enums.PaymentType;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@Document
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment extends BaseMongoModel {

        private String paymentId;
        private String walletId;
        private BigDecimal amount;
        private String currency;
        private PaymentStatus status;
        private PaymentType paymentType;
        private String sessionId;

        public PaymentDto toDto() {
                return PaymentDto.builder()
                        .paymentId(paymentId)
                        .walletId(walletId)
                        .amount(amount)
                        .currency(currency)
                        .status(status)
                        .paymentType(paymentType)
                        .sessionId(sessionId)
                        .build();
        }
}
