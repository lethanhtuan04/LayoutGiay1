package com.example.myapplication.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;
import com.example.myapplication.model.Bill;
import com.example.myapplication.model.User;

import java.util.ArrayList;
import java.util.List;


public class BillAdapter extends ArrayAdapter<Bill> {
    Activity context;
    List<Bill> bills;
    int layoutResource;

    public BillAdapter(Activity context, int resource, List<Bill> objects) {
        super(context, resource, objects);
        this.context = context;
        this.bills = objects;
        this.layoutResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater=this.context.getLayoutInflater();//build layout thanh java de android su dung
        View row=layoutInflater.inflate(this.layoutResource,null);

        TextView billid = row.findViewById(R.id.txtbillId);
        billid.setText(bills.get(position).getId().toString());
        TextView cartid = row.findViewById(R.id.txtcartId);
        cartid.setText(bills.get(position).getCartId().toString());
        TextView userid = row.findViewById(R.id.txtuserId);
        cartid.setText(bills.get(position).getUserId().toString());
        TextView phone = row.findViewById(R.id.txtphoneBill);
        cartid.setText(bills.get(position).getPhone().toString());
        TextView address = row.findViewById(R.id.txtaddressBill);
        cartid.setText(bills.get(position).getAddress().toString());
//        TextView price = row.findViewById(R.id.txtpriceBill);
//        cartid.setText(bills.get(position).getPrice().toString());

        return row;
    }
//        @NonNull
//        public View View (int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//            LayoutInflater layoutInflater = this.context.getLayoutInflater();
//            View row = layoutInflater.inflate(this.layoutResource, null);
//
//            TextView userId = row.findViewById(R.id.txt_iduser);
//            userId.setText(users.get(position).getId().toString());
//            TextView userName = row.findViewById(R.id.txt_nameuser);
//            userName.setText(users.get(position).getFullname());
//            return row;
//        }
}

