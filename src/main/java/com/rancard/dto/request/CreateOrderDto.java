/*(C) Gaspay App 2023-2024 */
package com.rancard.dto.request;

import com.rancard.dto.payload.Address;
import com.rancard.dto.payload.OrderItemDto;
import com.rancard.enums.Channel;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

@Data
public class CreateOrderDto {

    @NotNull private String paymentId;
    @NotNull private String customerMsisdn;
    private Address shippingAddress;

    private BigDecimal totalAmount;
    private Channel channel;
    @NotNull private String outletId;
    private List<OrderItemDto> orderItemsDtoList;
}
