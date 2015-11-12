package com.example.quangadmin.smsencrypfinal.Filecontact;

import android.content.Context;

import com.example.quangadmin.smsencrypfinal.Chat.SMSpr;
import com.example.quangadmin.smsencrypfinal.Key.Keyid;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by QUANGADMIN on 10/16/2015.
 */
public class FileOutIn implements Serializable {
    public FileOutIn(){

    }
    public static void writeObj(Context context, Object object, String fileName) {
        try {
            FileOutputStream fos = context.openFileOutput(fileName,
                    Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(object);
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Object readObj(Context context, String fileName) {
        Object object;
        FileInputStream fis;
        try {
            fis = context.openFileInput(fileName);
            ObjectInputStream is = new ObjectInputStream(fis);
            object = (Object) is.readObject();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return object;
    }
    public List<SMSpr> readList(Context context, String fileName) {
        FileInputStream fis;
        List<SMSpr> listSMS = null;
        try {
                fis = context.openFileInput(fileName);
                ObjectInputStream is = new ObjectInputStream(fis);
                listSMS = (List<SMSpr>) is.readObject();
                is.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
            System.out.println("File not fould");
            return null;
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }catch (ClassNotFoundException e){
            e.printStackTrace();
            return null;
        }
        return listSMS;
    }
    public static void writeList(Context context, List<SMSpr> list, String fileName) {
        try {
            FileOutputStream fos = context.openFileOutput(fileName,
                    Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(list);
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void writeEC(Context context, BigInteger ec, String fileName){
        try {
            FileOutputStream fos = context.openFileOutput(fileName,
                    Context.MODE_PRIVATE);
            fos.write(ec.toByteArray());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static BigInteger readEC(Context context, String fileName){
        InputStream fis;
        BigInteger ec;
        try {
            fis = context.openFileInput(fileName);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int bytesRead;
            while (( bytesRead = fis.read(b)) != -1) {
                bos.write(b, 0, bytesRead);
            }
            byte[] bytes = bos.toByteArray();
            ec = new BigInteger(bytes);
            fis.close();
        }catch(IOException e){
            e.printStackTrace();
            return null;
        }
        return ec;
    }
    public List<Keyid> readListID(Context context, String fileName) {
        FileInputStream fis;
        List<Keyid> listKey = new ArrayList<Keyid>();
        try {
                System.out.println("co FILE");
                fis = context.openFileInput(fileName);
                ObjectInputStream is = new ObjectInputStream(fis);
                listKey = (List<Keyid>) is.readObject();
                is.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
            System.out.println("ADADASDASDASDA");
            System.out.println("co FILE");
            return listKey;
        }catch (IOException e){
            e.printStackTrace();
            System.out.println("co FILE");
            return listKey;
        }catch (ClassNotFoundException e){
            System.out.println("co FILE");
            e.printStackTrace();
            return listKey;
        }catch (NullPointerException e){
            System.out.println("co FILE");
            e.printStackTrace();
            return listKey;
        }
        System.out.println("co FILE");
        return listKey;
    }
    public static void writeListID(Context context, List<Keyid> list, String fileName) {
        try {
            FileOutputStream fos = context.openFileOutput(fileName,
                    Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(list);
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
