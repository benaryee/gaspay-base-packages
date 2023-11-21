package com.gaspay.auth.model.request;

import com.gaspay.auth.model.payload.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest {
    private String id;
    private String username;
    private String firstname;
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
    private Address address = new Address();
}
