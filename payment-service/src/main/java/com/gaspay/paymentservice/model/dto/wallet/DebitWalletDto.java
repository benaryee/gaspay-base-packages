package com.gaspay.paymentservice.model.dto.wallet;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DebitWalletDto {
    private String id;
    private BigDecimal amount;
}
