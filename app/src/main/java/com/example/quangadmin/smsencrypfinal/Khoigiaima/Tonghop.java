/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.quangadmin.smsencrypfinal.Khoigiaima;

import org.bouncycastle.math.ec.ECPoint;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

/**
 *
 * @author QUANGADMIN
 */
public class Tonghop {
    private boolean Verifi;
    public Tonghop(){

    }
    public byte[] Tonghopmahoa(String c, ECPoint PublicKeyReceiver, BigInteger PrivateKeySender) throws UnsupportedEncodingException {
            Makhoa mk = new Makhoa();
            byte[] cB = c.getBytes(("UTF-8"));
            mk.Makhoa2( cB, PublicKeyReceiver, PrivateKeySender);

        byte[] encryptext = mk.getEncryptext();
        ECPoint Pc1 = mk.getPc1();
        ECPoint Pc2 = mk.getPc2();
        BigInteger rr = mk.getRr();
        BigInteger vv = mk.getVv();
        Donggoi donggoi = new Donggoi();
        donggoi.Combinemakhoa(encryptext, Pc1, Pc2, rr, vv);
        return donggoi.getPacket();
    }

    public String Tonghopgiaima(byte[] packet, ECPoint PublicKeySender, BigInteger PrivateKeyReceiver) throws UnsupportedEncodingException {
    Donggoi mogoi = new Donggoi();
        mogoi.uncombine(packet);
        byte[] cipher = mogoi.getC();
        BigInteger m = new BigInteger(cipher);
        ECPoint Pc1 = mogoi.getPC1();
        ECPoint Pc2 = mogoi.getPC2();
        BigInteger rr = mogoi.getRr();
        BigInteger vv = mogoi.getVv();

        Makhoa mk = new Makhoa();
        mk.Giaima2(rr, vv, m, Pc1, Pc2, PublicKeySender, PrivateKeyReceiver);
        Verifi = mk.isVerify();
        byte[] plaintext = mk.getPlaintext();
        int length;
        int position =0;
        length= plaintext[0];
        position +=1;
        byte[] plain = new byte[length];
        System.arraycopy(plaintext, position, plain, 0, length);
        String guira = new String(plain,"UTF-8");
        return guira;
    }

    public boolean isVerifi() {
        return Verifi;
    }
}
