/*(C) Gaspay App 2023 */
package com.rancard.dto.request;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateWalletDto {
    private String currency;
    private String password;
    private BigDecimal balance;
}
