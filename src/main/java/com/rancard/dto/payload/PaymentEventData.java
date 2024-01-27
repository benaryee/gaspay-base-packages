/*(C) Gaspay App 2024 */
package com.rancard.dto.payload;

import com.rancard.enums.Action;
import lombok.*;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@SuppressWarnings("unchecked")
public class PaymentEventData<S, R> {
    private String message;
    private Action status;
    private PaymentDto<S, R> paymentDto;
}
