package com.rancard.auth.model.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class SignInDto implements Serializable {
    private String username;
    private String password;
}
