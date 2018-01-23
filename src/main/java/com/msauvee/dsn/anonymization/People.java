package com.msauvee.dsn.anonymization;

import java.util.Random;
import org.apache.commons.lang3.StringUtils;

class People {

    private final static String EMAIL_PROVIDER = "acme.com";
    private static final Random random = new Random();

    public final String firstName;
    public final String lastName;
    public final Address address;
    public final String email;
    public final String phoneNumber;
    public final String birthCity;

    public People(String firstName, String lastName, Address address, String phoneNumber, String birthCity) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.email = StringUtils.stripAccents(firstName).toLowerCase()
                + "." + StringUtils.stripAccents(lastName).toLowerCase()
                + "@" + EMAIL_PROVIDER;
        this.phoneNumber = phoneNumber;
        this.birthCity = birthCity;
    }

    public String getSocialNumber(String prefix) {
        String result = Integer.toString(1 + random.nextInt(1))  // sex (1 or 2)
                + String.format("%02d",random.nextInt(100))  // birst year (00 < ?? <= 99)
                + String.format("%02d",1 + random.nextInt(12))  // birst mont(00 < ?? <= 12)
                + String.format("%02d",1 + random.nextInt(95)) // district (01 <= ?? <= 95)
                + String.format("%03d",1 + random.nextInt(999)) // INSEE code (3 digit, <>0)
                + String.format("%03d",1 + random.nextInt(999)); // variator (3 digit, <>0)
        
        return prefix + result.substring(prefix.length());
    }
}
