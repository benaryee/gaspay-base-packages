package com.rancard.ussdapp.model.payload;

import lombok.Data;

@Data
public class Address {
    private String location;
    private String longitude;
    private String latitude;
}
