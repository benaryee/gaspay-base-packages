package com.rancard.event;

import com.rancard.dto.payload.PaymentEventData;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;


@Getter
@Setter
@Builder
public class PaymentEvent extends ApplicationEvent {
    private PaymentEventData paymentEventData;

    public PaymentEvent(Object source, PaymentEventData paymentEventData) {
        super(source);
        this.paymentEventData = paymentEventData;
    }

    public PaymentEvent(PaymentEventData orderEventData) {
        super(orderEventData);
        this.paymentEventData = orderEventData;
    }
}
