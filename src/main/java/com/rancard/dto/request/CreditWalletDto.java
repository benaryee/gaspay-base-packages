/*(C) Gaspay App 2024 */
package com.rancard.dto.request;

import com.rancard.enums.PaymentType;
import com.rancard.mongo.Reward;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreditWalletDto implements Serializable {

    private String id;
    private BigDecimal amount;
    private PaymentType type;
    private Reward reward;
}
