package com.rancard.ussdapp.model.mongo;

import com.rancard.ussdapp.model.enums.Role;
import com.rancard.ussdapp.model.payload.Address;
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
    private int familySize;
    private String msisdn;
    private String email;
    private Address address = new Address();
    private String password;
}
