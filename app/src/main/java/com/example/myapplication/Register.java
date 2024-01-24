package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Register extends AppCompatActivity {
    TextView btnSignIn;
    ImageView ic_pass, ic_cfpass;
    EditText pass, cfpass, user;
    private boolean PassShowing = false;
    private boolean CFPassShowing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        addControls();
        addShowPass();
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void addShowPass() {
        ic_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PassShowing) {
                    PassShowing = false;
                    pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    ic_pass.setImageResource(R.drawable.showpass_gray);
                } else {
                    PassShowing = true;
                    pass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    ic_pass.setImageResource(R.drawable.hidenpass_gray);
                }
                pass.setSelection(pass.length());
            }
        });
        ic_cfpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CFPassShowing) {
                    CFPassShowing = false;
                    cfpass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    ic_cfpass.setImageResource(R.drawable.showpass_gray);
                } else {
                    CFPassShowing = true;
                    cfpass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    ic_cfpass.setImageResource(R.drawable.hidenpass_gray);
                }
                cfpass.setSelection(cfpass.length());
            }
        });
    }

    private void addControls() {
        btnSignIn = findViewById(R.id.btnSignUp);
        ic_cfpass = findViewById(R.id.passCFIC);
        ic_pass = findViewById(R.id.passIC);
        pass = findViewById(R.id.edtPass);
        cfpass = findViewById(R.id.edtCFPass);
    }

}