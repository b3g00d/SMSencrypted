package com.example.quangadmin.smsencrypfinal.Khoitinnhan;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsManager;
import android.util.Base64;
import android.widget.Toast;

import com.example.quangadmin.smsencrypfinal.Khoigiaima.Tonghop;

import org.bouncycastle.math.ec.ECPoint;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.ArrayList;

/**
 * Created by QUANGADMIN on 10/17/2015.
 */
public class Message{
    private BroadcastReceiver sendBroadcastReceiver= new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            switch (getResultCode()) {
                case Activity.RESULT_OK:
                    Toast.makeText(context, "SMS sent successfully", Toast.LENGTH_SHORT).show();
                    break;
                case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                    Toast.makeText(context, "Generic failure cause", Toast.LENGTH_SHORT).show();
                    break;
                case SmsManager.RESULT_ERROR_NO_SERVICE:
                    Toast.makeText(context, "Service is currently unavailable", Toast.LENGTH_SHORT).show();
                    break;
                case SmsManager.RESULT_ERROR_NULL_PDU:
                    Toast.makeText(context, "No pdu provided", Toast.LENGTH_SHORT).show();
                    break;
                case SmsManager.RESULT_ERROR_RADIO_OFF:
                    Toast.makeText(context, "Radio was explicitly turned off", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    private BroadcastReceiver deliveryBroadcastReceiver= new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            switch (getResultCode()) {
                case Activity.RESULT_OK:
                    Toast.makeText(context, "SMS deliver", Toast.LENGTH_SHORT).show();
                    break;
                case Activity.RESULT_CANCELED:
                    Toast.makeText(context, "SMS not deliver", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    Tonghop tonghop = new Tonghop();
    public Message(){

    }
    public void Message_send(Context ctx, String message, String phone){
        String SENT = "Message Sent";
        String DELIVER = "Message Deliver";

        PendingIntent sentPI = PendingIntent.getBroadcast(ctx, 0,new Intent(SENT),0);
        PendingIntent deliverPI = PendingIntent.getBroadcast(ctx, 0, new Intent(DELIVER),0);

        ctx.registerReceiver(sendBroadcastReceiver , new IntentFilter(SENT));
        ctx.registerReceiver(deliveryBroadcastReceiver , new IntentFilter(DELIVER));
        SmsManager smsManager = SmsManager.getDefault();
        ArrayList<String> smsBodyParts = smsManager.divideMessage(message);
        ArrayList<PendingIntent> sentPendingIntents = new ArrayList<PendingIntent>();
        ArrayList<PendingIntent> deliveredPendingIntents = new ArrayList<PendingIntent>();

        for (int i = 0; i < smsBodyParts.size(); i++) {
            sentPendingIntents.add(sentPI);
            deliveredPendingIntents.add(deliverPI);
        }
        smsManager.sendMultipartTextMessage(phone, null, smsBodyParts, sentPendingIntents, deliveredPendingIntents);
    }
    public void Message_send_mahoa(Context ctx, String message, String phone, ECPoint publicKey, BigInteger privateKey) throws UnsupportedEncodingException {
        String SENT = "Message Sent";
        String DELIVER = "Message Deliver";
        PendingIntent sentPI = PendingIntent.getBroadcast(ctx, 0,new Intent(SENT),0);
        PendingIntent deliverPI = PendingIntent.getBroadcast(ctx, 0, new Intent(DELIVER), 0);
        ctx.registerReceiver(sendBroadcastReceiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(context, "SMS sent successfully", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(context, "Generic failure cause", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(context, "Service is currently unavailable", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(context, "No pdu provided", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(context, "Radio was explicitly turned off", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(SENT));

        ctx.registerReceiver(deliveryBroadcastReceiver =new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(context, "SMS deliver", Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(context, "SMS not deliver", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(DELIVER));
        SmsManager smsManager = SmsManager.getDefault();
        byte[] packet =  tonghop.Tonghopmahoa(message, publicKey, privateKey);
        String encrypt = Base64.encodeToString(packet, Base64.DEFAULT);
        ArrayList<String> smsBodyParts = smsManager.divideMessage(encrypt);
        ArrayList<PendingIntent> sentPendingIntents = new ArrayList<PendingIntent>();
        ArrayList<PendingIntent> deliveredPendingIntents = new ArrayList<PendingIntent>();

        for (int i = 0; i < smsBodyParts.size(); i++) {
            sentPendingIntents.add(sentPI);
            deliveredPendingIntents.add(deliverPI);
        }
        smsManager.sendMultipartTextMessage(phone, null, smsBodyParts, sentPendingIntents, deliveredPendingIntents);

    }
    public void unregister(Context ctx){
        ctx.unregisterReceiver(sendBroadcastReceiver);
        ctx.unregisterReceiver(deliveryBroadcastReceiver);
    }

}
