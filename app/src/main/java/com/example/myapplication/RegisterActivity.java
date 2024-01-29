package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {
    TextView txtSignIn, mError;
    AppCompatButton btnSignUp;
    ImageView ic_pass, ic_cfpass;
    EditText edtpass, edtcfpass, edtuser, edtemail;
    private boolean PassShowing = false;
    private boolean CFPassShowing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        addControls();
        addShowPass();
        addSignUp();
        txtSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });
    }

    private void addSignUp() {
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = edtuser .getText().toString().trim();
                String email = edtemail.getText().toString().trim();
                String pass = edtpass.getText().toString().trim();
                String cf_pass = edtcfpass.getText().toString().trim();

                if (user.isEmpty()) {
                    mError.setText("Please fill in all information completely");
                    edtuser.setBackgroundResource(R.drawable.border_error_red);
                } else {
                    edtuser.setBackgroundResource(R.drawable.round_black_dark);
                }

                if (email.isEmpty()) {
                    mError.setText("Please fill in all information completely");
                    edtemail.setBackgroundResource(R.drawable.border_error_red);
                } else {
                    edtemail.setBackgroundResource(R.drawable.round_black_dark);
                }

                if (pass.isEmpty()) {
                    mError.setText("Please fill in all information completely");
                    edtpass.setBackgroundResource(R.drawable.border_error_red);
                } else {
                    edtpass.setBackgroundResource(R.drawable.round_black_dark);
                }
                if (cf_pass.isEmpty()) {
                    mError.setText("Please fill in all information completely");
                    edtcfpass.setBackgroundResource(R.drawable.border_error_red);
                } else {
                    edtcfpass.setBackgroundResource(R.drawable.round_black_dark);
                }
            }

        });
    }

    private void addShowPass() {
        ic_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PassShowing) {
                    PassShowing = false;
                    edtpass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    ic_pass.setImageResource(R.drawable.showpass_gray);
                } else {
                    PassShowing = true;
                    edtpass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    ic_pass.setImageResource(R.drawable.hidenpass_gray);
                }
                edtpass.setSelection(edtpass.length());
            }
        });
        ic_cfpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CFPassShowing) {
                    CFPassShowing = false;
                    edtcfpass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    ic_cfpass.setImageResource(R.drawable.showpass_gray);
                } else {
                    CFPassShowing = true;
                    edtcfpass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    ic_cfpass.setImageResource(R.drawable.hidenpass_gray);
                }
                edtcfpass.setSelection(edtcfpass.length());
            }
        });

    }


    private void addControls() {
        txtSignIn = findViewById(R.id.txtSignIn);

        ic_cfpass = findViewById(R.id.passCFIC);
        ic_pass = findViewById(R.id.passIC);

        edtpass = findViewById(R.id.edtPass);
        edtcfpass = findViewById(R.id.edtCFPass);

        edtuser = findViewById(R.id.edtUsername);
        edtemail = findViewById(R.id.edtEmail);
        btnSignUp=findViewById(R.id.btnSignUp);

        mError = findViewById(R.id.mError);


    }

}