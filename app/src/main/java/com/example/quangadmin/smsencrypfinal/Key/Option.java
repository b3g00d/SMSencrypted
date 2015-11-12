package com.example.quangadmin.smsencrypfinal.Key;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.quangadmin.smsencrypfinal.Danhba.Contact_IDs;
import com.example.quangadmin.smsencrypfinal.Danhba.DanhBa;
import com.example.quangadmin.smsencrypfinal.Filecontact.FileOutIn;
import com.example.quangadmin.smsencrypfinal.Khoigiaima.ECC;
import com.example.quangadmin.smsencrypfinal.Khoitinnhan.Message;
import com.example.quangadmin.smsencrypfinal.R;

import org.bouncycastle.math.ec.ECPoint;

import java.math.BigInteger;
import java.util.List;
import java.util.Random;

/**
 * Created by QUANGADMIN on 10/17/2015.
 */
public class Option extends AppCompatActivity{
TextView publickey;
    ECC ecc = new ECC();
    FileOutIn fileOut;
    Button privateKey, listPublic, sendPublic;
    BigInteger n;
    Context ctx = Option.this;
    int i = 0;
    ECPoint Publickey;
    List<Contact_IDs> listContact = null;
    IntentFilter intentFilter;
    Message msg = new Message();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quanlykey);
        intentFilter = new IntentFilter();
        intentFilter.addAction("SMS_RECEIVED_ACTION");
        privateKey = (Button) findViewById(R.id.privateKey);
        sendPublic = (Button) findViewById(R.id.sendPublickey);
        listPublic = (Button) findViewById(R.id.listPublic);
        publickey = (TextView) findViewById(R.id.PublicKey);
        n = fileOut.readEC(this, "PrivateKEY");
        Publickey = ecc.getG().multiply(n);
        publickey.setText(ecc.bytestoHEX(Publickey.getEncoded()));
        privateKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(Option.this)
                        .setTitle("Xac nhan KEY moi")
                        .setMessage("Ban muon tao key moi that khong?")
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                i = 1;
                                n = new BigInteger(256, new Random());
                                fileOut.writeEC(ctx, n, "PrivateKEY");
                                Publickey = ecc.getG().multiply(n);
                                publickey.setText(ecc.bytestoHEX(Publickey.getEncoded()));
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
        sendPublic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ctx, DanhBa.class));
                i = 2;
            }
        });

        listPublic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ctx, Keylayout.class));
            }
        });
    }
    @Override
    public void onBackPressed() {
        i = 0 ;
        finish();
    }
    @Override
    protected void onResume(){
        super.onResume();
        switch (i){
            case 1:
                i=0;
                break;
            case 2:
                i=0;
                listContact = (List<Contact_IDs>) fileOut.readObj(this, "quang");
                if(listContact != null) {
                    System.out.print(listContact.get(0).getNames());
                    msg.Message_send(this, "<@PublicKey#>" + Base64.encodeToString(Publickey.getEncoded(), Base64.DEFAULT), listContact.get(0).getPhoneNumbers());
                }
                break;
          }

        }

    }


