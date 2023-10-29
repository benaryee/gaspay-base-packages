package com.rancard.ussdapp.model.dto;

import com.rancard.ussdapp.model.enums.Channel;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class TopupRequestDto {
    private UserDto user;
    private String amount;
    private Channel channel;
    private String mobileNetwork;
    private String sessionId;
}
