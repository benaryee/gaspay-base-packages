package com.rancard.auth.model.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
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
