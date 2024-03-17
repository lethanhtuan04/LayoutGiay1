package com.example.myapplication.dbhelper;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.Nullable;

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

//    public ArrayList<Product> getDiscountProductByName(String name, String discountValue) {
//        ArrayList<Product> products = new ArrayList<>();
//        SQLiteDatabase db = getReadableDatabase();
//        Cursor cursor = null;
//        if (!discountValue.equals("-1")) {
//            cursor = db.rawQuery(
//                    "SELECT * " +
//                            "FROM Product INNER JOIN Discount ON Product.id = Discount.productId " +
//                            "WHERE Discount.status = 'OK' AND Discount.value = '" + discountValue +"' AND Product.name LIKE ?",
//                    new String[]{"%" + name + "%"});
//        }
//        else {
//            cursor = db.rawQuery(
//                    "SELECT * " +
//                            "FROM Product INNER JOIN Discount ON Product.id = Discount.productId " +
//                            "WHERE Discount.status = 'OK' AND Discount.value > '" + discountValue + "' AND Product.name LIKE ?",
//                    new String[]{"%" + name + "%"});
//        }
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            Product product = cursorToProduct(cursor);
//            Discount discount = new Discount(
//                    cursor.getInt(9),
//                    cursor.getInt(10),
//                    cursor.getFloat(11),
//                    cursor.getString(12)
//            );
//            product.setDiscount(discount);
//            product.addProductImage(productImageDbHelper.getAllImageByProduct(product.getId()));
//            products.add(product);
//            cursor.moveToNext();
//        }
//        cursor.close();
//        return products;
//    }

    // Thêm một sản phẩm mới vào cơ sở dữ liệu với hình ảnh dưới dạng BLOB
//    public void addProductWithImage(Product product) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_NAME, product.getName());
//        values.put(COLUMN_IMAGE, convertImageToByteArray(product.getImage()));
//        values.put(COLUMN_PRICE, product.getPrice());
//        db.insert(TABLE_PRODUCTS, null, values);
//        db.close();
//    }
    // Chuyển đổi mảng byte (BLOB) từ cơ sở dữ liệu thành hình ảnh Bitmap


}
