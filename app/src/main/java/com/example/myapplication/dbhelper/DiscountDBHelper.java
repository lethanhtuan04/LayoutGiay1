package com.example.myapplication.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.myapplication.model.Discount;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class DiscountDBHelper extends SQLiteOpenHelper {
    public DiscountDBHelper(@Nullable Context context) {
        super(context, DBHelper.DATABASE_NAME, null, DBHelper.DATABASE_VERSION);
    }




    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    private Discount cursorToDiscount(Cursor cursor) {
        return new Discount(
                cursor.getInt(0),
                cursor.getInt(1),
                cursor.getInt(2),
                cursor.getString(3)
        );
    }

    public ArrayList<Discount> getAllDiscounts() {
        ArrayList<Discount> discounts = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Discount", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            discounts.add(
                    new Discount(
                            cursor.getInt(0),
                            cursor.getInt(1),
                            cursor.getInt(2),
                            cursor.getString(3)
                    )
            );
            cursor.moveToNext();
        }
        cursor.close();
        return discounts;
    }

    @NotNull
    private ContentValues createContentValues(@NotNull Discount discount) {
        ContentValues values = new ContentValues();
        values.put("id", discount.getId());
        values.put("productID", discount.getProductId());
        values.put("value", discount.getValue());
        values.put("status", discount.getStatus());
        return values;
    }

    public long insert(Discount discount) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = createContentValues(discount);
        long result = db.insert("Discount", null, values);
        return result;
    }

    public int update(Discount discount) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = createContentValues(discount);
        return db.update("Discount", values, "id = ?", new String[]{String.valueOf(discount.getId())});
    }

    public int delete(Discount discount) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete("Discount", "id = ?", new String[]{String.valueOf(discount.getId())});
    }



    public Discount getDiscountById(Integer id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Discount WHERE id = ?", new String[]{String.valueOf(id)});
        Discount discount = null;
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            discount = cursorToDiscount(cursor);
        }
        cursor.close();
        return discount;
    }

    public Discount getDiscountByProductID(Integer productId) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Discount WHERE productID = ?", new String[]{String.valueOf(productId)});
        Discount discount = null;
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            discount = cursorToDiscount(cursor);
        }
        cursor.close();
        return discount;
    }

}
