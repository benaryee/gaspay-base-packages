package com.rancard.mongo;

import com.rancard.dto.payload.WalletDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@Document
@EqualsAndHashCode(callSuper = true)
public class Wallet extends BaseMongoModel {

    private String walletId;
    private BigDecimal balance;
    private BigDecimal promoBalance;
    private BigDecimal hirePurchaseBalance;
    private BigDecimal topupBalance;
    private String currency;
    private String status;
    private String password;

    public WalletDto toDto() {
        return WalletDto.builder()
                .walletId(walletId)
                .promoBalance(String.valueOf(promoBalance))
                .balance(String.valueOf(balance))
                .hirePurchaseBalance(String.valueOf(hirePurchaseBalance))
                .topupBalance(String.valueOf(topupBalance))
                .currency(currency)
                .status(status)
                .build();
    }
}
