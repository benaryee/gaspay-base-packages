package com.rancard.dto;

import java.math.BigDecimal;
import java.util.Objects;


public class OrderItemDto {
    private Long id;
    private String skuCode;
    private BigDecimal price;
    private Integer quantity;
    private float size;

    public OrderItemDto() {
    }

    public OrderItemDto(Long id, String skuCode, BigDecimal price, Integer quantity, float size) {
        this.id = id;
        this.skuCode = skuCode;
        this.price = price;
        this.quantity = quantity;
        this.size = size;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItemDto that = (OrderItemDto) o;
        return Float.compare(that.size, size) == 0 && Objects.equals(id, that.id) && Objects.equals(skuCode, that.skuCode) && Objects.equals(price, that.price) && Objects.equals(quantity, that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, skuCode, price, quantity, size);
    }

    @Override
    public String toString() {
        return "OrderItemDto{" +
                "id=" + id +
                ", skuCode='" + skuCode + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", size=" + size +
                '}';
    }
}
