package com.example.myapplication.utilities;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;
    private static final String PREF_NAME = "SessionPref";
    private static final String IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_ROLEID = "roleid";
     public static final String KEY_USERNAME = "username";
     public static final String KEY_EMAIL = "email";

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void createLoginSession(String username, String email,int roleid) {
        editor.putBoolean(IS_LOGGED_IN, true);
        editor.putInt(KEY_ROLEID, roleid);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_EMAIL, email);
        editor.commit();
    }

//    public HashMap<String, String> getUserDetails() {
//        HashMap<String, String> user = new HashMap<>();
//        user.put(KEY_USERNAME, sharedPreferences.getString(KEY_USERNAME, null));
//        user.put(KEY_EMAIL, sharedPreferences.getString(KEY_EMAIL, null));
//        return user;
//    }

    public HashMap<String, String> getUserDetails() {
        // Khởi tạo một HashMap để lưu trữ thông tin người dùng
        HashMap<String, String> user = new HashMap<>();

        // Lấy thông tin người dùng từ SharedPreferences
        String username = sharedPreferences.getString(KEY_USERNAME, null);
        String email = sharedPreferences.getString(KEY_EMAIL, null);

        // Đặt thông tin người dùng vào HashMap
        user.put(KEY_USERNAME, username);
        user.put(KEY_EMAIL, email);
//        // Thêm roleid vào HashMap
//        // Giả sử KEY_ROLEID là "roleid" và giá trị là kiểu int đã lưu trong SharedPreferences
//        int roleid = sharedPreferences.getInt(KEY_ROLEID, 0); // Giá trị mặc định 0 nếu không có giá trị
//        user.put(KEY_ROLEID, String.valueOf(roleid)); // Chuyển đổi kiểu int thành String để đặt vào HashMap
        return user;
    }

    public void logoutUser() {
        editor.clear();
        editor.commit();
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(IS_LOGGED_IN, false);
    }
}
