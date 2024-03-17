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
import com.example.myapplication.activity.admin.DashboardActivity;
//import com.example.myapplication.dbhelper.AccountDBHelper;
import com.example.myapplication.dbhelper.AccountDBHelper;
import com.example.myapplication.model.Account;
import com.example.myapplication.utilities.SessionManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    SessionManager sessionManager;
    private FirebaseAuth mAuth;

    boolean passShowing = false;
    TextView txtSignUp, viewError;
    AppCompatButton btnSignIn;
    ImageView PassIC, btnHome;
    EditText edtPass, edtemail;
    TextView fogotpass;
    AccountDBHelper accountDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        addControlos();
        addShowPass();
        AccountDBHelper AccountDBHelper = new AccountDBHelper(LoginActivity.this);
        addSignIn();
        //bắt đầu chạy trang register
        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }

        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });
        fogotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgotPassActivity.class));
            }
        });
    }


    private void addSignIn() {
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
                    setLogin();
                }
            }
        });
    }


    private void setLogin() {
        sessionManager = new SessionManager(getApplicationContext());
        String email = edtemail.getText().toString().trim();
        String password = edtPass.getText().toString().trim();
        if (email.equals("admin") && password.equals("admin")) {
            Intent adminActivity = new Intent(this, DashboardActivity.class);
            startActivity(adminActivity);
            return;
        }
        //    Account account = accountDBHelper.getAccountByEmail(email);
        //    if (account != null && email.equals(account.getEmail()) && password.equals(account.getPassword())) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            if (user != null) {
                                String userEmail = user.getEmail();
                          //      Toast.makeText(LoginActivity.this, userEmail, Toast.LENGTH_LONG).show();

                                Account account = accountDBHelper.getAccountByEmail(userEmail);
                              //  Toast.makeText(LoginActivity.this, account.getUsername(), Toast.LENGTH_LONG).show();

                                sessionManager.createLoginSession(account.getUsername(),account.getId(), email);
                                Intent mainActivity = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(mainActivity);
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            viewError.setText("Wrong account or password");
                            edtemail.setText("");
                            edtPass.setText("");
                        }
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
        btnHome = findViewById(R.id.btnSItoHome);

        btnSignIn = findViewById(R.id.btnSignIn);

        PassIC = findViewById(R.id.passIC);
        edtPass = findViewById(R.id.edtPassword);
        edtemail = findViewById(R.id.edtUserName);
        txtSignUp = findViewById(R.id.txtSignUp);
        viewError = findViewById(R.id.mError);
        fogotpass = findViewById(R.id.openforgotpass);
    }
}