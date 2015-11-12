package com.example.quangadmin.smsencrypfinal.Danhba;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.quangadmin.smsencrypfinal.Filecontact.FileOutIn;
import com.example.quangadmin.smsencrypfinal.R;

import java.util.ArrayList;
import java.util.List;

public class DanhBa extends AppCompatActivity {
    private List<Contact_ID> listContact = new ArrayList<>();
    private ListView listViewContact;
    private List<Contact_IDs> listContactPhone = new ArrayList<>();
    private FileOutIn fileWrite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_danhba);
        ContentResolver cr = getContentResolver();
        listViewContact = (ListView) findViewById(R.id.listViewDanhBa);
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                // Contact_ID a = new Contact_ID(null, null);
                String id = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));
                if (Integer.parseInt(cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {

                        //  a.setName(name);
                        String phoneNumber = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
                        //a.setPhoneNumber(phoneNumber);

                        listContact.add(new Contact_ID(name, phoneNumber));

                    }
                    pCur.close();
                }
            }
        }

        ListContactAdapter adapter = new ListContactAdapter(DanhBa.this, R.layout.layout_getallcontact_item, listContact);
        listViewContact.setAdapter(adapter);
        listViewContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contact_IDs apk = new Contact_IDs(null, null);
                String phone = "";
                char[] phoneChar =listContact.get(position).getPhoneNumber().toCharArray();
                for(int i = 0; i < phoneChar.length;i++){
                  if(!(phoneChar[i] == '(' || phoneChar[i] == ')' || phoneChar[i] == ' '|| phoneChar[i] == '-')){
                      phone += phoneChar[i];
                  }
                }
                apk.setPhoneNumbers(phone);
                apk.setNames(listContact.get(position).getName());
                listContactPhone.add(apk);
                Log.w("TESTTTTT", "" + listContactPhone.get(0).getPhoneNumbers() + phone);
                fileWrite = new FileOutIn();
                fileWrite.writeObj(DanhBa.this, listContactPhone, "quang");
                finish();
            }
        });
    }
    public void onBackPressed() {
        fileWrite = new FileOutIn();
        fileWrite.writeObj(DanhBa.this, null, "quang");
        finish();
    }
}
