package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Login extends AppCompatActivity {
    private boolean passShowing = false;
    TextView btnSignUp;
    ImageView PassIC;
    EditText edtPass, edtUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        addControlos();
        addShowPass();
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });
    }

    private void addShowPass() {
        PassIC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //checking if password is not
                if (passShowing) {
                    passShowing = false;
                    edtPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    PassIC.setImageResource(R.drawable.showpass_gray);
                } else {
                    passShowing = true;
                    edtPass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    PassIC.setImageResource(R.drawable.hidenpass_gray);
                }
                //cho con trỏ về cuối dòng password
                edtPass.setSelection(edtPass.length());
            }
        });
    }

    private void addControlos() {
        btnSignUp = findViewById(R.id.btnSignUp);
        PassIC = findViewById(R.id.passIC);
        edtPass = findViewById(R.id.edtPassword);
    }
}