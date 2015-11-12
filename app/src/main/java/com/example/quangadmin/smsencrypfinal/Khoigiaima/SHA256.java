package com.example.quangadmin.smsencrypfinal.Khoigiaima;

import org.bouncycastle.math.ec.ECPoint;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by mr2uang on 11/7/15.
 */
public class SHA256 {
    public byte[] SHA256(ECPoint ec){

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(ec.getEncoded());
            return md.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }
}
