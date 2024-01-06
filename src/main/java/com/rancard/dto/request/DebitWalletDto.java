/*(C) Gaspay App 2023 */
package com.rancard.dto.request;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DebitWalletDto {
    private String id;
    private BigDecimal amount;
}
