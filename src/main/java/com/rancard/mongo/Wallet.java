/*(C) Gaspay App 2023-2024 */
package com.rancard.mongo;

import com.rancard.dto.payload.WalletDto;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Wallet extends BaseMongoModel {

    private BigDecimal balance;
    private BigDecimal promoBalance;
    private double promoPoints;
    private BigDecimal hirePurchaseBalance;
    private BigDecimal topupBalance;
    private String currency;
    private String status;
    private String password;

    public WalletDto toDto() {
        return WalletDto.builder()
                .id(getIdString())
                .balance(balance)
                .promoBalance(promoBalance)
                .promoPoints(promoPoints)
                .hirePurchaseBalance(hirePurchaseBalance)
                .topupBalance(topupBalance)
                .currency(currency)
                .status(status)
                .password(password)
                .build();
    }

    public static Wallet fromDto(WalletDto dto) {
        return Wallet.builder()
                .id(new ObjectId(dto.getId()))
                .balance(dto.getBalance())
                .promoBalance(dto.getPromoBalance())
                .promoPoints(dto.getPromoPoints())
                .hirePurchaseBalance(dto.getHirePurchaseBalance())
                .topupBalance(dto.getTopupBalance())
                .currency(dto.getCurrency())
                .status(dto.getStatus())
                .password(dto.getPassword())
                .build();
    }
}
