package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.dbhelper.NotificationDBHelper;

public class TestActivity extends AppCompatActivity {
    Button btnTest;
    NotificationDBHelper notificationDBHelper;

    @SuppressLint("MissingInflatedId")
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        btnTest = findViewById(R.id.btnTest);
        notificationDBHelper = new NotificationDBHelper(this);

        // Gọi phương thức để tạo kênh thông báo
        createNotificationChannel();

        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPurchaseSuccessNotification();
            }
        });
    }


    private void showPurchaseSuccessNotification() {
        // Gọi hàm hiển thị thông báo từ lớp NotificationHelper
        NotificationDBHelper.showNotification(this, "Mua hàng thành công", "Cảm ơn bạn đã mua hàng!");
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.title);
            String description = getString(R.string.content);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("Your_Channel_ID", name, importance);
            channel.setDescription(description);
            // Đăng ký kênh với hệ thống
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
