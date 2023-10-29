package com.rancard.auth.model.dto.wallet;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WalletDto {

    private String id;
    private String walletId;
    private BigDecimal balance;
    private String currency;
    private String status;

}
