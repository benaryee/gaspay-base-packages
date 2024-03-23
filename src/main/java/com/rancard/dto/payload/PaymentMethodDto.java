package com.rancard.dto.payload;

import com.rancard.enums.PaymentChannel;
import lombok.*;
@Data
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class PaymentMethodDto {
    private String id;
    private PaymentChannel paymentChannel;
    private String accountNumber;
}
