package com.rancard.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateWalletDto {

    private String walletId;
    private String currency;
    private String password;
    private BigDecimal balance;


}
