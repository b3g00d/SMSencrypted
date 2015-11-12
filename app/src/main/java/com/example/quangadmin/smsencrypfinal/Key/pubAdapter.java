package com.example.quangadmin.smsencrypfinal.Key;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.quangadmin.smsencrypfinal.Khoigiaima.ECC;
import com.example.quangadmin.smsencrypfinal.R;

import java.util.List;

/**
 * Created by QUANGADMIN on 10/17/2015.
 */
public class pubAdapter extends ArrayAdapter<Keyid> {
    private Context context;
    private List<Keyid> list = null;
    private TextView name;
    private ECC ecc= new ECC();
    private FrameLayout layout;
    public pubAdapter(Context context, int resource){
        super(context,resource);
        this.context = context;
    }
    public int getCount() {
        if(list == null)
            return 0;
        return list.size();
    }

    public void add(Keyid object) {
        list.add(object);
        super.add(object);

    }
    public void readALL(List<Keyid> listread){
        if(listread != null)
            this.list = listread;
    }
    public List<Keyid> writeOut(){
        return list;
    }

    public Keyid getItem(int position) {
        return list.get(position);
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflator.inflate(R.layout.keyeachcontact,parent,false);
        }
        name = (TextView) convertView.findViewById(R.id.nameID);
        String nameID1, phoneID2, pubID3;
        layout = (FrameLayout) convertView.findViewById(R.id.layOut);
        boolean verifyID;
        Keyid id = getItem(position);
        nameID1 = id.name;
        phoneID2= id.phone;
        pubID3 = ecc.bytestoHEX(id.pub);
        verifyID = id.verify;
        System.out.println("Name la "+nameID1+" phonela "+phoneID2+" publickey la "+pubID3+" verifiy? "+ verifyID);
        name.setText(nameID1+" :"+phoneID2+"\n"+pubID3);
        layout.setBackgroundColor(verifyID ? context.getResources().getColor(R.color.red) : context.getResources().getColor(R.color.blue));
        return convertView;
    }
}
