/*(C) Gaspay App 2023-2024 */
package com.rancard.dto.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WalletDto {

    private String id;
    private BigDecimal balance;
    private BigDecimal promoBalance;
    private double promoPoints;
    private BigDecimal hirePurchaseBalance;
    private BigDecimal topupBalance;
    private String currency;
    private String password;
    private String status;
}
