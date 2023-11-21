package com.gaspay.paymentservice.model.dto.wallet;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateWalletDto {

    private String walletId;
    private String currency;
    private String password;
    private BigDecimal balance;


}
