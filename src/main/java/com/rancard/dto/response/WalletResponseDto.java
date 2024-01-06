/*(C) Gaspay App 2023 */
package com.rancard.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import lombok.Data;

@Data
public class WalletResponseDto implements Serializable {
    private double balance;
    private String currency;
    private String walletId;

    @JsonProperty("password")
    private String walletKey;
}
