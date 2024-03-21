package com.example.myapplication.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.myapplication.model.Account;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class AccountDBHelper extends SQLiteOpenHelper {
    public AccountDBHelper(@Nullable Context context) {
        super(context, DBHelper.DATABASE_NAME, null, DBHelper.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    @NotNull
    @Contract("_ -> new")
    private Account cursorToAccount(@NotNull Cursor cursor) {
        return new Account(
                cursor.getInt(0), //id
                cursor.getString(1),//username
                cursor.getInt(2),//roleid
                cursor.getString(3),//email
                cursor.getBlob(4),//avatar
                cursor.getString(5)
        );
    }

    public long registerUser(String username, String email, String password) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("email", email);
        values.put("roleID", "2");
        long newRowId = db.insert("Account", null, values);
        db.close();
        return newRowId;
    }

    public Account getAccountByEmail(String email) {
        Account account = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM Account" + " WHERE email = ?", new String[]{email});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            account = cursorToAccount(cursor);
        }
        cursor.close();
        return account;
    }
    public int updateAvatar(String email, byte[] newAvatar) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("avatar", newAvatar);
        // Điều kiện để cập nhật dòng dữ liệu cụ thể
        String selection = "email = ?";
        String[] selectionArgs = {email};
        // Thực hiện cập nhật và trả về số dòng được ảnh hưởng
        int count = db.update("Account", values, selection, selectionArgs);
        db.close();
        return count;
    }
}


