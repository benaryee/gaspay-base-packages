package com.rancard.paymentservice.model.dto.wallet;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WalletDto {

    private String id;
    private String walletId;
    private String balance;
    private String currency;
    private String password;

}
