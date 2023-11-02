package com.rancard.order.model.mongo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rancard.order.model.enums.UserStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Document
@JsonInclude(JsonInclude.Include.NON_NULL)
@SuperBuilder
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseMongoModel {
    private String username;
    private String firstname;
    private String othernames;
    private UserStatus userStatus;
    private String lastname;
    private String email;
    private String familySize;
    private String currentFuelSource;
    private String password;
    private boolean isActive;
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
