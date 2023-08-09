package com.rancard.productservice.model.payload;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class User {

    private String username;
    private String firstname;
    private String othernames;
    private String lastname;
    private String email;
    private String msisdn;
    private Address address = new Address();

}
