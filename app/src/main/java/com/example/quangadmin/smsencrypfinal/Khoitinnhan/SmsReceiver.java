package com.example.quangadmin.smsencrypfinal.Khoitinnhan;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import com.example.quangadmin.smsencrypfinal.Chat.InnerAPP;


/**
 * Created by QUANGADMIN on 10/17/2015.
 */
public class SmsReceiver extends BroadcastReceiver {
    public static final String SMS_BUNDLE = "pdus";

    public void onReceive(Context context, Intent intent) {
        Bundle intentExtras = intent.getExtras();
        if (intentExtras != null) {
            Object[] sms = (Object[]) intentExtras.get(SMS_BUNDLE);
            String messages = "";
            String phone = "";
            for (int i = 0; i < sms.length; ++i) {
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) sms[i]);

                String smsBody = smsMessage.getMessageBody().toString();
                phone = smsMessage.getOriginatingAddress();

                messages += smsBody;
            }
            String phoneSMS = "";
            char[] phoneChar =phone.toCharArray();
            int i = 0;
            if(phoneChar[0] == '+' && phoneChar[1] == '8' && phoneChar[2] == '4'){
                phoneSMS += '0';
                i = 3;
            }
            for(; i < phoneChar.length;i++){
                if((!(phoneChar[i] == '(' || phoneChar[i] == ')' || phoneChar[i] == ' '|| phoneChar[i] == '-')))
                    phoneSMS += phoneChar[i];
            }
            InnerAPP inst = InnerAPP.instance();
            inst.updateList(messages, phoneSMS);
           // Toast.makeText(context, phoneSMS + messages, Toast.LENGTH_SHORT).show();
           /* Intent broadcastIntent = new Intent();
            broadcastIntent.setAction("SMS_RECEIVED_ACTION");
            broadcastIntent.putExtra("phone",phoneSMS);
            broadcastIntent.putExtra("sms",messages);
            context.sendBroadcast(broadcastIntent);*/
        }
    }
}
