package com.example.quangadmin.smsencrypfinal.Main;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.quangadmin.smsencrypfinal.Chat.InnerAPP;
import com.example.quangadmin.smsencrypfinal.Filecontact.FileOutIn;
import com.example.quangadmin.smsencrypfinal.Key.Keyid;
import com.example.quangadmin.smsencrypfinal.Key.Option;
import com.example.quangadmin.smsencrypfinal.Khoigiaima.ECC;
import com.example.quangadmin.smsencrypfinal.R;

import org.bouncycastle.math.ec.ECPoint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    FileOutIn fileOut;
    Button message,key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        if (fileOut.readEC(this, "PrivateKEY") == null) {
           startActivity(new Intent(MainActivity.this, First.class));
        }
        message = (Button) findViewById(R.id.xemTinnhan);
        key = (Button) findViewById(R.id.quanlyKey);
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,InnerAPP.class));
            }
        });
        key.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Option.class));
            }
        });
    }
    @Override
    public void onBackPressed() {
        finish();
    }
}