package com.example.myapplication.activity.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.adapter.DiscountAdapter;
import com.example.myapplication.adapter.ProductAdapter;
import com.example.myapplication.dbhelper.DiscountDBHelper;
import com.example.myapplication.dbhelper.ProductDBHelper;
import com.example.myapplication.model.Discount;
import com.example.myapplication.model.Product;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class ShowAdProductActivity extends AppCompatActivity {

    ListView listView;
    ImageView back1;
    ImageView addProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_ad_product);

        ProductDBHelper productDBHelper = new ProductDBHelper(this);
        List<Product> products = productDBHelper.getAllProducts();

        ProductAdapter adapter = new ProductAdapter(this, R.layout.items_ad_product, products);
        listView = findViewById(R.id.listviewproduct);
        listView.setAdapter(adapter);

        back1 = findViewById(R.id.btnBackToDashboard1);
        back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowAdProductActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArrayAdapter<Product> adapter = (ArrayAdapter<Product>) parent.getAdapter();
                Product product = adapter.getItem(position);
                if (product != null) {
                    Intent intent = new Intent(ShowAdProductActivity.this, AdminProductActivity.class);
                    intent.putExtra("product",
                            product); // Gửi đối tượng Discount trực tiếp qua Intent
                    startActivity(intent);
                }
            }
        });

        addProduct = findViewById(R.id.btnAddproduct);
        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShowAdProductActivity.this, AdminProductActivity.class));
            }
        });
    }
}
