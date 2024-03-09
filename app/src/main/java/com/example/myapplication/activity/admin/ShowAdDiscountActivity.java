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
import com.example.myapplication.adapter.DiscountAdapter;
import com.example.myapplication.dbhelper.DiscountDBHelper;
import com.example.myapplication.model.Discount;

import java.util.List;

public class ShowAdDiscountActivity extends AppCompatActivity {

    ListView listView;
    ImageView back;
    ImageView add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_ad_discount);

        DiscountDBHelper discountDbHelper = new DiscountDBHelper(this);

        List<Discount> discounts = discountDbHelper.getAllDiscounts();

        DiscountAdapter adapter = new DiscountAdapter(this, R.layout.items_ad_discount, discounts);
        listView = findViewById(R.id.listview);
        listView.setAdapter(adapter);

        back = findViewById(R.id.btnBackToDashboard);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShowAdDiscountActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArrayAdapter<Discount> adapter = (ArrayAdapter<Discount>) parent.getAdapter();
                Discount discount = adapter.getItem(position);
                if (discount != null) {
                    Intent intent = new Intent(ShowAdDiscountActivity.this, AdminDiscountActivity.class);
                    intent.putExtra("discount", discount); // Gửi đối tượng Discount trực tiếp qua Intent
                    startActivity(intent);
                }
            }
        });
        add = findViewById(R.id.btnAdddiscount);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShowAdDiscountActivity.this, AdminDiscountActivity.class));
            }
        });

    }
}