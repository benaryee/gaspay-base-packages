/*(C) Gaspay App 2023-2024 */
package com.rancard.dto.payload;

import com.rancard.enums.Channel;
import com.rancard.enums.OrderStatus;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import lombok.*;

@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Data
@EqualsAndHashCode
public class OrderDto implements Serializable {
    public String orderId;
    public List<OrderItem> items;
    public double totalAmount;
    public String customerMsisdn;
    public OrderStatus orderStatus;
    public Address shippingAddress;
    public String agentId;
    public List<OrderStateHistory> stateHistory;
    public String outletId;
    private Channel channel;
    public String paymentId;
    private String pickupCode;
    public LocalDateTime createdAt;
}
