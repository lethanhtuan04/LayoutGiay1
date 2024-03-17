package com.example.myapplication.activity.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.ProductAdapter;
import com.example.myapplication.adapter.ProductTypeAdapter;
import com.example.myapplication.dbhelper.ProductDBHelper;
import com.example.myapplication.dbhelper.ProductTypeDBHelper;
import com.example.myapplication.model.Product;
import com.example.myapplication.model.ProductType;

import java.io.Serializable;
import java.util.List;

public class ShowAdProductTypeActivity extends AppCompatActivity {
    ListView listviewProType;
    ImageView back2;
    ImageView addProType;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_ad_product_type);

        back2=findViewById(R.id.btnBackToDashboard2);
        back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowAdProductTypeActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        });

        addProType=findViewById(R.id.btnAdProType);
        addProType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShowAdProductTypeActivity.this, AdminProductTypeActivity.class));
            }
        });

        ProductTypeDBHelper productTypeDBHelper=new ProductTypeDBHelper(this);
        List<ProductType> productTypes=productTypeDBHelper.getAllProductsType();

        ProductTypeAdapter adapter=new ProductTypeAdapter(this,R.layout.items_ad_product_type,productTypes);
        listviewProType = findViewById(R.id.listviewProType);
        listviewProType.setAdapter(adapter);

        //Bấm vô list hiển thị qua trang admin
        listviewProType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArrayAdapter<ProductType> adapter1=(ArrayAdapter<ProductType>) parent.getAdapter();
                ProductType productType=adapter1.getItem(position);
//                if(productType !=null){
//                    Intent intent = new Intent(ShowAdProductTypeActivity.this, AdminProductTypeActivity.class);
//                   intent.putExtra("productType", (CharSequence) productType);
//                    startActivity(intent);
//                }

            }
        });
    }
}