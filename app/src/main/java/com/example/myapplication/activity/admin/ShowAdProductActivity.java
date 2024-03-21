package com.example.myapplication.activity.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.adapter.AdminProductAdapter;
import com.example.myapplication.dbhelper.ProductDBHelper;
import com.example.myapplication.model.Product;

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

        AdminProductAdapter adapter = new AdminProductAdapter(this, R.layout.items_ad_product, products);
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
                    intent.putExtra("idpro", product.getId()); // Gửi đối tượng Discount trực tiếp qua Intent
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
