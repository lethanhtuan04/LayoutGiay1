package com.example.myapplication.dbhelper;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.Nullable;

import com.example.myapplication.model.Discount;
import com.example.myapplication.model.Product;

import java.util.ArrayList;

public class ProductDBHelper extends SQLiteOpenHelper {
    public ProductDBHelper(@Nullable Context context) {
        super(context, DBHelper.DATABASE_NAME, null, DBHelper.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private Product cursorToProduct(Cursor cursor) {
        byte[] imageByteArray = cursor.getBlob(4);
        // Lấy dữ liệu từ trường "image"
        Bitmap imageBitmap = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length); // Chuyển đổi mảng byte thành đối tượng Bitmap

        return new Product(
                cursor.getInt(0), // id
                cursor.getInt(1), // type
                cursor.getString(2), // name
                cursor.getDouble(3), // price
                imageByteArray, // Truyền đối tượng Bitmap vào constructor của Product thay vì chuỗi
                cursor.getString(5), // img1
                cursor.getString(6), // img2
                cursor.getString(7), // img3
                cursor.getString(8), // img4
                cursor.getString(9), // detail
                cursor.getFloat(10), // star
                cursor.getString(11) // status
        );
    }


    public ArrayList<Product> getAllProducts() {
        ArrayList<Product> products = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Product", null);
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    do {
                        Product product = cursorToProduct(cursor);

                        products.add(product);
                    } while (cursor.moveToNext());
                }
            } finally {
                cursor.close();
            }
        }
        return products;
    }

    public Product getProductById(Integer id) {
        ArrayList<Product> products = getProductByField("id", id);
        if (products.size() > 0)
            return products.get(0);
        return null;
    }

    private ArrayList<Product> getProductByField(String field, Object value) {
        ArrayList<Product> products = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        if (value instanceof String)
            cursor = getCursorWithStringValue(db, field, value.toString());
        else
            cursor = getCursorWithNumberValue(db, field, value.toString());

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Product product = cursorToProduct(cursor);
            products.add(product);
            cursor.moveToNext();
        }
        cursor.close();
        return products;
    }

    private Cursor getCursorWithStringValue(SQLiteDatabase db, String field, String value) {
        Cursor cursor = db.rawQuery("SELECT * FROM Product" + " WHERE " + field + " LIKE ?", new String[]{"%" + value + "%"});
        return cursor;
    }

    private Cursor getCursorWithNumberValue(SQLiteDatabase db, String field, String value) {
        return db.rawQuery("SELECT * FROM Product" + " WHERE " + field + " = ?", new String[]{value});
    }

    public ArrayList<Product> getDiscountProducts() {
        ArrayList<Product> products = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM Product INNER JOIN Discount ON Product.id = Discount.productId", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Product product = cursorToProduct(cursor);
            Discount discount = new Discount(
                    cursor.getInt(12),
                    cursor.getInt(13),
                    cursor.getInt(14),
                    cursor.getString(15)
            );
            product.setDiscount(discount);
            products.add(product);
            cursor.moveToNext();
        }
        cursor.close();
        return products;
    }
}
