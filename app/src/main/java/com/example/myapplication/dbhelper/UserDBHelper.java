package com.example.myapplication.dbhelper;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.myapplication.model.User;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;



import java.util.ArrayList;

public class UserDBHelper extends SQLiteOpenHelper {


    public UserDBHelper(@Nullable Context context) {
        super(context, DBHelper.DATABASE_NAME, null, DBHelper.DATABASE_VERSION);
    }

    @Override
    public void onCreate(@NotNull SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(@NotNull SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    @NotNull
    @Contract("_ -> new")
    private User cursorToUser(@NotNull Cursor cursor) {
        return new User(
                cursor.getInt(0),       //id
                cursor.getInt(1),          //accountID
                cursor.getString(2),        //fullname
                cursor.getString(3),        //sex
                cursor.getString(4),        //phone
                cursor.getString(5),        //address
                cursor.getString(6),        //avatar
                cursor.getString(7)         //status

        );
    }

    public User getUser(@NotNull Integer id) {
        User user = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM User"  + " WHERE id = ?", new String[]{id.toString()});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            user = cursorToUser(cursor);
        }
        cursor.close();
        return user;
    }

    @NotNull
    private ContentValues createContentValues(@NotNull User user) {
        ContentValues values = new ContentValues();
        values.put("fullname", user.getFullname());
        values.put("accountId", user.getAccountId());
        values.put("sex", user.getSex());
        values.put("phone", user.getPhone());
        values.put("address", user.getAddress());
        values.put("avatar", user.getAvatar());
        values.put("status", user.getStatus());
        return values;
    }

    public long insert(User user) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = createContentValues(user);
        return db.insert("User", null, values);
    }

    public int update(User user) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = createContentValues(user);
        return db.update("User", values, "id" + " = ?", new String[]{String.valueOf(user.getId())});
    }

    public int delete(@NotNull User user) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete("User", "id" + " = ?", new String[]{String.valueOf(user.getId())});
    }

    public User getUserByAccountId(Integer accountId) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM User"  + " WHERE accountId = ?",
                new String[]{String.valueOf(accountId)});
        User user = null;
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            user = cursorToUser(cursor);
        }
        cursor.close();
        return user;
    }

    public ArrayList<User> getAllUser() {
        ArrayList<User> users = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM User"  , null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            users.add(
                    cursorToUser(cursor)
            );
            cursor.moveToNext();
        }
        cursor.close();
        return users;
    }
}

