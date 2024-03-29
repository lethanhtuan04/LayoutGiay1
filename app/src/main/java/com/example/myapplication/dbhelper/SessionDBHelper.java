package com.example.myapplication.dbhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SessionDBHelper extends SQLiteOpenHelper {

    private static final String TABLE_SESSION = "session";
    private static final String KEY_ID = "id";

    private static final String KEY_USERNAME = "username";
    private static final String KEY_IDUSER = "iduser"; // Sửa từ int thành String
    private static final String KEY_EMAIL = "email";

    public SessionDBHelper(Context context) {
        super(context, DBHelper.DATABASE_NAME, null, DBHelper.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_SESSION + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"

                + KEY_USERNAME + " TEXT,"
                + KEY_IDUSER + " TEXT," // Sửa từ int thành String
                + KEY_EMAIL + " TEXT" + ")";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SESSION);
        onCreate(db);
    }
}
