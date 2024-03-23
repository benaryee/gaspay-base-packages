package com.rancard.mongo;


import com.rancard.dto.payload.PaymentMethodDto;
import com.rancard.enums.PaymentChannel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
@EqualsAndHashCode(callSuper = true)
public class PaymentMethod extends BaseMongoModel {
    private PaymentChannel paymentChannel;
    private String accountNumber;


    public static PaymentMethod fromDto(PaymentMethodDto paymentMethodDto) {
        return PaymentMethod.builder()
                .id(new ObjectId(paymentMethodDto.getId()))
                .paymentChannel(paymentMethodDto.getPaymentChannel())
                .accountNumber(paymentMethodDto.getAccountNumber())
                .build();
    }

    public PaymentMethodDto toDto() {
        return PaymentMethodDto.builder()
                .id(this.getId().toHexString())
                .paymentChannel(paymentChannel)
                .accountNumber(accountNumber)
                .build();
    }
}
