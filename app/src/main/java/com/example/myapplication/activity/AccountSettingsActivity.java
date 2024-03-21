package com.example.myapplication.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.dbhelper.AccountDBHelper;
import com.example.myapplication.fragment.ProfileFragment;
import com.example.myapplication.model.Account;
import com.example.myapplication.utilities.SessionManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class AccountSettingsActivity extends AppCompatActivity {
    TextView btnintoChangePass;
    ImageView btnBack;
    ImageView avatar;
    private static final int PICK_IMAGE_REQUEST = 1;
    SessionManager sessionManager;
    TextView txtusername, txtuserName, txtemail;
    Account account;
    AccountDBHelper accountDBHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);
        addControls();

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                FragmentManager fragmentManager = getSupportFragmentManager();
//                ProfileFragment profileFragment = new ProfileFragment();
//                FragmentTransaction transaction = fragmentManager.beginTransaction();
//                transaction.replace(R.id.frame_container, profileFragment);
//                transaction.addToBackStack(null); // Để có thể nhấn nút Back để quay lại Fragment trước đó (nếu cần)
//                transaction.commit();
                finish();
                //startActivity(new Intent(AccountSettingsActivity.this, ProfileFragment.class));
            }
        });

        avatar = findViewById(R.id.avatar);
        txtemail = findViewById(R.id.txtemail);
        txtuserName = findViewById(R.id.txtuserName);
        txtusername = findViewById(R.id.txtusername);

        sessionManager = new SessionManager(this);
        HashMap<String, String> userDetails = sessionManager.getUserDetails();
        String email = userDetails.get(SessionManager.KEY_EMAIL);
        accountDBHelper = new AccountDBHelper(this);
        account = accountDBHelper.getAccountByEmail(email);
        if (avatar != null) {
            byte[] imageByteArray = account.getAvatar();
            if (imageByteArray != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);
                avatar.setImageBitmap(bitmap);
            } else {
                avatar.setImageResource(R.drawable.ic_houman_60);
            }
        }
        txtemail.setText(account.getEmail());
        txtusername.setText(account.getUsername());
        txtuserName.setText(account.getUsername());

        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImageFromPhone();
            }
        });

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

    private void chooseImageFromPhone() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        Uri uri = Uri.parse(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getPath());
        intent.setDataAndType(uri, "image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                // Convert Uri to Bitmap
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                avatar.setImageBitmap(bitmap);
                // Convert Bitmap to byte array
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                // Update account avatar in the database
                String email = sessionManager.getUserDetails().get(SessionManager.KEY_EMAIL);
                accountDBHelper.updateAvatar(email, byteArray);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void addControls() {
        btnintoChangePass = findViewById(R.id.intochangePass);
    }
}