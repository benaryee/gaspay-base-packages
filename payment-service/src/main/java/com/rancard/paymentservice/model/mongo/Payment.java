package com.rancard.paymentservice.model.mongo;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment extends BaseMongoModel {

        private String paymentId;
        private String walletId;
        private String amount;
        private String currency;
        private PaymentStatus status;
        private PaymentType paymentType;
        private String sessionId;
}
