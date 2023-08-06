package com.rancard.auth.model.dto;

import com.rancard.auth.model.enums.UserStatus;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@EqualsAndHashCode
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
    private Set<RoleDto> roles = new HashSet<>();
}
