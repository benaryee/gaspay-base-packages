package com.rancard.payload;

import java.util.Objects;

public class Address {
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String location;
    private String longitude;
    private String latitude;
    private String ghanaPostGps;

    public Address(String street, String city, String state, String postalCode, String location, String longitude, String latitude, String ghanaPostGps) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.location = location;
        this.longitude = longitude;
        this.latitude = latitude;
        this.ghanaPostGps = ghanaPostGps;
    }

    public Address() {
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getGhanaPostGps() {
        return ghanaPostGps;
    }

    public void setGhanaPostGps(String ghanaPostGps) {
        this.ghanaPostGps = ghanaPostGps;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(street, address.street) && Objects.equals(city, address.city) && Objects.equals(state, address.state) && Objects.equals(postalCode, address.postalCode) && Objects.equals(location, address.location) && Objects.equals(longitude, address.longitude) && Objects.equals(latitude, address.latitude) && Objects.equals(ghanaPostGps, address.ghanaPostGps);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, city, state, postalCode, location, longitude, latitude, ghanaPostGps);
    }

    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", location='" + location + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", ghanaPostGps='" + ghanaPostGps + '\'' +
                '}';
    }
}
