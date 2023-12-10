package com.rancard.dto.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    private String id;
    private String email;
    private String phone;
    private String firstname;
    private String lastname;
    private String othernames;
    private String walletId;
}
