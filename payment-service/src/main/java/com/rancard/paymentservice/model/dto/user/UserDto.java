package com.rancard.paymentservice.model.dto.user;

import lombok.Data;

@Data
public class UserDto {
    private String id;
    private String email;
    private String phone;
    private String firstname;
    private String lastname;
    private String othernames;
    private String walletId;
}
