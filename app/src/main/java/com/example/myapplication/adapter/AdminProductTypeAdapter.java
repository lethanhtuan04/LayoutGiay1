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
import com.example.myapplication.model.ProductType;

import java.util.List;

public class AdminProductTypeAdapter extends ArrayAdapter<ProductType> {
    Activity context;
    List<ProductType> arrayListProductType;
    int layoutResource;

    public AdminProductTypeAdapter(Activity context, int resource, List<ProductType> listProductType) {
        super(context, resource, listProductType);
        this.context = context;
        this.arrayListProductType = listProductType;
        this.layoutResource = resource;
    }

    static class ViewHolder {
        TextView id;
        TextView name;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        AdminProductTypeAdapter.ViewHolder holder;

        if (row == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            row = inflater.inflate(layoutResource, parent, false);
            holder = new AdminProductTypeAdapter.ViewHolder();
            holder.id = row.findViewById(R.id.txt_row_show_idProductType);
            holder.name = row.findViewById(R.id.txt_row_show_nameProductType);
            row.setTag(holder);
        } else {
            holder = (AdminProductTypeAdapter.ViewHolder) row.getTag();
        }

        holder.id.setText(String.valueOf(arrayListProductType.get(position).getId()));
        holder.name.setText(arrayListProductType.get(position).getName());
        return row;
    }


}
