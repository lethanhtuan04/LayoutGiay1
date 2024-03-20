package com.example.myapplication.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.BillAdapter;
import com.example.myapplication.dbhelper.BillDBHelper;
import com.example.myapplication.model.Bill;
import com.example.myapplication.utilities.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;

public class StatusBillActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<Bill> bills;
    SessionManager sessionManager;
    ImageView btnBack;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_bill);

        recyclerView = findViewById(R.id.rvStatusBill);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        sessionManager = new SessionManager(this);
        // Lấy thông tin người dùng từ SessionManager
        HashMap<String, String> userDetails = sessionManager.getUserDetails();
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (userDetails != null) {
            // Lấy iduser từ userDetails
            String id = userDetails.get(SessionManager.KEY_IDUSER);
            if (id != null) {
                // Hiển thị danh sách hóa đơn
                setBillDisplay(Integer.parseInt(id));
            } else {
                // Hiển thị thông báo nếu không tìm thấy iduser
                Toast.makeText(this, "User ID not found", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Hiển thị thông báo nếu không có thông tin người dùng
            Toast.makeText(this, "User details not found", Toast.LENGTH_SHORT).show();
        }
    }

    private void setBillDisplay(int userId) {
        // Tạo đối tượng BillDBHelper
        BillDBHelper billDbHelper = new BillDBHelper(StatusBillActivity.this);
        // Lấy danh sách hóa đơn từ cơ sở dữ liệu
        bills = billDbHelper.getAllBillsExceptReceived(userId);

        if (bills != null && !bills.isEmpty()) {
            // Nếu danh sách hóa đơn không rỗng, hiển thị lên RecyclerView
            BillAdapter adapter = new BillAdapter( this,bills);
            recyclerView.setAdapter(adapter);
        } else {
            // Nếu không có hóa đơn nào, hiển thị thông báo
            Toast.makeText(this, "No bills found", Toast.LENGTH_SHORT).show();
        }
    }
}
