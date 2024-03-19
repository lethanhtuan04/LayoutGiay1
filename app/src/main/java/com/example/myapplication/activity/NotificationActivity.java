package com.example.myapplication.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.NotificationAdapter;
import com.example.myapplication.dbhelper.NotificationDBHelper;
import com.example.myapplication.model.Notification;
import com.example.myapplication.utilities.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;

public class NotificationActivity extends AppCompatActivity {
    ImageView btnBack;
    RecyclerView rcNoti;
    SessionManager sessionManager;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        sessionManager = new SessionManager(this);
        btnBack = findViewById(R.id.btnBack_notification);
        rcNoti = findViewById(R.id.rvNotifications);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getUnreadNotifications();
    }

    @Override
    public void onResume() {
        super.onResume();
        getUnreadNotifications();
    }

    private void getUnreadNotifications() {
        ArrayList<Notification> notificationList = getAllNotifications();
        if (notificationList.size() > 0) {
            NotificationAdapter adapter = new NotificationAdapter(notificationList);
            RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
            rcNoti.setLayoutManager(manager);
            rcNoti.setAdapter(adapter);
            rcNoti.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
                @Override
                public void onChildViewAttachedToWindow(@NonNull View view) {
                }

                @Override
                public void onChildViewDetachedFromWindow(@NonNull View view) {
                    if (adapter.getItemCount() == 0) {
                        rcNoti.setVisibility(View.GONE);
                    }
                }
            });
        }
    }

    private ArrayList<Notification> getAllNotifications() {
        ArrayList<Notification> notifications = new ArrayList<>();

        if (sessionManager.getUserDetails() == null) return notifications;

        NotificationDBHelper notificationDbHelper = new NotificationDBHelper(this);
        HashMap<String, String> userDetails = sessionManager.getUserDetails();
        String id = userDetails.get(SessionManager.KEY_IDUSER);
        return notificationDbHelper.getAllNotifications(Integer.valueOf(id));
    }

}