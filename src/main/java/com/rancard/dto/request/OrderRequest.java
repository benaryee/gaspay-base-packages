/*(C) Gaspay App 2023 */
package com.rancard.dto.request;

import com.rancard.dto.payload.Address;
import com.rancard.dto.payload.OrderItemDto;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest implements Serializable {

    @NotNull private String customerMsisdn;
    private Address shippingAddress;

    @NotNull private String paymentId;

    private String outletId;
    private List<OrderItemDto> orderItemsDtoList;
}
