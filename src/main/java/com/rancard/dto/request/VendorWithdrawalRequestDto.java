/*(C) Gaspay App 2023-2024 */
package com.rancard.dto.request;

import com.rancard.enums.PaymentType;
import com.rancard.mongo.User;
import com.rancard.mongo.Vendor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VendorWithdrawalRequestDto implements Serializable {

    @DBRef private Vendor sender;

    @DBRef private User recipient;

    private BigDecimal amount;
    private PaymentType paymentType;
    private String sessionId;
}
