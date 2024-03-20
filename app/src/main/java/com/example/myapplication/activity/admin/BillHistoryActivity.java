package com.example.myapplication.activity.admin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.BillAdapter;
import com.example.myapplication.adapter.UserAdapter;
import com.example.myapplication.dbhelper.BillDBHelper;
//import com.example.myapplication.dbhelper.UserDBHelper;
import com.example.myapplication.model.Bill;

import java.util.ArrayList;
import java.util.List;

public class BillHistoryActivity extends AppCompatActivity {


    ImageButton btnBack;

//    SwitchMaterial swViewAll;

    ListView listView;

    LinearLayout noMoreBills;
    @SuppressLint("MissingInflater")
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addControls();
        setContentView(R.layout.activity_bill_history);
    BillDBHelper billDBHelper= new BillDBHelper(this);
 List<Bill> bill=billDBHelper.getAllBills();
 BillAdapter adapter=new BillAdapter(this,R.layout.bill_item,bill);
listView=findViewById(R.id.listviewBill);
listView.setAdapter( adapter);

//        ButterKnife.bind(this);

//        btnBack.setOnClickListener(view -> finish());
////        swViewAll.setOnClickListener(view -> setBillSDisplay());
//        setBillSDisplay();
}

    private void addControls() {
        btnBack = findViewById(R.id.btnBack);
        listView = findViewById(R.id.listviewBill);
//        noMoreBills = findViewById(R.id.noMoreBills);
    }
}

//    private void setBillSDisplay() {
//        BillDBHelper billDbHelper = new BillDBHelper(this);
//        ArrayList<Bill> bills = new ArrayList<>();
//        if (swViewAll.isChecked())
//            bills = billDbHelper.getAllBills(user.getId());
//        else
//            bills = billDbHelper.getUnpaidBills(user.getId());
//        if (bills.size() > 0) {
//            BillAdapter adapter = new BillAdapter(bills, this);
//            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
//            rvBillHistory.setLayoutManager(layoutManager);
//            rvBillHistory.setAdapter(adapter);
//            rvBillHistory.setVisibility(View.VISIBLE);
//            noMoreBills.setVisibility(View.GONE);
//            for (Bill bill : bills) {
//                System.out.println(bill);
//            }
//        } else {
//            rvBillHistory.setVisibility(View.GONE);
//            noMoreBills.setVisibility(View.VISIBLE);
//        }
//    }
//}
