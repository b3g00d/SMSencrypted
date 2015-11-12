/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.quangadmin.smsencrypfinal.Khoigiaima;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author QUANGADMIN
 */
public class AES {
    private byte[] encryptext;
    private byte[] plaintext;
    private byte[] KEY;
    private byte[] iv = {0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00};
    private IvParameterSpec ips;
    private Cipher cipher;
    
    public AES(){
    }
    
    public void Encryp(byte[] plaintext,byte[] KEY){
        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            ips = new IvParameterSpec(iv);
            Key key = new SecretKeySpec(KEY, 0, 32, "AES");
            cipher.init(Cipher.ENCRYPT_MODE, key,ips);
            this.encryptext = cipher.doFinal(plaintext);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(AES.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(AES.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(AES.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidAlgorithmParameterException ex) {
            Logger.getLogger(AES.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(AES.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(AES.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
    
    public void Decryp(byte[] encryptext, byte[] KEY){
        try {
            cipher = Cipher.getInstance("AES/CBC/NoPadding");
            ips = new IvParameterSpec(iv);
            Key key = new SecretKeySpec(KEY, 0, 32, "AES");
            cipher.init(Cipher.DECRYPT_MODE, key,ips);
            this.plaintext = cipher.doFinal(encryptext);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(AES.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(AES.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(AES.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidAlgorithmParameterException ex) {
            Logger.getLogger(AES.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(AES.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(AES.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the encryptext
     */
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
     * @return the KEY
     */

    /**
     * @return the length
     */
}
