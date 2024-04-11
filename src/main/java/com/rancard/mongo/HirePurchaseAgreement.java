package com.rancard.mongo;

import com.rancard.dto.payload.HirePurchaseAgreementDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
@EqualsAndHashCode(callSuper = true)
public class HirePurchaseAgreement extends BaseMongoModel{

    private String name;
    private BigDecimal amount;
    private boolean isActive;
    private String couponCode;

    public static HirePurchaseAgreement fromDto(HirePurchaseAgreementDto hirePurchaseAgreementDto){
        return HirePurchaseAgreement.builder()
                .id(new ObjectId(hirePurchaseAgreementDto.getId()))
                .name(hirePurchaseAgreementDto.getName())
                .amount(hirePurchaseAgreementDto.getAmount())
                .isActive(hirePurchaseAgreementDto.isActive())
                .build();
    }

    public HirePurchaseAgreementDto toDto(){
        return HirePurchaseAgreementDto.builder()
                .id(getIdString())
                .name(name)
                .amount(amount)
                .isActive(isActive)
                .build();
    }
}
