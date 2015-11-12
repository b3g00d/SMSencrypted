package com.example.quangadmin.smsencrypfinal.Chat;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.quangadmin.smsencrypfinal.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by QUANGADMIN on 10/16/2015.
 */
public class chatAdapter extends ArrayAdapter<SMSpr> {
    private List<SMSpr> send_list =new ArrayList<SMSpr>();
    private TextView chat_text;
    private Context ctx;
       public chatAdapter(Context context, int resource){
           super(context,resource);
           this.ctx = context;
       }

    @Override
    public void add(SMSpr object) {
        send_list.add(object);
        super.add(object);

    }
    public void clear(){
        send_list = new ArrayList<SMSpr>();
    }
    public List<SMSpr> writeOut(){
        return send_list;
    }
    public void readALL(List<SMSpr> listread){
        if(listread != null)
        this.send_list = listread;
    }

    @Override
    public int getCount() {
        if(send_list == new ArrayList<SMSpr>())
            return 0;
        return send_list.size();
    }

    @Override
    public SMSpr getItem(int position) {
        return send_list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflator = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflator.inflate(R.layout.smslayout,parent,false);
        }
        chat_text = (TextView) convertView.findViewById(R.id.log);
        String message;
        boolean POSITION;
        SMSpr chat = getItem(position);
        message = chat.message;
        POSITION = chat.position;
        chat_text.setText(message);
        chat_text.setBackgroundResource(POSITION ? R.drawable.left : R.drawable.right);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        if(!POSITION){
            params.gravity = Gravity.RIGHT;
        }else{
            params.gravity = Gravity.LEFT;
        }
        chat_text.setLayoutParams(params);
        return convertView;
    }
}
