package com.gaspay.auth.model.dto.wallet;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateWalletDto {

    private String walletId;
    private String currency;
    private String password;
    private BigDecimal balance;


}
