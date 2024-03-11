package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.activity.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ForgotPassActivity extends AppCompatActivity {
    Button btn_forgot;
    FirebaseAuth auth;
    EditText edt_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);
        auth= FirebaseAuth.getInstance();
        edt_email= findViewById(R.id.edt_email);
        btn_forgot = findViewById(R.id.btn_forgot);
        btn_forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

            myRef.setValue("Hello, World!");
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Toast.makeText(getBaseContext(),"Value is:"  , Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getBaseContext(),"Forget to read value"  , Toast.LENGTH_LONG).show();
            }
        });

    }

    private void validateData() {
        String email = edt_email.getText().toString();
        if(email.isEmpty()){
            edt_email.setError("Required");
        }else{
            forgetPass(email);
        }
    }

    private void forgetPass(String email) {
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(ForgotPassActivity.this, "check your email", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ForgotPassActivity.this, LoginActivity.class));
                    finish();
                }else{
                    Toast.makeText(ForgotPassActivity.this, "Error "+task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}