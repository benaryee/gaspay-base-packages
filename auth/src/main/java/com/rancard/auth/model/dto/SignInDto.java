package com.rancard.auth.model.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SignInDto implements Serializable {
    private String username;
    private String password;
}
