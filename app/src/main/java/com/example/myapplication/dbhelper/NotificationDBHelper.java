package com.example.myapplication.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.myapplication.model.Notification;

import java.util.ArrayList;

public class NotificationDBHelper extends SQLiteOpenHelper {

//        private static final String TABLE_NAME = "Notification";
//        private static final String NOTIFY_ID = "id";
//        private static final String NOTIFY_USER_ID = "userId";
//        private static final String NOTIFY_TYPE = "type";
//        private static final String NOTIFY_MESSAGE = "message";
//        private static final String NOTIFY_STATUS = "status";

    public NotificationDBHelper(@Nullable Context context) {
        super(context, DBHelper.DATABASE_NAME, null, DBHelper.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    private Notification cursorToNotification(Cursor cursor) {
        return new Notification(
                cursor.getInt(0),
                cursor.getInt(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4)
        );
    }

    public ArrayList<Notification> getAllNotifications(Integer userId) {
        ArrayList<Notification> notifications = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM Notification" + " WHERE userId = ? AND " + "status" + " = ?",
                new String[]{userId.toString(), Notification.NOTIFY_UNREAD});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            notifications.add(cursorToNotification(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        return notifications;
    }

    public int update(Notification notification) {
        SQLiteDatabase db = getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("status", notification.getStatus());
        return db.update("Notification", contentValues, "id = ?",
                new String[]{notification.getId().toString()});
    }

    private ContentValues createContentValues(Notification notification) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("userID", notification.getUserId());
        contentValues.put("type", notification.getType());
        contentValues.put("message", notification.getMessage());
        contentValues.put("status", notification.getStatus());
        return contentValues;
    }


    public long insert(Notification notification) {
        SQLiteDatabase db = getReadableDatabase();
        ContentValues contentValues = createContentValues(notification);
        return db.insert("Notification", null, contentValues);
    }
}

