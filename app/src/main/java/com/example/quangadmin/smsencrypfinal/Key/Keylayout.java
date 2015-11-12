package com.example.quangadmin.smsencrypfinal.Key;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.quangadmin.smsencrypfinal.Filecontact.FileOutIn;
import com.example.quangadmin.smsencrypfinal.R;

import java.util.List;

/**
 * Created by QUANGADMIN on 10/17/2015.
 */
public class Keylayout extends AppCompatActivity {
    ListView listview;
    FileOutIn fileoutin = new FileOutIn();
    pubAdapter adapter;
    int i = 0;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listcontactkey);
        adapter = new pubAdapter(Keylayout.this, R.layout.keyeachcontact);
        listview = (ListView) findViewById(R.id.listContactID);
        adapter.readALL(fileoutin.readListID(this, "PublicKeyList"));
        System.out.println("CAI NAY NO KHONG NULL NHE");
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        System.out.print("1" +adapter.getItem(position).verify);
                        adapter.getItem(position).verify = !adapter.getItem(position).verify;
                        System.out.print("1" +adapter.getItem(position).verify);
                        fileoutin.writeListID(Keylayout.this, adapter.writeOut(), "PublicKeyList");
                        listview.setAdapter(adapter);
                    }
                });
    }
    @Override
    public void onBackPressed() {
        finish();
    }
}

