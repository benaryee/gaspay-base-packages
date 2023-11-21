package com.gaspay.ussdapp.model.dto;

import com.gaspay.ussdapp.model.enums.Channel;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TopupRequestDto {
    private UserDto user;
    private String amount;
    private Channel channel;
    private String mobileNetwork;
    private String sessionId;
}
