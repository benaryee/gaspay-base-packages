package com.rancard.auth.models.mongo;

import com.rancard.auth.models.enums.UserStatus;
import com.rancard.auth.models.payload.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.sql.Timestamp;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "users")
public class User {
    private String id;
    private String username;
    private String firstname;
    private String othernames;

    @Enumerated(value = EnumType.STRING)
    private UserStatus userStatus;


    private String lastname;
    private String email;
    private int familySize;
    private String currentFuelSource;
    private String password;
    private String pin;
    private boolean isActive;
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String msisdn;
    private int successiveFailedAttempts;

    private boolean resetPassword;
    private Timestamp lastLogin;
    private Timestamp lastSeen;

    private Address address = new Address();
    private Set<Role> roles;
}
