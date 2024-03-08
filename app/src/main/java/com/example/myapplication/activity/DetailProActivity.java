package com.example.myapplication.activity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.myapplication.R;
import com.example.myapplication.model.Product;
import com.example.myapplication.utilities.SessionManager;

import java.util.HashMap;

public class DetailProActivity extends AppCompatActivity {
    SessionManager sessionManager;
    ImageView btnBack, btnDetailtoCart, imgMain;
    TextView txtPrice, txtName;
    AppCompatButton btnAddtoCart;
    TextView test;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pro);
        addControls();
        viewDetailPro();
        setBtnBack();
        AddtoCart();
    }

    private void AddtoCart() {
        btnAddtoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager = new SessionManager(getApplicationContext());
                if (!sessionManager.isLoggedIn()) {
                    startActivity(new Intent(DetailProActivity.this, LoginActivity.class));
                } else {
                    // Nếu đã đăng nhập, lấy thông tin session và hiển thị
                    HashMap<String, String> userDetails = sessionManager.getUserDetails();
                    String username = userDetails.get(SessionManager.KEY_USERNAME);
                    // Hiển thị thông tin username và email
                    test.setText(username);

                }
            }
        });
    }

    private void setBtnBack() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void addControls() {
        btnBack = findViewById(R.id.btnBackPro);
        btnDetailtoCart = findViewById(R.id.btnDetailtoCart);
        txtName = findViewById(R.id.txtName);
        txtPrice = findViewById(R.id.txtPrice);
        imgMain = findViewById(R.id.imgMain);
        btnAddtoCart = findViewById(R.id.btnAddtoCart);
        test=findViewById(R.id.testt);
    }

    private void viewDetailPro() {
        Intent intent = getIntent();
        Product product = (Product) getIntent().getSerializableExtra("product");
        // Hiển thị thông tin sản phẩm (ví dụ: tên sản phẩm và hình ảnh)
        if (product != null) {
            txtName.setText(product.getName());
            txtPrice.setText(product.getPrice() + "");
            // Hiển thị hình ảnh từ mảng byte (BLOB)
            byte[] imageByteArray = product.getImage();
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);
            imgMain.setImageBitmap(bitmap);
        }
    }
}


