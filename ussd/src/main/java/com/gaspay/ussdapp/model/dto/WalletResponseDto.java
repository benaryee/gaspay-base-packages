package com.gaspay.ussdapp.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class WalletResponseDto implements Serializable {
    private double balance;
    private String currency;
    private String walletId;

    @JsonProperty("password")
    private String walletKey;
}
