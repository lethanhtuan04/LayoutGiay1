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
import com.example.myapplication.model.Discount;

import java.util.List;

public class DiscountAdapter extends ArrayAdapter<Discount> {
    Activity context;
    List<Discount> arrayListDiscount;
    int layoutResource;

    public DiscountAdapter(Activity context, int resource, List<Discount> listDiscount)
    {
        super(context, resource, listDiscount);
        this.context = context;
        this.arrayListDiscount = listDiscount;
        this.layoutResource = resource;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater=this.context.getLayoutInflater();//build layout thanh java de android su dung
        View row=layoutInflater.inflate(this.layoutResource,null);

        TextView discountId = row.findViewById(R.id.txt_row_show_idDiscount);
        discountId.setText(arrayListDiscount.get(position).getId().toString());
        TextView productId = row.findViewById(R.id.txt_row_show_productIdDiscount);
        productId.setText(arrayListDiscount.get(position).getProductId().toString());
        TextView value = row.findViewById(R.id.txt_row_show_valueDiscount);
        value.setText(arrayListDiscount.get(position).getValue()+"");

        return row;
    }
}

