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
                cursor.getString(2),//pass
                cursor.getInt(3),//roleid
                cursor.getString(4),//email
                cursor.getString(5)
        );
    }

    public long registerUser(String username, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("email", email);
        values.put("password", password);

        long newRowId = db.insert("Account", null, values);
        db.close();
        return newRowId;
    }

//    public Account getAccount(@NotNull Integer id) {
//        Account account = null;
//        SQLiteDatabase db = getReadableDatabase();
//        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE id = ?", new String[]{id.toString()});
//        if (cursor.getCount() > 0) {
//            cursor.moveToFirst();
//            account = cursorToAccount(cursor);
//        }
//        cursor.close();
//        return account;
//    }

//    public Account login(String email, String password) {
//        Account account = null;
//        SQLiteDatabase db = getReadableDatabase();
//        Cursor cursor = db.rawQuery(
//                "SELECT * FROM Account" + " WHERE email = ? AND password = ?",
//                new String[]{email, password});
//        if (cursor.getCount() > 0) {
//            cursor.moveToFirst();
//            account = cursorToAccount(cursor);
//        }
//        cursor.close();
//        return account;
//    }
//
//    public Account getAccountByRowId(long rowID) {
//        SQLiteDatabase db = getReadableDatabase();
//        Cursor cursor = db.rawQuery(
//                "SELECT * FROM Account" + " WHERE rowid = ?",
//                new String[]{String.valueOf(rowID)});
//        Account account = null;
//        if (cursor.getCount() > 0) {
//            cursor.moveToFirst();
//            account = cursorToAccount(cursor);
//        }
//        cursor.close();
//        return account;
//    }

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


}


