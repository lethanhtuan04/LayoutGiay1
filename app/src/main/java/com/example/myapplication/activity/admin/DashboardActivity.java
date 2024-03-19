package com.example.myapplication.activity.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

public class DashboardActivity extends AppCompatActivity {

    LinearLayout btnDiscount, btnProductAdmin, btnProductType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        addControls();
        Event();
    }

    private void Event() {
        btnDiscount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, ShowAdDiscountActivity.class));
            }
        });

        btnProductAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, ShowAdProductActivity.class));
            }
        });

        btnProductType.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, ShowAdProductTypeActivity.class));
            }
        }));

    }

    private void addControls() {
        btnProductAdmin = findViewById(R.id.btnProductAdmin);
        btnDiscount = findViewById(R.id.btnDiscount);
        btnProductType = findViewById(R.id.btnProductType);
    }

    public void logout(View view) {
        startActivity(new Intent(DashboardActivity.this, MainActivity.class));
    }
}