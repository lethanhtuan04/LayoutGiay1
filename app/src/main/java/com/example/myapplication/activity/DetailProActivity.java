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
import com.example.myapplication.dbhelper.DiscountDBHelper;
import com.example.myapplication.model.Discount;
import com.example.myapplication.model.Product;

import java.text.DecimalFormat;

public class DetailProActivity extends AppCompatActivity {
    ImageView btnBack, btnDetailtoCart, imgMain;
    TextView txtPrice, txtName, txtGiakhicoDiscount, txtDetailPro;
    AppCompatButton btnAddtoCart;

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
        txtDetailPro = findViewById(R.id.txtDetailPro);
        btnAddtoCart = findViewById(R.id.btnAddtoCart);
        txtGiakhicoDiscount = findViewById(R.id.txtGiakhicoDiscount);
    }

    @SuppressLint("SetTextI18n")
    private void viewDetailPro() {
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        Intent intent = getIntent();
        Product product = (Product) intent.getSerializableExtra("product");
        // Hiển thị thông tin sản phẩm (ví dụ: tên sản phẩm và hình ảnh)
        if (product != null) {
            DiscountDBHelper discountDBHelper = new DiscountDBHelper(this);
            Discount discount = discountDBHelper.getDiscountByProductID(product.getId());
            if (discount != null) {
                double priceDiscount = product.getPrice() - product.getPrice() * discount.getValue() / 100;
                String giasaokhigiam = decimalFormat.format(priceDiscount);
                txtPrice.setText(giasaokhigiam + "đ");
                String price = decimalFormat.format(product.getPrice());
                txtGiakhicoDiscount.setText(price + "đ");
            } else {
                String price = decimalFormat.format(product.getPrice());
                txtPrice.setText(price + "đ");
                txtGiakhicoDiscount.setVisibility(View.GONE);
            }
           txtDetailPro.setText(product.getDetail());
            txtName.setText(product.getName());
            // Hiển thị hình ảnh từ mảng byte (BLOB)
            byte[] imageByteArray = product.getImage();
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);
            imgMain.setImageBitmap(bitmap);
        }
    }
}


