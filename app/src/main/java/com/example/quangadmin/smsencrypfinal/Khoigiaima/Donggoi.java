/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.quangadmin.smsencrypfinal.Khoigiaima;

import org.bouncycastle.math.ec.ECPoint;

import java.math.BigInteger;
import java.util.Arrays;

public class Donggoi {
    private ECC ecc = new ECC();
    private ECPoint PC1;
    private ECPoint PC2;
    private byte[] c,packet;
    private BigInteger rr;
    private BigInteger vv;
    public Donggoi(){
        
    }
    
    public void Combinemakhoa(byte[] c, ECPoint PC1, ECPoint PC2, BigInteger r, BigInteger v){
        byte[] CBytes = c;
        byte CByteslength = (byte)c.length;
        
        byte[] PC1Bytes = PC1.getEncoded();
        byte PC1Byteslength = (byte)PC1Bytes.length;
        
        byte[] PC2Bytes = PC2.getEncoded();
        byte PC2Byteslength = (byte)PC2Bytes.length;

        byte[] RBytes = r.toByteArray();
        byte RByteslength = (byte)RBytes.length;

        byte[] VBytes = v.toByteArray();
        byte VByteslength = (byte)VBytes.length;

        byte[] packet = new byte[1 + c.length
                + 1 + PC1Bytes.length
                + 1 + PC2Bytes.length
                + 1 + RBytes.length
                + 1 + VBytes.length];
        int position = 0;
        System.out.println("1 "+c.length+" "+PC1Bytes.length+" "+PC2Bytes.length+" "+RBytes.length+" "+VBytes.length +" "+packet.length);
        System.out.println("1 "+(int)CByteslength+" "+(int)PC1Byteslength+" "+(int)PC2Byteslength+" "+(int)RByteslength+" "+(int)VByteslength +" "+packet.length);
        packet[position] = CByteslength;
        position += 1;
        System.arraycopy(CBytes, 0, packet, position, (int) CByteslength);
        position += (int)CByteslength;
        
        packet[position] = PC1Byteslength ;
        position += 1;
        System.arraycopy(PC1Bytes, 0, packet, position, (int) PC1Byteslength);
        position += (int)PC1Byteslength;

        packet[position] = PC2Byteslength ;
        position += 1;
        System.arraycopy(PC2Bytes, 0, packet, position, (int) PC2Byteslength);
        position += (int)PC2Byteslength;

        packet[position] = RByteslength ;
        position += 1;
        System.arraycopy(RBytes, 0, packet, position, (int) RByteslength);
        position += (int)RByteslength;

        packet[position] = VByteslength ;
        position += 1;
        System.arraycopy(VBytes, 0, packet, position, (int) VByteslength);
        System.out.println("1 "+c.length+" "+PC1Bytes.length+" "+PC2Bytes.length+" "+RBytes.length+" "+VBytes.length);
        System.out.println("Cbytes " + Arrays.toString(CBytes));
        System.out.println("PC1 "+  Arrays.toString(PC1.getEncoded()));
        System.out.println("PC2 "+  Arrays.toString(PC1.getEncoded()));
        System.out.println("rr "+ r.toString());
        System.out.println("vv " + v.toString());
        this.packet = packet;
    }
    
    public void  uncombine(byte[] packet){
        int position = 0;
        
        int CByteslength = packet[position];
        position += 1;
        byte[] CBytes = new byte[CByteslength];
        System.arraycopy(packet, position, CBytes, 0, CByteslength);
        position += CByteslength;

        int PC1Byteslength = packet[position];
        position += 1;
        byte[] PC1Bytes = new byte[PC1Byteslength];
        System.arraycopy(packet, position, PC1Bytes, 0, PC1Byteslength);
        position += PC1Byteslength;

        int PC2Byteslength = packet[position];
        position += 1;
        byte[] PC2Bytes = new byte[PC2Byteslength];
        System.arraycopy(packet, position, PC2Bytes, 0, PC2Byteslength);
        position += PC2Byteslength;

        int RByteslength = packet[position];
        position += 1;
        byte[] RBytes = new byte[RByteslength];
        System.arraycopy(packet, position, RBytes, 0, RByteslength);
        position += RByteslength;

        int VByteslength = packet[position];
        position += 1;
        byte[] VBytes = new byte[VByteslength];
        System.arraycopy(packet, position, VBytes, 0, VByteslength);
        
        this.c = CBytes;
        this.PC1 = ecc.decode(PC1Bytes);
        this.PC2 = ecc.decode(PC2Bytes);
        this.rr = new BigInteger(RBytes);
        this.vv = new BigInteger(VBytes);
    }

    /**
     * @return the R
     */

    /**
     * @return the c
     */
    public byte[] getC() {
        return c;
    }

    /**
     * @return the packet
     */
    public byte[] getPacket() {
        return packet;
    }

    /**
     * @return the s
     */

    public ECPoint getPC1() {
        return PC1;
    }


    public ECPoint getPC2() {
        return PC2;
    }

    public BigInteger getRr() {
        return rr;
    }


    public BigInteger getVv() {
        return vv;
    }

}
