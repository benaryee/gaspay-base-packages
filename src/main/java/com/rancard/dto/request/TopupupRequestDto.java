package com.rancard.dto.request;

import com.rancard.dto.payload.UserDto;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TopupupRequestDto {
    private UserDto user;
    private BigDecimal amount;
    private String channel;
    private String mobileNetwork;
    private String sessionId;


    public void setMobileNetwork(String mobileNetwork) {
        this.mobileNetwork = mobileNetwork;
    }
}
