package com.rancard.event;

import com.rancard.dto.payload.OrderDto;
import com.rancard.enums.OrderStatus;

import java.util.Objects;


public class OrderEvent {
    private String message;
    private OrderStatus status;
    private OrderDto orderDto;

    public OrderEvent() {
    }

    public OrderEvent(String message, OrderStatus status, OrderDto orderDto) {
        this.message = message;
        this.status = status;
        this.orderDto = orderDto;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public OrderDto getOrderDto() {
        return orderDto;
    }

    public void setOrderDto(OrderDto orderDto) {
        this.orderDto = orderDto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderEvent that = (OrderEvent) o;
        return Objects.equals(message, that.message) && status == that.status && Objects.equals(orderDto, that.orderDto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, status, orderDto);
    }

    @Override
    public String toString() {
        return "OrderEvent{" +
                "message='" + message + '\'' +
                ", status=" + status +
                ", orderDto=" + orderDto +
                '}';
    }
}
