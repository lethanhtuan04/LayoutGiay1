package com.example.myapplication.dbhelper;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

        byte[] imageByteArray1 = cursor.getBlob(4); // img1
        byte[] imageByteArray2 = cursor.getBlob(5); // img2
        byte[] imageByteArray3 = cursor.getBlob(6); // img3
        byte[] imageByteArray4 = cursor.getBlob(7);//img4
        // Lấy dữ liệu từ trường "image"
        return new Product(
                cursor.getInt(0), // id
                cursor.getInt(1), // type
                cursor.getString(2), // name
                cursor.getDouble(3), // price
                imageByteArray1,//img1
                imageByteArray2,//img2
                imageByteArray3,//img3
                imageByteArray4,// img4
                cursor.getString(8), // detail
                cursor.getFloat(9), // star
                cursor.getString(10) // status
        );
    }

    private ContentValues createContentValues(Product product) {
        ContentValues values = new ContentValues();
        values.put("id", product.getId());
        values.put("type", product.getType());
        values.put("name", product.getName());
        values.put("price", product.getPrice());
        values.put("image1", product.getImage1());
        values.put("image2", product.getImage2());
        values.put("image3", product.getImage3());
        values.put("image4", product.getImage4());
        values.put("detail", product.getDetail());
        values.put("star", product.getStar());
        values.put("status", product.getStatus());
        return values;
    }

    public Product getProductByID(Integer id) {
        ArrayList<Product> products = getProductByField("id", id);
        if (products.size() > 0)
            return products.get(0);
        return null;
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
//    public Product getProductById(Integer id) {
//        ArrayList<Product> products = getProductByField(Product, id);
//        if (products.size() > 0)
//            return products.get(0);
//        return null;
//    }

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
                    cursor.getInt(11),
                    cursor.getInt(12),
                    cursor.getInt(13),
                    cursor.getString(14)
            );
            product.setDiscount(discount);
            products.add(product);
            cursor.moveToNext();
        }
        cursor.close();
        return products;
    }

    public long insert(Product product) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = createContentValues(product);
        return db.insert("Product", null, values);
    }

    public int update(Product product) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = createContentValues(product);
        return db.update("Product", values, "id" + " = ?", new String[]{String.valueOf(product.getId())});
    }

    public int delete(Product product) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete("Product", "id" + " = ?", new String[]{String.valueOf(product.getId())});
    }

}
