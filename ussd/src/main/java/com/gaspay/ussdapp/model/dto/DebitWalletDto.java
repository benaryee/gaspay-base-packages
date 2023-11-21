package com.gaspay.ussdapp.model.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DebitWalletDto {
    private String id;
    private BigDecimal amount;
}
