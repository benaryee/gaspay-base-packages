/*(C) Gaspay App 2024 */
package com.rancard.event;

import com.rancard.dto.payload.PaymentEventData;
import lombok.*;
import org.springframework.context.ApplicationEvent;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Builder
@Data
public class PaymentEvent<S, R> extends ApplicationEvent {
    private PaymentEventData<S, R> paymentEventData;

    public PaymentEvent() {
        super(new Object());
    }

    public PaymentEvent(Object source, PaymentEventData<S, R> paymentEventData) {
        super(source);
        this.paymentEventData = paymentEventData;
    }

    public PaymentEvent(PaymentEventData<S, R> paymentEventData) {
        super(paymentEventData);
        this.paymentEventData = paymentEventData;
    }
}
