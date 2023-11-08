package com.rancard.auth.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rancard.auth.model.enums.UserStatus;
import com.rancard.auth.model.payload.Address;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable {
    private String id;
    private String email;
    private String phone;
    private String firstname;
    private String lastname;
    private String othernames;
    private String onboardStatus;
    private String lastLogin;
    private String lastSeen;
    private UserStatus userStatus;
    private int successiveFailedAttempts;
    private boolean loggedIn;
    private String created;
    private String updated;
    private String walletId;
    private Address address;
}
