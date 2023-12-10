package com.rancard.event;

import com.rancard.dto.payload.OrderEventData;
import lombok.*;
import org.springframework.context.ApplicationEvent;


@Getter
@Setter
@Builder
public class OrderEvent extends ApplicationEvent {
    private OrderEventData orderEventData;

    public OrderEvent() {
        super(new Object());
    }


    public OrderEvent(Object source, OrderEventData orderEventData) {
        super(source);
        this.orderEventData = orderEventData;
    }

    public OrderEvent(OrderEventData orderEventData) {
        super(orderEventData);
        this.orderEventData = orderEventData;
    }

}
