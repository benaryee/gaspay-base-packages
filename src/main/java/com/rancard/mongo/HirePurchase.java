package com.rancard.mongo;


import com.rancard.dto.payload.HirePurchaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;
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
    private List<Payment<S,R>> payments;


    //TODO - Return
    public static <S, R> HirePurchase<S, R> fromDto(HirePurchaseDto<S,R> hirePurchaseDto){
        return HirePurchase.<S, R>builder()
                .id(new ObjectId(hirePurchaseDto.getId()))
                .agreementId(hirePurchaseDto.getAgreementId())
                .user(User.fromDto(hirePurchaseDto.getUser()))
                .amount(hirePurchaseDto.getAmount())
                .outstandingAmount(hirePurchaseDto.getOutstandingAmount())
                .dueDate(hirePurchaseDto.getDueDate())
                .isPaid(hirePurchaseDto.isPaid())
                .build();
    }

    public HirePurchaseDto<S,R> toDto(){
        return HirePurchaseDto.<S,R>builder()
                .id(getIdString())
                .agreementId(agreementId)
                .user(user.toDto())
                .amount(amount)
                .outstandingAmount(outstandingAmount)
                .dueDate(dueDate)
                .isPaid(isPaid)
                .build();
    }
}
