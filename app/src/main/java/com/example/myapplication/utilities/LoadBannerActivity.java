package com.example.myapplication.utilities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.R;
import com.example.myapplication.fragment.HomeFragment;

public class LoadBannerActivity extends AppCompatActivity {
    ViewFlipper viewFlipper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewFlipper = findViewById(R.id.viewlipper);
        // Thêm Fragment vào FragmentManager
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, new HomeFragment());
        transaction.commit();
    }
}
