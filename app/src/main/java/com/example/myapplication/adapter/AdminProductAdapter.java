package com.example.myapplication.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;
import com.example.myapplication.model.Product;

import java.text.DecimalFormat;
import java.util.List;


public class AdminProductAdapter extends ArrayAdapter<Product> {
    Activity context;
    List<Product> arrayListProduct;
    int layoutResource;

    public AdminProductAdapter(Activity context, int resource, List<Product> listProduct) {
        super(context, resource, listProduct);
        this.context = context;
        this.arrayListProduct = listProduct;
        this.layoutResource = resource;
    }


    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;

        if (row == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            row = inflater.inflate(layoutResource, parent, false);
            holder = new ViewHolder();
            holder.id = row.findViewById(R.id.idPro);
            holder.name = row.findViewById(R.id.namePro);
            holder.img = row.findViewById(R.id.listImage);
            holder.price = row.findViewById(R.id.pricePro);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }
        DecimalFormat decimalFormat = new DecimalFormat("#,###");

        holder.id.setText("ID: " + String.valueOf(arrayListProduct.get(position).getId()));
        holder.name.setText(arrayListProduct.get(position).getName());

        byte[] imageByteArray = arrayListProduct.get(position).getImage1();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);
        holder.img.setImageBitmap(bitmap);
        String price = decimalFormat.format(arrayListProduct.get(position).getPrice());

        holder.price.setText(price + "");
        return row;
    }

    static class ViewHolder {
        TextView id, name, price;
        ImageView img;
    }
}

