package com.rancard.dto.payload;

import com.rancard.enums.OrderStatus;
import com.rancard.payload.Address;
import com.rancard.payload.OrderItem;
import lombok.Builder;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Builder
public class OrderDto implements Serializable {
    private String orderId;
    private List<OrderItem> items;
    private double totalAmount;
    private String customerMsisdn;
    private OrderStatus orderStatus;
    private Address shippingAddress;
    private String agentId;
    private LocalDateTime createdAt;

    public OrderDto() {
    }

    public OrderDto(String orderId, List<OrderItem> items, double totalAmount, String customerMsisdn, OrderStatus orderStatus, Address shippingAddress, String agentId, LocalDateTime createdAt) {
        this.orderId = orderId;
        this.items = items;
        this.totalAmount = totalAmount;
        this.customerMsisdn = customerMsisdn;
        this.orderStatus = orderStatus;
        this.shippingAddress = shippingAddress;
        this.agentId = agentId;
        this.createdAt = createdAt;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getCustomerMsisdn() {
        return customerMsisdn;
    }

    public void setCustomerMsisdn(String customerMsisdn) {
        this.customerMsisdn = customerMsisdn;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDto orderDto = (OrderDto) o;
        return Double.compare(orderDto.totalAmount, totalAmount) == 0 && Objects.equals(orderId, orderDto.orderId) && Objects.equals(items, orderDto.items) && Objects.equals(customerMsisdn, orderDto.customerMsisdn) && orderStatus == orderDto.orderStatus && Objects.equals(shippingAddress, orderDto.shippingAddress) && Objects.equals(agentId, orderDto.agentId) && Objects.equals(createdAt, orderDto.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, items, totalAmount, customerMsisdn, orderStatus, shippingAddress, agentId, createdAt);
    }
}
