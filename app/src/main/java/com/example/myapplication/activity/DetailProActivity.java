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
import com.example.myapplication.dbhelper.ProductDBHelper;
import com.example.myapplication.fragment.CartFragment;
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
    ProductDBHelper productDBHelper;
    Product product;

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
                    String id = userDetails.get(sessionManager.KEY_IDUSER);

                    int productid = (int) getIntent().getSerializableExtra("id");
                    productDBHelper = new ProductDBHelper(getApplicationContext());
                    product = productDBHelper.getProductById(productid);

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
        ArrayList<Cart> cartItems = cartDBHelper.getAllCartByStatus(Integer.parseInt(userDetails.get(sessionManager.KEY_IDUSER)), "wait");
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
                sessionManager = new SessionManager(DetailProActivity.this);
                if (sessionManager.isLoggedIn())
//                    startActivity(new Intent(DetailProActivity.this, CartFragment.class));
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.cart_fragment, new CartFragment())
                            .commit();
                else
                    startActivity(new Intent(DetailProActivity.this, LoginActivity.class));

            }
        });
    }

    private void setBtnBell() {
        btnBell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager = new SessionManager(DetailProActivity.this);
                if (sessionManager.isLoggedIn())
                    startActivity(new Intent(DetailProActivity.this, NotificationActivity.class));
                else
                    startActivity(new Intent(DetailProActivity.this, LoginActivity.class));

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

    private void viewDetailPro() {
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        Intent intent = getIntent();
        int productid = (int) intent.getSerializableExtra("id");
        productDBHelper = new ProductDBHelper(getApplicationContext());
        product = productDBHelper.getProductById(productid);


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
            displaySelectedImage(product.getImage1(), imgMain);
            displaySelectedImage(product.getImage1(), img1);
            displaySelectedImage(product.getImage2(), img2);
            displaySelectedImage(product.getImage3(), img3);
            displaySelectedImage(product.getImage4(), img4);

            choseImage(product);
        }
    }

    private void displaySelectedImage(byte[] imgage, ImageView imageView) {
        if (imgage != null && imgage.length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imgage, 0, imgage.length);
            imageView.setImageBitmap(bitmap);
        }
    }

}


