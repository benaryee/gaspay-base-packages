/*(C) Gaspay App 2023-2024 */
package com.rancard.mongo;

import com.rancard.dto.payload.PaymentDto;
import com.rancard.enums.PaymentStatus;
import com.rancard.enums.PaymentType;
import java.math.BigDecimal;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
@EqualsAndHashCode(callSuper = true)
public class Payment<S, R> extends BaseMongoModel {

    @Indexed private String paymentId;

    @Indexed private String extPaymentId;

    @Indexed private String walletId;
    private BigDecimal amount;
    private String currency;
    private PaymentStatus status;
    private PaymentType paymentType;

    @Indexed @DBRef private S sender;

    @Indexed @DBRef private R recipient;

    @Indexed private String sessionId;

    public <S, R> PaymentDto<S, R> toDto() {
        return PaymentDto.<S, R>builder()
                .id(getIdString())
                .paymentId(paymentId)
                .walletId(walletId)
                .amount(amount)
                .currency(currency)
                .sender((S) sender)
                .recipient((R) recipient)
                .status(status)
                .paymentType(paymentType)
                .createdAt(created)
                .sessionId(sessionId)
                .build();
    }

    public static <S, R> Payment<S, R> fromDto(PaymentDto<S, R> paymentDto) {
        return Payment.<S, R>builder()
                .id(new ObjectId(paymentDto.getId()))
                .paymentId(paymentDto.getPaymentId())
                .walletId(paymentDto.getWalletId())
                .amount(paymentDto.getAmount())
                .currency(paymentDto.getCurrency())
                .sender(paymentDto.getSender())
                .recipient(paymentDto.getRecipient())
                .status(paymentDto.getStatus())
                .paymentType(paymentDto.getPaymentType())
                .sessionId(paymentDto.getSessionId())
                .build();
    }
}
