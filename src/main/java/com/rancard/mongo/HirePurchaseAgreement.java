package com.rancard.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
@EqualsAndHashCode(callSuper = true)
public class HirePurchaseAgreement extends BaseMongoModel{

    private BigDecimal amount;
    private boolean isActive;
    private String couponCode;
}
