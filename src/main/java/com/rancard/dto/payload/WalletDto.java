package com.rancard.dto.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WalletDto {

    private String id;
    private String walletId;
    private String balance;
    private String promoBalance;
    private String hirePurchaseBalance;
    private String topupBalance;
    private String currency;
    private String password;
    private String status;
}
