package com.rancard.event;

import com.rancard.dto.payload.OrderEventData;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;


@Getter
@Setter
public class OrderEvent extends ApplicationEvent {
    private OrderEventData orderEventData;

    public OrderEvent(Object source, OrderEventData orderEventData) {
        super(source);
        this.orderEventData = orderEventData;
    }

    public OrderEvent(OrderEventData orderEventData) {
        super(orderEventData);
        this.orderEventData = orderEventData;
    }
}
