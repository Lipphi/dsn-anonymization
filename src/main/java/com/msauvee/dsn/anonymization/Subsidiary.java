package com.msauvee.dsn.anonymization;

class Subsidiary {
    public final Address address; 
    public final String nic;
    public final String ape;
    
    public Subsidiary(Address address, String nic, String ape) {
        this.address = address;
        this.nic = nic;
        this.ape = ape;
    }
}
