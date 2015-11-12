package com.example.quangadmin.smsencrypfinal.Chat;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsMessage;
import android.util.Base64;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quangadmin.smsencrypfinal.Danhba.Contact_IDs;
import com.example.quangadmin.smsencrypfinal.Danhba.DanhBa;
import com.example.quangadmin.smsencrypfinal.Filecontact.FileOutIn;
import com.example.quangadmin.smsencrypfinal.Key.Keyid;
import com.example.quangadmin.smsencrypfinal.Khoigiaima.ECC;
import com.example.quangadmin.smsencrypfinal.Khoigiaima.Tonghop;
import com.example.quangadmin.smsencrypfinal.Khoitinnhan.Message;
import com.example.quangadmin.smsencrypfinal.R;
import org.bouncycastle.math.ec.ECPoint;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.List;

/**
 * Created by QUANGADMIN on 10/16/2015.
 */
public class InnerAPP extends AppCompatActivity {
    ListView list;
    private static InnerAPP inst;
    Button danhBa, send;
    Switch maHoa;
    EditText textSend;
    TextView phoneContact,nameContact,hienThi;
    boolean position = false;
    static boolean check;
    chatAdapter adapter;
    Context ctx = InnerAPP.this;
    int i = 0;
    int j = 0;
    FileOutIn fileOut;
    List<Contact_IDs> listContact = null;
    List<Keyid> listid;
    Message msg = new Message();
    IntentFilter intentFilter;
    ECPoint publicKeycontact;
    ECC ecc = new ECC();
    Tonghop tonghop = new Tonghop();
    static String phonehere = new String();
    public static InnerAPP instance() {
        return inst;
    }

    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.innerapp);
        phoneContact = (TextView) findViewById(R.id.sdtContatc);
        hienThi = (TextView) findViewById(R.id.hienthi);
        nameContact = (TextView) findViewById(R.id.nameContact);
        list = (ListView) findViewById(R.id.listViewSMS);
        danhBa = (Button) findViewById(R.id.danhba);
        send = (Button) findViewById(R.id.send);
        textSend = (EditText) findViewById(R.id.sendText);
        adapter = new chatAdapter(ctx, R.layout.smslayout);
        maHoa = (Switch) findViewById(R.id.mahoahaykhong);
        maHoa.setChecked(false);
        hienThi.setText("Mã khóa : Đóng");
        intentFilter = new IntentFilter();
        intentFilter.addAction("SMS_RECEIVED_ACTION");
        send.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            adapter.add(new SMSpr(position, textSend.getText().toString()));
            if (listContact != null) {
                if(adapter.writeOut() != null) {
                    fileOut.writeList(InnerAPP.this, adapter.writeOut(), listContact.get(0).getNames() + listContact.get(0).getPhoneNumbers());
                    System.out.println("!@#@#@#@#@ " + textSend.getText().toString() + listContact.get(0).getPhoneNumbers());
                    if(!maHoa.isChecked()) {
                        msg.Message_send(InnerAPP.this, textSend.getText().toString(), listContact.get(0).getPhoneNumbers());
                    }else{
                        BigInteger n = fileOut.readEC(ctx, "PrivateKEY");
                        try {
                            msg.Message_send_mahoa(ctx, textSend.getText().toString(), listContact.get(0).getPhoneNumbers(), publicKeycontact, n);
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("asdasdasdasda");
                }
            }
            i = 2;
            j = 1;
            textSend.setText("");
         }
        });
        if(maHoa != null){
            maHoa.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        hienThi.setText("Mã khóa : Mở");
                        if (listContact != null) {
                            listid = fileOut.readListID(ctx, "PublicKeyList");
                            if (listid != null) {
                                System.out.println("CAI NAY CUNG KHONG NULL");
                                for (Keyid id : listid) {
                                    if (id.phone.equals(phoneContact.getText().toString())) {
                                        if (id.verify) {
                                            publicKeycontact = ecc.decode(id.pub);
                                            break;
                                        } else {
                                            maHoa.setChecked(false);
                                            hienThi.setText("Mã khóa : Đóng");
                                            System.out.println("CHua co Publickey or not Verified");
                                            break;
                                        }
                                    } else {
                                        maHoa.setChecked(false);
                                        System.out.println("CHua co Publickey or not Verified");
                                    }
                                }
                            } else {
                                maHoa.setChecked(false);
                                hienThi.setText("Mã khóa : Đóng");
                                System.out.println("CHua co Publickey or not Verifiedasdfasfd");
                            }
                        }
                    } else {
                        System.out.println("FALSEEEEEEEEEEEEEEEEEEEEEEEE");
                        maHoa.setChecked(false);
                        hienThi.setText("Mã khóa : Đóng");
                    }
                    i = 3;
                }
            });
    }

    danhBa.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(InnerAPP.this, DanhBa.class));
            i = 1;
        }
    });
}
    public void getAllSms() {
        adapter.clear();
        Uri message = Uri.parse("content://sms/");

        ContentResolver cr = getContentResolver();

        Cursor c = cr.query(message, null, null, null, "date ASC");
        int totalSMS = c.getCount();

        if (c.moveToFirst()) {
            for (int i = 0; i < totalSMS; i++) {
                System.out.println("Ten trong list  la :"+listContact.get(0).getNames());
                System.out.println("Ten trong sms  la :"+getContactName(
                        getApplicationContext(),
                        c.getString(c
                                .getColumnIndexOrThrow("address"))));
                if (listContact.get(0).getNames().equals(getContactName(
                        getApplicationContext(),
                        c.getString(c
                                .getColumnIndexOrThrow("address")))))
                    if (c.getString(c.getColumnIndexOrThrow("type")).equals("1")) {
                        adapter.add(new SMSpr(true, c.getString(c.getColumnIndexOrThrow("body"))));
                    } else {
                        adapter.add(new SMSpr(false, c.getString(c.getColumnIndexOrThrow("body"))));
                    }
                c.moveToNext();
            }
        }
        c.close();
    }
    public String getContactName(Context context, String phoneNumber) {
        ContentResolver cr = context.getContentResolver();
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
                Uri.encode(phoneNumber));
        Cursor cursor = cr.query(uri,
                new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME}, null, null, null);
        if (cursor == null) {
            return null;
        }
        String contactName = null;
        if (cursor.moveToFirst()) {
            contactName = cursor.getString(cursor
                    .getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return contactName;
    }
    public void updateList(final String smsMessage,final String phone) {
        Toast.makeText(this,phone + smsMessage, Toast.LENGTH_SHORT).show();
        phonehere =  listContact.get(0).getPhoneNumbers();
        check = maHoa.isChecked();
      if(phonehere.equals(phone)){
          if( smsMessage.length() > 20 &&smsMessage.substring(0,13).equals("<@PublicKey#>")){
                Toast.makeText(this,"Da nhan Public from: "+ phone, Toast.LENGTH_SHORT).show();
                listid = fileOut.readListID(ctx, "PublicKeyList");
                byte[] publicByte = Base64.decode(smsMessage.substring(13), Base64.DEFAULT);
                Keyid newKey = new Keyid(listContact.get(0).getNames(), listContact.get(0).getPhoneNumbers(), publicByte, false);
                listid.add(newKey);
                fileOut.writeListID(ctx, listid, "PublicKeyList");
            }else {
                if (!check) {
                    adapter.add(new SMSpr(true, smsMessage));
                   list.setAdapter(adapter);
                } else {
                    byte[] packet = Base64.decode(smsMessage, Base64.DEFAULT);
                    try {
                        String plaintext = tonghop.Tonghopgiaima(packet, publicKeycontact, fileOut.readEC(ctx, "PrivateKEY"));
                        if(tonghop.isVerifi()){
                            adapter.add(new SMSpr(true, plaintext));
                            list.setAdapter(adapter);
                        }else{
                            adapter.add(new SMSpr(true, "SMS khong duoc xac dinh chu ky"));
                            list.setAdapter(adapter);
                        }
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                Toast.makeText(this,"Ra ", Toast.LENGTH_SHORT).show();
                fileOut.writeList(InnerAPP.this, adapter.writeOut(), listContact.get(0).getNames() + listContact.get(0).getPhoneNumbers());
            }
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        list.setAdapter(adapter);
        list.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        if(adapter.writeOut() != null)
            adapter.registerDataSetObserver(new DataSetObserver() {
                @Override
                public void onChanged() {
                    super.onChanged();
                    list.setSelection(adapter.getCount() - 1);
                }
            });
        switch(i) {
            case 1:
                fileOut = new FileOutIn();
                listContact = (List<Contact_IDs>) fileOut.readObj(this, "quang");
                if (listContact != null) {
                    nameContact.setText(listContact.get(0).getNames());
                    phoneContact.setText(listContact.get(0).getPhoneNumbers());
                    adapter.clear();
                    if (fileOut.readList(this, listContact.get(0).getNames() + listContact.get(0).getPhoneNumbers()) == null) {
                        getAllSms();
                        System.out.println("BBBBBBBBBBBBBBBBBB");
                        fileOut.writeList(this, adapter.writeOut(),listContact.get(0).getNames() + listContact.get(0).getPhoneNumbers());
                        list.setAdapter(adapter);
                        i = 0;
                    }else{
                        adapter.readALL(fileOut.readList(this, listContact.get(0).getNames() + listContact.get(0).getPhoneNumbers()));
                        list.setAdapter(adapter);
                    }
                }
                System.out.println("Danh ba");
                break;
            case 2:
                System.out.println("Nut send");
                i = 0;
                break;
        }
    }
    @Override
    public void onBackPressed() {
        finish();
    }
    @Override
    public void onPause(){
        if(j ==1){
            j = 0;
            msg.unregister(ctx);
        }

        super.onPause();
    }
}
