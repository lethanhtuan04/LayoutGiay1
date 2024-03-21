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
                cursor.getString(4),
                cursor.getBlob(5)

        );
    }

    public ArrayList<Notification> getAllNotifications(Integer accId) {
        ArrayList<Notification> notifications = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM Notification" + " WHERE accId = ? AND " + "status" + " = ?",
                new String[]{accId.toString(), Notification.NOTIFY_UNREAD});
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
        contentValues.put("accID", notification.getAccId());
        contentValues.put("type", notification.getType());
        contentValues.put("message", notification.getMessage());
        contentValues.put("status", notification.getStatus());
        contentValues.put("image", notification.getImage());

        return contentValues;
    }
    public int getNotificationCount(Integer accId) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT COUNT(*) FROM Notification WHERE accId = ? AND status = ?",
                new String[]{accId.toString(), Notification.NOTIFY_UNREAD});
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        return count;
    }


    public long insert(Notification notification) {
        SQLiteDatabase db = getReadableDatabase();
        ContentValues contentValues = createContentValues(notification);
        return db.insert("Notification", null, contentValues);
    }
}

