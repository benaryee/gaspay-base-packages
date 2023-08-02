package com.rancard.auth.model.mongo;

import com.rancard.auth.model.enums.UserStatus;
import com.rancard.auth.model.payload.Address;
import lombok.Data;


import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Document
public class User extends BaseMongoModel {
    private String username;
    private String firstname;
    private String othernames;
    private UserStatus userStatus;
    private String lastname;
    private String email;
    private int familySize;
    private String currentFuelSource;
    private String password;
    private boolean isActive;
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String msisdn;
    private int successiveFailedAttempts;

    private boolean resetPassword;
    private LocalDateTime lastLogin;
    private LocalDateTime lastSeen;

    private String keycloakUserId;
    private Address address = new Address();
    private Set<Role> roles;
}