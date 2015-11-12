package com.example.quangadmin.smsencrypfinal.Main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.quangadmin.smsencrypfinal.Filecontact.FileOutIn;
import com.example.quangadmin.smsencrypfinal.Khoigiaima.ECC;
import com.example.quangadmin.smsencrypfinal.R;

import org.bouncycastle.math.ec.ECPoint;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by QUANGADMIN on 10/17/2015.
 */
public class First extends AppCompatActivity {
    FileOutIn fileOut;
    Button first;
    ECC ecc = new ECC();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            setContentView(R.layout.first);
            first = (Button) findViewById(R.id.first);
            first.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BigInteger n = new BigInteger(256,new Random());
                    ECPoint publickey = ecc.getG().multiply(n);
                    byte[] publickeyByte = publickey.getEncoded();
                    System.out.println(Arrays.toString(n.toByteArray()));
                    fileOut.writeEC(First.this,n,"PrivateKEY");
                    finish();
                }
            });
        }
}
