package com.rancard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderPlacedEvent {
    private Order order;

    public OrderPlacedEvent(Object source, Order order) {
        super();
        this.order = order;
    }

}
