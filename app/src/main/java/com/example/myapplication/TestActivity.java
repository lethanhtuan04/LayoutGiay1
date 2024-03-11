package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TestActivity extends AppCompatActivity {
    private static final String TAG = "YourActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        // Gọi hàm gửi tin nhắn vào Firebase
        sendMessageToFirebase("Hello Firebase!");
    }

    private void sendMessageToFirebase(String message) {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("testNode");

        databaseRef.setValue(message)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Message sent successfully");
                        // Tin nhắn đã được gửi thành công, có nghĩa là kết nối đến Firebase đã thành công
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Failed to send message: " + e.getMessage());
                        // Đã xảy ra lỗi khi gửi tin nhắn, có thể là do kết nối đến Firebase không thành công
                    }
                });
    }
}