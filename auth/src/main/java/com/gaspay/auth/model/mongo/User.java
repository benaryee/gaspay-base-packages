package com.gaspay.auth.model.mongo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gaspay.auth.model.enums.UserStatus;
import com.gaspay.auth.model.payload.Address;
import lombok.AllArgsConstructor;
import lombok.Data;


import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Document
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class User extends BaseMongoModel{
    private String username;
    private String firstname;
    private String othernames;
    private UserStatus userStatus;
    private String lastname;
    private String email;
    private String familySize;
    private String currentFuelSource;
    private String password;
    private boolean active;
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String msisdn;
    private int successiveFailedAttempts;
    private String walletId;

    private boolean resetPassword;
    private LocalDateTime lastLogin;
    private LocalDateTime lastSeen;

    private String keycloakUserId;
    private Address address = new Address();
    private Set<Role> roles;
}
