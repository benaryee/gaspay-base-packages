package com.rancard.ussdapp.model.dto;

import com.rancard.ussdapp.model.payload.Address;
import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
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
    private String walletId;
    private String password;
    private String confirmPassword;
    private String currentFuelSource;
    private String familySize;
    private UserStatus userStatus;
    private int successiveFailedAttempts;
    private boolean loggedIn;
    private String created;
    private Address address = new Address();
    private String updated;
}
