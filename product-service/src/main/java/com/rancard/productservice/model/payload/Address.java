package com.rancard.productservice.model.payload;

import lombok.Data;

@Data
public class Address {
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String location;
    private double longitude;
    private double latitude;
    private String ghanaPostGps;
}
