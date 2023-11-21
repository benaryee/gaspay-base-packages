package com.gaspay.ussdapp.model.mongo;

import com.gaspay.ussdapp.model.payload.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private String id;
    private String firstname;
    private String lastname;
    private String currentFuelSource;
    private String familySize;
    private String msisdn;
    private String email;
    private Address address = new Address();
    private String password;
}
