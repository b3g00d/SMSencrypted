package com.example.quangadmin.smsencrypfinal.Danhba;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.example.quangadmin.smsencrypfinal.Danhba.Contact_ID;
import com.example.quangadmin.smsencrypfinal.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nhocnhinho on 7/11/2015.
 */
public class ListContactAdapter extends ArrayAdapter<Contact_ID> {
private LayoutInflater layoutInflater;
        List<Contact_ID> mCustomers;

private Filter mFilter = new Filter() {
@Override
public String convertResultToString(Object resultValue) {
        return ((Contact_ID)resultValue).getName();
        }

@Override
protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();

        if (constraint != null) {
        ArrayList<Contact_ID> suggestions = new ArrayList<Contact_ID>();
        for (Contact_ID customer : mCustomers) {
        // Note: change the "contains" to "startsWith" if you only want starting matches
        if (customer.getName().toLowerCase().contains(constraint.toString().toLowerCase())||
                customer.getPhoneNumber().toLowerCase().contains(constraint.toString().toLowerCase())) {
        suggestions.add(customer);
        }
        }

        results.values = suggestions;
        results.count = suggestions.size();
        }

        return results;
        }

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
@Override
protected void publishResults(CharSequence constraint, FilterResults results) {
        clear();
        if (results != null && results.count > 0) {
        // we have filtered results
        addAll((ArrayList<Contact_ID>) results.values);
        } else {
        // no filter, add entire original list back in
        addAll(mCustomers);
        }
        notifyDataSetChanged();
        }
        };

public ListContactAdapter(Context context, int textViewResourceId, List<Contact_ID> customers) {
        super(context, textViewResourceId, customers);
        // copy all the customers into a master list
        mCustomers = new ArrayList<Contact_ID>(customers.size());
        mCustomers.addAll(customers);
        layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

@Override
public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
        view = layoutInflater.inflate(R.layout.layout_getallcontact_item, null);
        }

        Contact_ID customer = getItem(position);

        TextView name = (TextView) view.findViewById(R.id.textViewTen);
        name.setText(customer.getName());
    TextView phone = (TextView) view.findViewById(R.id.textViewSdt);
    phone.setText(customer.getPhoneNumber());

        return view;
        }

@Override
public Filter getFilter() {
        return mFilter;
        }
        }