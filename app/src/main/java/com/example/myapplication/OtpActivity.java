package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class OtpActivity extends AppCompatActivity {
    private EditText otpEt1,otpEt2,otpEt3,otpEt4;
    private TextView resendBtn;
    private  boolean resendEnabled =false;
    private  int resendTime =60;
    private int selectedETPosition =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        otpEt1= findViewById(R.id.otpET1);
        otpEt2= findViewById(R.id.otpET2);
        otpEt3=findViewById(R.id.otpET3);
        otpEt4=findViewById(R.id.otpET4);
        resendBtn=findViewById(R.id.resendBtn);
        final Button verifyBtn=findViewById(R.id.verifyBtn);
        final TextView otpEmail=findViewById(R.id.otpEmail);
        final  TextView otpUser=findViewById(R.id.otpUser);

        final  String getEmail=getIntent().getStringExtra("email");
        final String getUser=getIntent().getStringExtra("user");

        otpEmail.setText(getEmail);
        otpUser.setText(getUser);
        otpEt1.addTextChangedListener(textWatcher);
        otpEt2.addTextChangedListener(textWatcher);
        otpEt3.addTextChangedListener(textWatcher);
        otpEt4.addTextChangedListener(textWatcher);
        showKeyboard(otpEt1);

        startCountDownTimer();
        resendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(resendEnabled){
                    startCountDownTimer();
                }
            }
        });
        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final  String generateOtp=otpEt1.getText().toString()+otpEt2.getText().toString()+otpEt3.getText().toString()+otpEt4.getText().toString();
                if(generateOtp.length()==4){

                }

            }
        });

    }
    private  void showKeyboard(EditText otpET){
        otpET.requestFocus();
        InputMethodManager inputMethodManager =(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(otpET,InputMethodManager.SHOW_IMPLICIT);

    }
    private  void startCountDownTimer(){
        resendEnabled = false;
        resendBtn.setTextColor(Color.parseColor("#376DF4"));
        new CountDownTimer(resendTime * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                resendBtn.setText("Resend Code ("+(millisUntilFinished/1000)+")");

            }

            @Override
            public void onFinish() {
                resendEnabled=true;
                resendBtn.setText("Resend Code");
                resendBtn.setTextColor(getResources().getColor(R.color.main_blue));


            }
        }.start();
    }

    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if(s.length()>0){
                if(selectedETPosition==0){
                    selectedETPosition=1;
                    showKeyboard(otpEt2);
                }
                else if(selectedETPosition==1){
                    selectedETPosition=2;
                    showKeyboard(otpEt3);

                }
                else if(selectedETPosition==2){
                    selectedETPosition=3;
                    showKeyboard(otpEt4);

                }


            }

        }
    };
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_DEL){
            if(selectedETPosition==3){
                selectedETPosition=2;
                showKeyboard(otpEt3);
            }
            else if(selectedETPosition==2){
                selectedETPosition=1;
                showKeyboard(otpEt2);
            }
            else if(selectedETPosition==1){
                selectedETPosition=0;
                showKeyboard(otpEt1);
            }
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }
}
