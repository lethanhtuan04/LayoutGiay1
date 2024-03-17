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
import com.example.myapplication.fragment.CartFragment;
import com.example.myapplication.fragment.NotificationFragment;
import com.example.myapplication.model.Cart;
import com.example.myapplication.model.Discount;
import com.example.myapplication.model.Product;
import com.example.myapplication.utilities.SessionManager;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class DetailProActivity extends AppCompatActivity {
    ImageView btnBack, btnDetailtoCart, btnBell, imgMain;
    TextView txtPrice, txtName, txtGiakhicoDiscount, txtDetailPro;
    AppCompatButton btnAddtoCart;
    HashMap<String, String> userDetails;
    ImageView img1, img2, img3, img4;
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
        addEvent();
    }

    private void addEvent() {
        setBtnBack();
        AddtoCart();
        setGoCart();
        setBtnBell();
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

    private void setGoCart() {
        btnDetailtoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DetailProActivity.this, CartFragment.class));
            }
        });
    }

    private void setBtnBell() {
        btnBell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DetailProActivity.this, NotificationFragment.class));

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

    private void choseImage(Product product) {
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                byte[] imageByteArray = product.getImage1();
                displaySelectedImage(imageByteArray);
            }
        });

        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                byte[] imageByteArray = product.getImage2();
                displaySelectedImage(imageByteArray);
            }
        });

        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                byte[] imageByteArray = product.getImage3();
                displaySelectedImage(imageByteArray);
            }
        });

        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                byte[] imageByteArray = product.getImage4();
                displaySelectedImage(imageByteArray);
            }
        });
    }

    private void displaySelectedImage(byte[] imgage) {
        // Lấy hình ảnh từ ImageView đã chọn
        Bitmap bitmap = BitmapFactory.decodeByteArray(imgage, 0, imgage.length);
        imgMain.setImageBitmap(bitmap);
    }

    private void addControls() {
        btnBack = findViewById(R.id.btnBackPro);
        btnDetailtoCart = findViewById(R.id.btnDetailtoCart);
        btnBell = findViewById(R.id.btnBell);
        btnAddtoCart = findViewById(R.id.btnAddtoCart);

        txtName = findViewById(R.id.txtName);
        txtPrice = findViewById(R.id.txtPrice);
        txtDetailPro = findViewById(R.id.txtDetailPro);
        txtGiakhicoDiscount = findViewById(R.id.txtGiakhicoDiscount);

        imgMain = findViewById(R.id.imgMain);
        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        img3 = findViewById(R.id.img3);
        img4 = findViewById(R.id.img4);
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
            byte[] imageByteArray = product.getImage1();
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);
            imgMain.setImageBitmap(bitmap);

            byte[] imageByteArray1 = product.getImage1();
            Bitmap bitmap1 = BitmapFactory.decodeByteArray(imageByteArray1, 0, imageByteArray1.length);
            img1.setImageBitmap(bitmap1);

            byte[] imageByteArray2 = product.getImage2();
            Bitmap bitmap2 = BitmapFactory.decodeByteArray(imageByteArray2, 0, imageByteArray2.length);
            img2.setImageBitmap(bitmap2);

            byte[] imageByteArray3 = product.getImage3();
            Bitmap bitmap3 = BitmapFactory.decodeByteArray(imageByteArray3, 0, imageByteArray3.length);
            img3.setImageBitmap(bitmap3);

            byte[] imageByteArray4 = product.getImage4();
            Bitmap bitmap4 = BitmapFactory.decodeByteArray(imageByteArray4, 0, imageByteArray4.length);
            img4.setImageBitmap(bitmap4);

            choseImage(product);
        }
    }
}


