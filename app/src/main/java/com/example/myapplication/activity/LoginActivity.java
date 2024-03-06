package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.activity.admin.DashboardActivity;
import com.example.myapplication.dbhelper.AccountDBHelper;
import com.example.myapplication.model.Account;

public class LoginActivity extends AppCompatActivity {
    private boolean passShowing = false;
    private boolean checkedHome = true;
    private boolean checkedFavorite = false;
    private boolean checkedCart = false;
    private boolean checkedProfile = false;
    TextView txtSignUp, viewError;
    AppCompatButton btnSignIn;
    ImageView PassIC,btnHome;
    EditText edtPass, edtemail;
    AccountDBHelper accountDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        addControlos();
        addShowPass();
//        addSignIn();
        setSignIn();
        //bắt đầu chạy trang register
        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }

        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = edtemail.getText().toString().trim();
                String pass = edtPass.getText().toString().trim();
                if (user.isEmpty()) {
                    viewError.setText("Please fill in all information completely");
                    edtemail.setBackgroundResource(R.drawable.border_error_red);
                } else {
                    edtemail.setBackgroundResource(R.drawable.round_black_dark);
                }
                if (pass.isEmpty()) {
                    viewError.setText("Please fill in all information completely");
                    edtPass.setBackgroundResource(R.drawable.border_error_red);
                } else {
                    edtPass.setBackgroundResource(R.drawable.round_black_dark);
                }

                //Viết hàm thực hiện chạy trang đăng nhập

                if (!user.isEmpty() && !pass.isEmpty()) {
                    btnSignIn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));

                        }
                    });
//                    setLogin();
                }
            }
        });


        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });

    }

    private void setSignIn() {
        accountDBHelper = new AccountDBHelper(LoginActivity.this);

    }


//    private void addSignIn() {
//        btnSignIn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String user = edtemail.getText().toString().trim();
//                String pass = edtPass.getText().toString().trim();
//                if (user.isEmpty()) {
//                    viewError.setText("Please fill in all information completely");
//                    edtemail.setBackgroundResource(R.drawable.border_error_red);
//                } else {
//                    edtemail.setBackgroundResource(R.drawable.round_black_dark);
//                }
//                if (pass.isEmpty()) {
//                    viewError.setText("Please fill in all information completely");
//                    edtPass.setBackgroundResource(R.drawable.border_error_red);
//                } else {
//                    edtPass.setBackgroundResource(R.drawable.round_black_dark);
//                }
//
//                //Viết hàm thực hiện chạy trang đăng nhập
//
//                if (!user.isEmpty() && !pass.isEmpty()) {
//                    btnSignIn.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            startActivity(new Intent(LoginActivity.this, HomeFragment.class));
//
//                        }
//                    });
////                    setLogin();
//                }
//            }
//        });
//    }


    private void setLogin() {
        String email = edtemail.getText().toString();
        String password = edtPass.getText().toString();
        if (email.equals("admin") && password.equals("123")) {
            Intent adminActivity = new Intent(this, DashboardActivity.class);
            startActivity(adminActivity);
            finish();
            return;
        }
        Account account = accountDBHelper.getAccountByEmail(email);
        if (email.equals(account.getEmail()) && password.equals(account.getPassword())) {
            Intent mainActivity = new Intent(this, MainActivity.class);
            startActivity(mainActivity);
            finish();
        } else {
            viewError.setText("Wrong account or password");
            edtemail.setText("");
            edtPass.setText("");
        }
//            finish();
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
        btnHome=findViewById(R.id.btnSItoHome);
        btnSignIn = findViewById(R.id.btnSignIn);
        PassIC = findViewById(R.id.passIC);
        edtPass = findViewById(R.id.edtPassword);
        edtemail = findViewById(R.id.edtUserName);
        txtSignUp = findViewById(R.id.txtSignUp);
        viewError = findViewById(R.id.mError);

    }
}