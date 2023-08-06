package com.rancard.paymentservice.model.mongo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@EqualsAndHashCode(callSuper = true)
public class Payment extends BaseMongoModel {

        private String paymentId;
        private String walletId;
        private String amount;
        private String currency;
        private String status;
        private PaymentType paymentType;
        private String sessionId;
}
