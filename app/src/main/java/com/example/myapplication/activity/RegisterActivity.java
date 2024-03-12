package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.dbhelper.AccountDBHelper;
import com.example.myapplication.fragment.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    TextView txtSignIn, mError;
    AppCompatButton btnSignUp;
    ImageView ic_pass, ic_cfpass, btnHome;
    EditText edtpass, edtcfpass, edtuser, edtemail;
    private boolean PassShowing = false;
    private boolean CFPassShowing = false;

    AccountDBHelper accountDBHelper = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        addControls();
        onStart();
        addShowPass();
        addSignUp();
        txtSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));

            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            currentUser.reload();
        }
    }


    private void addSignUp() {
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //moi them
//                final String getEdtUserTxt = edtuser.getText().toString();
//                final String getEdtEmailTxt = edtemail.getText().toString();

                //   Intent intent = new Intent(RegisterActivity.this, OtpActivity.class);
//                intent.putExtra("edtuser", getEdtUserTxt);
//                intent.putExtra("edtemail", getEdtEmailTxt);
//                startActivity(intent);//

                String user = edtuser.getText().toString().trim();
                String email = edtemail.getText().toString().trim();
                String pass = edtpass.getText().toString().trim();
                String cf_pass = edtcfpass.getText().toString().trim();
                accountDBHelper = new AccountDBHelper(RegisterActivity.this);

                //Phải nhập đầy đủ thông tin
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

                    //Xác nhận mật khẩu trùng nhau
                    if (cf_pass.equals(pass)) //so sánh chuỗi
                    {
                        registerAccount(user, email, pass);
                        mAuth.createUserWithEmailAndPassword(email, pass)
                                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            startActivity(new Intent(RegisterActivity.this, HomeFragment.class));
                                        } else {
                                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });


                    } else {

                        mError.setText("Password incorrect");
                        edtcfpass.setBackgroundResource(R.drawable.border_error_red);
                    }
                }
            }

        });
    }


    private void registerAccount(String username, String email, String password) {
        long result = accountDBHelper.registerUser(username, email, password);

        if (result != -1) {
            Toast.makeText(this, "Đăng ký tài khoản thành công", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Lỗi khi đăng ký tài khoản", Toast.LENGTH_SHORT).show();
        }
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
        btnHome = findViewById(R.id.btnSUtoHome);
        edtuser = findViewById(R.id.edtUserName);
        edtemail = findViewById(R.id.edtEmail);
        btnSignUp = findViewById(R.id.btnSignUp);

        mError = findViewById(R.id.mError);


    }

}