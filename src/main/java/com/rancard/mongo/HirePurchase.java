package com.rancard.mongo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
@EqualsAndHashCode(callSuper = true)
public class HirePurchase<S,R> extends BaseMongoModel {

    @Indexed
    private String agreementId;

    @Indexed
    @DBRef(db = "auth")
    private User user;

    private BigDecimal amount;
    private BigDecimal outstandingAmount;
    private LocalDateTime dueDate;
    private boolean isPaid;

    @DBRef(db = "payment-service")
    private List<Payment<S,R>> paymentIds;

}
