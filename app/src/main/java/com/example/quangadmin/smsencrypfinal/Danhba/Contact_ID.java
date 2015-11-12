package com.example.quangadmin.smsencrypfinal.Danhba;

import java.io.Serializable;

/**
 * Created by Nhocnhinho on 5/26/2015.
 */


public class Contact_ID implements Serializable {

    private String name, phoneNumber;
    public Contact_ID(String name, String phoneNumber) {
        super();
        this.name = name;
        this.phoneNumber = phoneNumber;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

}
