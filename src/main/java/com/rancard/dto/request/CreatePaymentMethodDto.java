package com.rancard.dto.request;

import com.rancard.enums.PaymentChannel;
import lombok.*;

@Data
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class CreatePaymentMethodDto {

    private PaymentChannel paymentChannel;
    private String accountNumber;

}
