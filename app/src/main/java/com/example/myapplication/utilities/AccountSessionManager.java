package com.example.myapplication.utilities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.myapplication.activity.admin.DashboardActivity;
import com.example.myapplication.dbhelper.AccountDBHelper;
import com.example.myapplication.dbhelper.UserDBHelper;
import com.example.myapplication.model.Account;
import com.example.myapplication.model.User;


public class AccountSessionManager {
    private static Account currentUser;
    private static AccountDBHelper accountDbHelper;
    private static UserDBHelper userDbHelper;

    public static Account getCurrentUser() {
        return currentUser;
    }

    public static boolean checkLogin(Context context, String email) {
        accountDbHelper = new AccountDBHelper(context);
        currentUser = accountDbHelper.getAccountByEmail(email);

        if (currentUser != null) {
            userDbHelper = new UserDBHelper(context);
            User user = userDbHelper.getUserByAccountId(currentUser.getId());

            if (user != null) {
                if (currentUser.getRoleID() == 1) {
                    Intent intent = new Intent(context, DashboardActivity.class);
                    context.startActivity(intent);
                    Activity activity = (Activity) context;
                    Toast.makeText(context, "Đã đăng nhập với tên " + user.getFullname(), Toast.LENGTH_SHORT).show();
                    activity.finish();
                }
                Toast.makeText(context, "Đã đăng nhập với tên " + user.getFullname(), Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        return false;
    }

    public static void logout() {
        currentUser = null;
    }
}
