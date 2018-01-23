package com.msauvee.dsn.anonymization;

class Address {
    public final String address;
    public final String address2;
    public final String zipcode;
    public final String inseeCode;
    public final String city;

    Address(String address) {
        this.address = address;
        this.address2 = "Bat 1";
        this.zipcode = "75015";
        this.inseeCode = "123";
        this.city = "Paris";
    }   
}
