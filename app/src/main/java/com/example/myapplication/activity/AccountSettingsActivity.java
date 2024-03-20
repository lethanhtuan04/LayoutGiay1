package com.example.myapplication.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.fragment.ProfileFragment;

public class AccountSettingsActivity extends AppCompatActivity {
    TextView btnintoChangePass;
    ImageView btnBack;
    ImageView avatar;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);
        addControls();

        btnBack = findViewById(R.id.btnBack);
        avatar = findViewById(R.id.avatar);

        btnintoChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountSettingsActivity.this, ProfileFragment.class));
            }
        });
        btnintoChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountSettingsActivity.this, ChangePasswordActivity.class));
            }
        });
    }

    private void addControls() {
        btnintoChangePass = findViewById(R.id.intochangePass);
    }
}