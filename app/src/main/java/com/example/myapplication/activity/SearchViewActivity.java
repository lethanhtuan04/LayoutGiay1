package com.example.myapplication.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.adapter.ProductRecyclerViewAdapter;
import com.example.myapplication.dbhelper.ProductDBHelper;
import com.example.myapplication.model.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SearchViewActivity extends AppCompatActivity {

    ImageView btnBackHome, btnNoti;
    EditText edtSearch;
    RecyclerView rcvsearchResult;
    TextView btnAllPro, btnMax, btnMin, btnDiscount;

    ProductRecyclerViewAdapter productRecyclerViewAdapter;
    ProductDBHelper productDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view);

        addControls();
        addHand();
        edtSearch.requestFocus();
        loadProducts();
        chooseSearch();

         //Khởi tạo GridLayoutManager và productRecyclerViewAdapter ở đây
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rcvsearchResult.setLayoutManager(gridLayoutManager);
        productRecyclerViewAdapter = new ProductRecyclerViewAdapter(new ArrayList<Product>(), this);
        rcvsearchResult.setAdapter(productRecyclerViewAdapter);

        //khi tìm kí tự sẽ hiện sản phẩm có trùng ký tự
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = s.toString();
                searchProducts(query);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

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
        productDBHelper = new ProductDBHelper(this);
        List<Product> discountedProducts = productDBHelper.getDiscountProducts();
        productRecyclerViewAdapter = new ProductRecyclerViewAdapter(discountedProducts, this);
        rcvsearchResult.setAdapter(productRecyclerViewAdapter);
    }
    private void searchProducts(String query) {
        productDBHelper = new ProductDBHelper(this);
        List<Product> searchResults = productDBHelper.searchProductsByName(query);
        productRecyclerViewAdapter.updateData(searchResults);
    }
    private void loadProducts() {
        productDBHelper = new ProductDBHelper(this);
        List<Product> productList = productDBHelper.getAllProducts();
        productRecyclerViewAdapter = new ProductRecyclerViewAdapter(productList, this);
        rcvsearchResult.setAdapter(productRecyclerViewAdapter);
    }

    private void addHand() {
        btnNoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SearchViewActivity.this, NotificationActivity.class));
            }
        });
        btnBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SearchViewActivity.this, MainActivity.class));
            }
        });
    }

    @SuppressLint("WrongViewCast")
    private void addControls() {
        btnBackHome = findViewById(R.id.btnBackHome);
        btnNoti = findViewById(R.id.btnNoti);
        edtSearch = findViewById(R.id.edtsearch);
        rcvsearchResult = findViewById(R.id.resultSearch);

        btnAllPro = findViewById(R.id.allPro);
        btnMax = findViewById(R.id.searchPriceMax);
        btnMin = findViewById(R.id.searchPriceMin);
        btnDiscount = findViewById(R.id.searchDiscount);
    }


}