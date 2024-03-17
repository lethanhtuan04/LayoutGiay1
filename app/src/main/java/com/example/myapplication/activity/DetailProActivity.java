package com.example.myapplication.activity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.myapplication.R;
import com.example.myapplication.dbhelper.CartDBHelper;
import com.example.myapplication.dbhelper.DiscountDBHelper;
import com.example.myapplication.model.Cart;
import com.example.myapplication.model.Discount;
import com.example.myapplication.model.Product;
import com.example.myapplication.utilities.SessionManager;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class DetailProActivity extends AppCompatActivity {
    ImageView btnBack, btnDetailtoCart, imgMain;
    TextView txtPrice, txtName, txtGiakhicoDiscount, txtDetailPro;
    AppCompatButton btnAddtoCart;
    HashMap<String, String> userDetails;
    SessionManager sessionManager;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pro);
        sessionManager = new SessionManager(DetailProActivity.this); // Khởi tạo sessionManager
        userDetails = sessionManager.getUserDetails();
        addControls();
        viewDetailPro();
        setBtnBack();
        AddtoCart();
    }

    private void AddtoCart() {
        btnAddtoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sessionManager.isLoggedIn()) {
                    Product product = (Product) getIntent().getSerializableExtra("product");
                    String id = userDetails.get(sessionManager.KEY_IDUSER);
                    // Thêm sản phẩm vào giỏ hàng ở đây
                    if (isProductInCart(product)) {
                        Toast.makeText(DetailProActivity.this, "Sản phẩm đã tồn tại trong giỏ hàng!", Toast.LENGTH_SHORT).show();
                    } else {
                        CartDBHelper cartDbHelper = new CartDBHelper(DetailProActivity.this);
                        Cart cart = new Cart(Integer.parseInt(id), product.getId());
                        long rowId = cartDbHelper.insert(cart);

                        if (rowId > 0) {
                            Toast.makeText(DetailProActivity.this, "Đã thêm vào giỏ hàng!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(DetailProActivity.this, "Đã xảy ra lỗi khi thêm vào giỏ hàng.", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Intent intent = new Intent(DetailProActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private boolean isProductInCart(Product product) {
        boolean isExist = false;
        CartDBHelper cartDBHelper = new CartDBHelper(DetailProActivity.this);
        ArrayList<Cart> cartItems = cartDBHelper.getAllCarts(Integer.parseInt(userDetails.get(sessionManager.KEY_IDUSER)));

        for (Cart cart : cartItems) {
            if (cart.getProductId() == product.getId()) {
                isExist = true;
                break;
            }
        }

        return isExist;
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

        if (product != null) {
            DiscountDBHelper discountDBHelper = new DiscountDBHelper(this);
            Discount discount = discountDBHelper.getDiscountByProductID(product.getId());

            if (discount != null) {

                double discountedPrice = DiscountDBHelper.calculateDiscountedPrice(product, discount);
                String giasaokhigiam = decimalFormat.format(discountedPrice);
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


