package com.example.myapplication.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.myapplication.model.Product;
import com.example.myapplication.model.ProductType;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ProductTypeDBHelper extends SQLiteOpenHelper {
    public ProductTypeDBHelper(@Nullable Context context) {
        super(context, DBHelper.DATABASE_NAME, null, DBHelper.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    private ProductType cursorToProductType(Cursor cursor) {
        return new ProductType(
                cursor.getInt(0), // id
                cursor.getString(1), // name
                cursor.getString(2) // status
        );
    }

    // lấy toàn bộ danh sách
    public ArrayList<ProductType> getAllProductsType() {
        ArrayList<ProductType> productTypes = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM ProductType", null);
        if (cursor != null) {
            try {
                while (cursor.moveToNext()) {
                    ProductType productType = new ProductType(
                            cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2)
                    );
                    productTypes.add(productType);
                }
            } finally {
                cursor.close();
            }
        }
        return productTypes;
    }

    @NotNull
    private ContentValues createContentValues(@NotNull ProductType productType) {
        ContentValues values = new ContentValues();
        values.put("id", productType.getId());
        values.put("name", productType.getName());
        values.put("status", productType.getStatus());
        return values;
    }

    public long insertProType(ProductType productType) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = createContentValues(productType);
        long result = db.insert("ProductType", null, values);
        return result;
    }

    public int updateProType(ProductType productType) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = createContentValues(productType);
        return db.update("ProductType", values, "id = ?", new String[]{String.valueOf(productType.getId())});
    }

    public int deleteProType(ProductType productType) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete("ProductType", "id = ?", new String[]{String.valueOf(productType.getId())});
    }

    public ProductType getProductTypeById(Integer id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM ProductType WHERE id = ?", new String[]{String.valueOf(id)});
        ProductType productType = null;
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            productType = cursorToProductType(cursor);
        }
        cursor.close();
        return productType;
    }

    public ProductType getProductTypeByID(Integer id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM ProductType WHERE id = ?", new String[]{String.valueOf(id)});
        ProductType productType = null;
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            productType = cursorToProductType(cursor);
        }
        cursor.close();
        return productType;
    }

}
