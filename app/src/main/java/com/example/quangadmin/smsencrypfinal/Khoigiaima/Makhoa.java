/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.quangadmin.smsencrypfinal.Khoigiaima;

import org.bouncycastle.math.ec.ECPoint;

import java.math.BigInteger;
import java.util.Random;

/**
 *
 * @author QUANGADMIN
 */
public class Makhoa {
    private byte[] encryptext,plaintext;
    private ECPoint Pc1,Pc2;
    private ECC ecc = new ECC();
    private AES aes = new AES();
    private BigInteger rr,vv;
    private boolean verify;
    private int length1;
    
    public Makhoa(){
        
    }
    /**
     * @return the encryptext
     */
    public void Makhoa2(byte[] plaintext, ECPoint PublicKEYReceiver, BigInteger PrivateKEYSender){
      BigInteger m ;
      BigInteger r;
      BigInteger v;
      BigInteger nounce;
      ECPoint kG;
      BigInteger kIver;
      length1 = plaintext.length;
      byte[] c = new byte[1 + length1];
      int position = 0;
      c[position] = (byte) length1;
      position += 1;
      System.arraycopy(plaintext, 0, c, position, length1);
      
      // lay key AES K1 theo hash ban ro~ roi lay K1 ma hoa ban ro tao ra m
      BigInteger K1 = new BigInteger(256, new Random());
      ECPoint Pm= ecc.getG().multiply(K1);
      aes.Encryp(c, new SHA256().SHA256(Pm));
      m = new BigInteger(aes.getEncryptext());
      
      //Tao chu ky so tren m
        do{
            do{
                nounce = ecc.getNounce();
                kG = ecc.getG().multiply(nounce);
                r = kG.getX().toBigInteger().mod(ecc.getQ());
              }while(r.equals(BigInteger.ZERO));
            kIver = nounce.modInverse(ecc.getQ());
           v = kIver.multiply((m.add(PrivateKEYSender.multiply(r))));
          }while(v.equals(BigInteger.ZERO));
        
      // Ma hoa key AES K1 theo ECC P-384 xuat vao Pc
      ECPoint kPa = PublicKEYReceiver.multiply(nounce);
      
      // Chuan bi cho vao goi tao byte
      this.encryptext = aes.getEncryptext();
      this.Pc1 = kG;
      this.Pc2 = Pm.add(kPa);
      this.rr = r;
      this.vv =v;
    }
    public void Giaima2(BigInteger r, BigInteger v, BigInteger m, ECPoint Pc1, ECPoint Pc2, ECPoint PublicKEYSender, BigInteger PrivateKEYReceiver){
        BigInteger w = v.modInverse(ecc.getQ());
        BigInteger vvv;
        BigInteger u1 = m.multiply(w).mod(ecc.getQ());
        BigInteger u2 = r.multiply(w).mod(ecc.getQ());
        
        ECPoint X = (ecc.getG().multiply(u1)).add(PublicKEYSender.multiply(u2));
        if(X == null){
            this.verify = false;
        }else{
            vvv = X.getX().toBigInteger().mod(ecc.getQ());
          if(vvv.equals(r)){
           this.verify = true; 
          }
        }
        ECPoint Pm = Pc2.subtract(Pc1.multiply(PrivateKEYReceiver));
        aes.Decryp(m.toByteArray(), new SHA256().SHA256(Pm));
        this.plaintext = aes.getPlaintext();
    }
    public byte[] getEncryptext() {
        return encryptext;
    }


    /**
     * @return the plaintext
     */
    public byte[] getPlaintext() {
        return plaintext;
    }

    /**
     * @return the rr
     */
    public BigInteger getRr() {
        return rr;
    }

    /**
     * @return the vv
     */
    public BigInteger getVv() {
        return vv;
    }

    /**
     * @return the Pc
     */
    public ECPoint getPc1() {
        return Pc1;
    }
    
    public ECPoint getPc2() {
        return Pc2;
    }

    /**
     * @return the length
     */
    public int getLength() {
        return length1;
    }

    public boolean isVerify() {
        return verify;
    }
    /**
     * @return the length
     */
}
