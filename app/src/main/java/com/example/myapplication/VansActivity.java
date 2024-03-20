//package com.example.myapplication;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.ImageView;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.GridLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.myapplication.activity.NikeActivity;
//import com.example.myapplication.activity.NotificationActivity;
//import com.example.myapplication.adapter.ProductRecyclerViewAdapter;
//import com.example.myapplication.dbhelper.ProductDBHelper;
//import com.example.myapplication.model.Product;
//
//import java.util.ArrayList;
//
//public class VansActivity extends AppCompatActivity {
//
//        RecyclerView recyclerView;
//        ImageView back, noti;
//        ProductDBHelper productDBHelper;
//        ArrayList<Product> productList;
//        private ProductRecyclerViewAdapter productRecyclerViewAdapter;
//
//
//        @Override
//        protected void onCreate(Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//            setContentView(R.layout.activity_nike);
//            recyclerView = findViewById(R.id.rcSPtheoHang);
//            back = findViewById(R.id.btnBackHome);
//            noti = findViewById(R.id.btnNoti);
//            back.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    finish();
//                }
//            });
//            noti.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    startActivity(new Intent(VansActivity.this, NotificationActivity.class));
//                }
//            });
//            setupVerticalRecyclerView();
//        }
//
//        private void setupVerticalRecyclerView() {
//            recyclerView.setHasFixedSize(true);
//            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
//            recyclerView.setLayoutManager(gridLayoutManager);
//            productDBHelper = new ProductDBHelper(this);
//            productList = productDBHelper.getProductsByType(1)  ;
//            productRecyclerViewAdapter = new ProductRecyclerViewAdapter(productList, this);
//            recyclerView.setAdapter(productRecyclerViewAdapter);
//        }
//    }
//}