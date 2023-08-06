package com.rancard.paymentservice.model.mongo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@Document
@EqualsAndHashCode(callSuper = true)
public class Wallet extends BaseMongoModel {

    private String walletId;
    private BigDecimal balance;
    private String currency;
    private String status;
    private String password;
}
