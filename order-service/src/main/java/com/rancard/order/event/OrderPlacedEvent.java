package com.rancard.order.event;

import com.rancard.order.model.Order;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;


@Getter
@Setter
public class OrderPlacedEvent extends ApplicationEvent {
    private Order order;

    public OrderPlacedEvent(Object source, Order order) {
        super(source);
        this.order = order;
    }

    public OrderPlacedEvent(Order order) {
        super(order);
        this.order = order;
    }
}
