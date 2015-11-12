package com.example.quangadmin.smsencrypfinal.Key;

import org.bouncycastle.math.ec.ECPoint;

import java.io.Serializable;

/**
 * Created by QUANGADMIN on 10/17/2015.
 */
public class Keyid implements Serializable {
    public String name;
    public String phone;
    public byte[] pub;
    public boolean verify;
    public Keyid(String name, String phone, byte[] pub, boolean verify){
        super();
        this.name = name;
        this.phone = phone;
        this.pub = pub;
        this.verify = verify;
    }
}
