/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.quangadmin.smsencrypfinal.Khoigiaima;

import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.util.encoders.Hex;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Random;


/**
 *
 * @author QUANGADMIN
 */
public class ECC implements Serializable{
    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
    private ECCurve ecc;
    private ECPoint G;
    private BigInteger a,b,p,q,nounce;
    public  ECC(){

        // p = 2^192 - 2^64 - 1
        p = new BigInteger("FFFFFFFFFFFFFFFFFFFFFFFF" +
                "FFFFFFFEFFFFFFFFFFFFFFFF", 16);
        a = new BigInteger("FFFFFFFFFFFFFFFFFFFFFFFF" +
                "FFFFFFFEFFFFFFFFFFFFFFFC", 16);
        b = new BigInteger("64210519E59C80E70FA7E9AB" +
                "72243049FEB8DEECC146B9B1", 16);
        q = new BigInteger("FFFFFFFFFFFFFFFFFFFFFFFF" +
                "99DEF836146BC9B1B4D22831", 16);
        ecc = new ECCurve.Fp(p,a,b);
        G = ecc.decodePoint(Hex.decode("04188DA80EB03090F" +
                "67CBF20EB43A18800F4F" +
                "F0AFD82FF101207192B9" +
                "5FFC8DA78631011ED6B2" +
                "4CDD573F977A11E794811"));

        //Ensure nounce < q
    } 
    public ECPoint decode(byte[] code){
        return ecc.decodePoint(code);
    }

    /**
     * @return the nounce
     */
    public BigInteger getNounce() {
        do{
             Random rn = new Random();
             nounce = new BigInteger(getQ().bitLength(),rn);
	}while(nounce.compareTo(getQ()) > -1);
        return nounce;
    }
    public String bytestoHEX(byte[] bytes){
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
    /**
     * @return the q
     */
    public BigInteger getQ() {
        return q;
    }

    /**
     * @return the G
     */
    public ECPoint getG() {
        return G;
    }
}
