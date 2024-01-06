/*(C) Gaspay App 2023 */
package com.rancard.dto.request;

import com.rancard.dto.payload.UserDto;
import java.io.Serializable;
import java.math.BigDecimal;

import com.rancard.enums.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MakePaymentDto implements Serializable {
    private UserDto sender;
    private UserDto recipient;
    private BigDecimal amount;
    private PaymentType paymentType;
    private String sessionId;

}
