package com.rancard.dto;


import com.rancard.payload.Address;

import java.util.List;
import java.util.Objects;

public class OrderRequest {
    private String customerMsisdn;
    private List<OrderItemDto> orderItemsDtoList;
    private Address shippingAddress;

    public OrderRequest() {
    }

    public OrderRequest(String customerMsisdn, List<OrderItemDto> orderItemsDtoList, Address shippingAddress) {
        this.customerMsisdn = customerMsisdn;
        this.orderItemsDtoList = orderItemsDtoList;
        this.shippingAddress = shippingAddress;
    }

    public String getCustomerMsisdn() {
        return customerMsisdn;
    }

    public void setCustomerMsisdn(String customerMsisdn) {
        this.customerMsisdn = customerMsisdn;
    }

    public List<OrderItemDto> getOrderItemsDtoList() {
        return orderItemsDtoList;
    }

    public void setOrderItemsDtoList(List<OrderItemDto> orderItemsDtoList) {
        this.orderItemsDtoList = orderItemsDtoList;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderRequest that = (OrderRequest) o;
        return Objects.equals(customerMsisdn, that.customerMsisdn) && Objects.equals(orderItemsDtoList, that.orderItemsDtoList) && Objects.equals(shippingAddress, that.shippingAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerMsisdn, orderItemsDtoList, shippingAddress);
    }

    @Override
    public String toString() {
        return "OrderRequest{" +
                "customerMsisdn='" + customerMsisdn + '\'' +
                ", orderItemsDtoList=" + orderItemsDtoList +
                ", shippingAddress=" + shippingAddress +
                '}';
    }
}
