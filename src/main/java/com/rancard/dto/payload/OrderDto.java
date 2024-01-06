/*(C) Gaspay App 2023 */
package com.rancard.dto.payload;

import com.rancard.enums.OrderStatus;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
public class OrderDto implements Serializable {
    private String orderId;
    private List<OrderItem> items;
    private double totalAmount;
    private String customerMsisdn;
    private OrderStatus orderStatus;
    private Address shippingAddress;
    private String agentId;
    private List<OrderStateHistory> stateHistory;
    private String outletId;
    private String paymentId;
    private LocalDateTime createdAt;
}
