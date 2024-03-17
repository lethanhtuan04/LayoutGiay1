package com.example.myapplication.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.utilities.SessionManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;

public class ChangePasswordActivity extends AppCompatActivity {

    EditText edtOldPass, edtnewPass, edtcfnewPass;
    Button btnChange;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        addControls();
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();

            }
        });
    }

    private void changePassword() {
        sessionManager = new SessionManager(ChangePasswordActivity.this);
        HashMap<String, String> userDetails = sessionManager.getUserDetails();
        // Lấy email của người dùng
        String email = userDetails.get(SessionManager.KEY_EMAIL);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String oldPassword = edtOldPass.getText().toString().trim();
        String newPass = edtnewPass.getText().toString().trim();
        String cfnewPass = edtcfnewPass.getText().toString().trim();
        // Kiểm tra các trường đã được điền đầy đủ hay chưa
        if (oldPassword.isEmpty() || newPass.isEmpty() || cfnewPass.isEmpty()) {
            Toast.makeText(ChangePasswordActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra mật khẩu mới và mật khẩu mới xác nhận có trùng khớp nhau không
        if (newPass.equals(cfnewPass)) {
            AuthCredential credential = EmailAuthProvider.getCredential(email, oldPassword); // oldPassword là mật khẩu cũ nhập vào
            // Xác thực lại người dùng với mật khẩu cũ
            user.reauthenticate(credential)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                // Nếu xác thực thành công, tiến hành đổi mật khẩu
                                user.updatePassword(newPass)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    // Đổi mật khẩu thành công
                                                    Toast.makeText(ChangePasswordActivity.this, "Mật khẩu mới đã được cập nhật", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    // Đổi mật khẩu thất bại
                                                    Toast.makeText(ChangePasswordActivity.this, "Đã xảy ra lỗi khi cập nhật mật khẩu", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            } else {
                                // Xác thực thất bại, có thể do mật khẩu cũ không đúng
                                Toast.makeText(ChangePasswordActivity.this, "Xác thực thất bại. Mật khẩu cũ không đúng", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            Toast.makeText(ChangePasswordActivity.this, "Mật khẩu mới và xác nhận mật khẩu mới không khớp", Toast.LENGTH_SHORT).show();
        }
    }



    private void addControls() {
        edtOldPass = findViewById(R.id.edtoldPass);
        edtnewPass = findViewById(R.id.edtnewpass);
        edtcfnewPass = findViewById(R.id.edtcfnewPass);
        btnChange = findViewById(R.id.btnChange);
    }
}