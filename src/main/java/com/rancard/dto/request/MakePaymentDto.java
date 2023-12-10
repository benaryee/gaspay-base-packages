package com.rancard.dto.request;

import com.rancard.dto.payload.UserDto;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class MakePaymentDto {
    private UserDto sender;
    private UserDto recipient;
    private BigDecimal amount;
    private String sessionId;
}
