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
    private String msisdn;
    private String email;
    private Address adress;
    private Role role;
}
