package com.rancard.order.model.mongo;

import lombok.Data;

@Data
public class Address {
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String location;
    private String longitude;
    private String latitude;
    private String ghanaPostGps;
}
