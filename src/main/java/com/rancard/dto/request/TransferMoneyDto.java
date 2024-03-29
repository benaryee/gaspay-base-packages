/*(C) Gaspay App 2023-2024 */
package com.rancard.dto.request;

import com.rancard.enums.PaymentType;
import com.rancard.mongo.Outlet;
import com.rancard.mongo.Vendor;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferMoneyDto implements Serializable {

    @DBRef private Outlet sender;

    @DBRef private Vendor recipient;

    private BigDecimal amount;
    private PaymentType paymentType;
    private String sessionId;
}
