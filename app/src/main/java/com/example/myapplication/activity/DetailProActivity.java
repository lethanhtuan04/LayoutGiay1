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

import com.example.myapplication.R;
import com.example.myapplication.fragment.CartFragment;
import com.example.myapplication.model.Product;

public class DetailProActivity extends AppCompatActivity {

    ImageView btnBack, btnDetailtoCart, imgMain;
    TextView txtPrice, txtName;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pro);
        addControls();
        viewDetailPro();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
btnDetailtoCart.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        startActivity(new Intent(DetailProActivity.this, CartFragment.class));
    }
});

//
    }

    private void addControls() {
        btnBack = findViewById(R.id.btnBackPro);
        btnDetailtoCart = findViewById(R.id.btnDetailtoCart);
        txtName = findViewById(R.id.txtName);
        txtPrice = findViewById(R.id.txtPrice);
        imgMain = findViewById(R.id.imgMain);
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


