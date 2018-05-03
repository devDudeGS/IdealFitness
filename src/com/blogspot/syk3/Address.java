package com.blogspot.syk3;

public class Address {
    // variable assignment for atomic values of address
    public final String streetAddress;
    public final String city;
    public final String state;
    public final int zipcode;

    // Constructor
    public Address(String streetAddress, String city, String state, int zipcode) {
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
    }

    // when printing Address as String
    public String toString() {
        return this.streetAddress + ", " + this.city + ", " + this.state + " " + this.zipcode;
    }
}
