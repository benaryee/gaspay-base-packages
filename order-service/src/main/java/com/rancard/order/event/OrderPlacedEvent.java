package com.rancard.order.event;

import com.rancard.basepackages.event.OrderEvent;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;


@Getter
@Setter
public class OrderPlacedEvent extends ApplicationEvent {
    private OrderEvent orderEvent;

    public OrderPlacedEvent(Object source, OrderEvent orderEvent) {
        super(source);
        this.orderEvent = orderEvent;
    }

    public OrderPlacedEvent(OrderEvent orderEvent) {
        super(orderEvent);
        this.orderEvent = orderEvent;
    }
}
