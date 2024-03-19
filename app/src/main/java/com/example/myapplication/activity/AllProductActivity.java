package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.ProductRecyclerViewAdapter;
import com.example.myapplication.dbhelper.ProductDBHelper;
import com.example.myapplication.model.Product;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AllProductActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ProductDBHelper productDBHelper;
    ImageView btnBack, btnNoti;
    List<Product> productList;
    TextView btnAllPro, btnMax, btnMin, btnDiscount;
    ProductRecyclerViewAdapter productRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_product);
        addControls();
        setupRecyclerView();
        loadProducts();
        addClickListeners();
        chooseSearch();
    }

    // thêm phương thức mới để tìm kiếm và sắp xếp sản phẩm từ cao đến thấp.
    private void searchAndSortByMaxPrice() {
        List<Product> searchResults = productDBHelper.getAllProducts(); // Lấy tất cả sản phẩm từ cơ sở dữ liệu
        Collections.sort(searchResults, new Comparator<Product>() { // Sắp xếp sản phẩm từ cao đến thấp
            @Override
            public int compare(Product p1, Product p2) {
                return Double.compare(p2.getPrice(), p1.getPrice());
            }
        });
        productRecyclerViewAdapter.updateList(searchResults); // Cập nhật danh sách sản phẩm trên RecyclerView
    }

    private void searchAndSortByMinPrice() {
        List<Product> searchResults = productDBHelper.getAllProducts(); // Lấy tất cả sản phẩm từ cơ sở dữ liệu
        Collections.sort(searchResults, new Comparator<Product>() { // Sắp xếp sản phẩm từ thấp đến cao
            @Override
            public int compare(Product p1, Product p2) {
                return Double.compare(p1.getPrice(), p2.getPrice());
            }
        });
        productRecyclerViewAdapter.updateList(searchResults); // Cập nhật danh sách sản phẩm trên RecyclerView
    }

    // Trong lớp AllProductActivity, thêm phương thức mới để tải và hiển thị danh sách các sản phẩm có giảm giá.
    private void loadDiscountedProducts() {
        productDBHelper = new ProductDBHelper(AllProductActivity.this);
        List<Product> discountedProducts = productDBHelper.getDiscountProducts();
        productRecyclerViewAdapter = new ProductRecyclerViewAdapter(discountedProducts, this);
        recyclerView.setAdapter(productRecyclerViewAdapter);
    }


    private void setupRecyclerView() {
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(AllProductActivity.this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
    }

    private void loadProducts() {
        productDBHelper = new ProductDBHelper(AllProductActivity.this);
        productList = productDBHelper.getAllProducts();
        productRecyclerViewAdapter = new ProductRecyclerViewAdapter(productList, this);
        recyclerView.setAdapter(productRecyclerViewAdapter);
    }


    private void chooseSearch() {
        btnMax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchAndSortByMaxPrice(); // Khi nhấn vào nút btnMax, tìm kiếm và sắp xếp sản phẩm theo giá từ cao đến thấp
            }
        });
        btnMin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchAndSortByMinPrice(); // Khi nhấn vào nút btnMin, tìm kiếm và sắp xếp sản phẩm theo giá từ thấp đến cao
            }
        });
        btnDiscount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadDiscountedProducts();
            }
        });
        btnAllPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadProducts();
            }
        });
    }

    private void addClickListeners() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnNoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AllProductActivity.this, NotificationActivity.class));
            }
        });
    }

    private void addControls() {
        recyclerView = findViewById(R.id.resultAllPro);
        btnBack = findViewById(R.id.btnBackHome);
        btnNoti = findViewById(R.id.btnNoti);
        btnAllPro = findViewById(R.id.allPro);
        btnMax = findViewById(R.id.searchPriceMax);
        btnMin = findViewById(R.id.searchPriceMin);
        btnDiscount = findViewById(R.id.searchDiscount);

    }
}
