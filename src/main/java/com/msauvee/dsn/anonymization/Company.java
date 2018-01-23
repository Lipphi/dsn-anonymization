package com.msauvee.dsn.anonymization;

class Company {
    public final String name;
    public final Address address;    
    public final String siren;
    public final String nic;
    public final String siret;
    public final String ape;

    public Company(Address address, String siren, String nic, String ape) {
        this.name = "acme";
        this.address = address;
        this.siren = siren;
        this.nic = nic; 
        this.siret = siren + nic; //"32165498712315";
        this.ape = ape;
    }
}
