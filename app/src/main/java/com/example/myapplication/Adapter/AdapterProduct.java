package com.example.myapplication.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.model.Product;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AdapterProduct extends BaseAdapter {
    Context context;
    ArrayList<Product>list;

    public AdapterProduct(Context context, ArrayList<Product> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row=inflater.inflate(R.layout.activity_listviewproad, null);
        ImageView imgHinhGiay = row.findViewById(R.id.imgHinhGiay);
        TextView txtIdSP = row.findViewById(R.id.txtIdSP);
        TextView txtNameSP = row.findViewById(R.id.txtNameSp);
        TextView txtSttSp = row.findViewById(R.id.txtSttSp);
        Button btnFix = row.findViewById(R.id.btnFix);
        Button btnDelete = row.findViewById(R.id.btnDelete);

        Product proDuct = list.get(position);
        txtIdSP.setText(proDuct.getId().toString());
        txtNameSP.setText(proDuct.getName().toString());
        txtSttSp.setText(proDuct.getStatus().toString());

        //Bitmap bmHinhGiay = BitmapFactory.decodeFile()  lấy hình

        return row;
    }
}
