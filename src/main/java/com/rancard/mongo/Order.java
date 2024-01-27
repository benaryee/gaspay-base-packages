/*(C) Gaspay App 2023-2024 */
package com.rancard.mongo;

import com.rancard.dto.payload.Address;
import com.rancard.dto.payload.OrderDto;
import com.rancard.dto.payload.OrderItem;
import com.rancard.dto.payload.OrderStateHistory;
import com.rancard.enums.Channel;
import com.rancard.enums.OrderStatus;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Document
public class Order extends BaseMongoModel {

    @Indexed private String orderId;
    private List<OrderItem> items;
    private double totalAmount;

    @Indexed private String customerMsisdn;
    @Indexed private OrderStatus orderStatus;

    private List<OrderStateHistory> stateHistory;
    private Address shippingAddress;

    @Indexed private String agentId;
    @Indexed private String pickupCode;

    @Indexed private Channel channel;

    @Indexed private String outletId;

    @Indexed private String paymentId;

    public OrderDto toDto() {
        return OrderDto.builder()
                .orderId(orderId)
                .items(items)
                .totalAmount(totalAmount)
                .customerMsisdn(customerMsisdn)
                .orderStatus(orderStatus)
                .pickupCode(pickupCode)
                .shippingAddress(shippingAddress)
                .paymentId(paymentId)
                .createdAt(created)
                .stateHistory(stateHistory)
                .agentId(agentId)
                .channel(channel)
                .outletId(outletId)
                .build();
    }

    public static Order fromDto(OrderDto orderDto) {
        return Order.builder()
                .orderId(orderDto.getOrderId())
                .items(orderDto.getItems())
                .totalAmount(orderDto.getTotalAmount())
                .customerMsisdn(orderDto.getCustomerMsisdn())
                .orderStatus(orderDto.getOrderStatus())
                .paymentId(orderDto.getPaymentId())
                .shippingAddress(orderDto.getShippingAddress())
                .created(orderDto.getCreatedAt())
                .stateHistory(orderDto.getStateHistory())
                .agentId(orderDto.getAgentId())
                .pickupCode(orderDto.getPickupCode())
                .channel(orderDto.getChannel())
                .outletId(orderDto.getOutletId())
                .build();
    }
}
