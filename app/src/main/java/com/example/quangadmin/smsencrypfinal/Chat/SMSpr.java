package com.example.quangadmin.smsencrypfinal.Chat;

import java.io.Serializable;

/**
 * Created by QUANGADMIN on 10/16/2015.
 */
public class SMSpr implements Serializable {
    public boolean position;
    public String message;
    public SMSpr(){

    }
    public SMSpr(boolean position, String message){
        super();
        this.position =position;
        this.message = message;
    }
}
