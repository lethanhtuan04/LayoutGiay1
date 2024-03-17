package com.example.myapplication.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.myapplication.model.Cart;
import com.example.myapplication.model.Product;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CartDBHelper extends SQLiteOpenHelper {


    private final Context context;

    public CartDBHelper(@Nullable Context context) {
        super(context, DBHelper.DATABASE_NAME, null, DBHelper.DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(@NotNull SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    @NotNull
    private ContentValues createContentValues(@NotNull Cart cart) {
        ContentValues values = new ContentValues();
        values.put("userId", cart.getUserId());
        values.put("productId", cart.getProductId());
        values.put("quantity", cart.getQuantity());
        values.put("status", cart.getStatus());
        return values;
    }

    public long insert(Cart cart) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = createContentValues(cart);
        long result = db.insert("Cart", null, values);
        if (result > 0) {
            ProductDBHelper productDbHelper = new ProductDBHelper(this.context);
            Product product = productDbHelper.getProductById(cart.getProductId());
            cart.setProduct(product);
        }
        return result;
    }

    public int update(Cart cart) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = createContentValues(cart);
        return db.update("Cart", values, "id" + " = ?", new String[]{String.valueOf(cart.getId())});
    }

    public int delete(Integer cartId) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete("Cart", "id" + " = ?", new String[]{String.valueOf(cartId)});
    }

    @NotNull
    private Cart cursorToCart(@NotNull Cursor cursor) {
        return new Cart(
                cursor.getInt(0),
                cursor.getInt(1),
                cursor.getInt(2),
                cursor.getInt(3),
                cursor.getString(4)
        );
    }


    private ArrayList<Cart> getCart(Cursor cursor) {
        ArrayList<Cart> carts = new ArrayList<>();
        cursor.moveToFirst();
        byte[] imageByteArray = cursor.getBlob(9);

        while (!cursor.isAfterLast()) {
            Cart cart = cursorToCart(cursor);
            Product product = new Product(

                    cursor.getInt(5), // id
                    cursor.getInt(6), // type
                    cursor.getString(7), // name
                    cursor.getDouble(8), // price
                    imageByteArray, // Truyền đối tượng Bitmap vào constructor của Product thay vì chuỗi
                    cursor.getString(10), // img1
                    cursor.getString(11), // img2
                    cursor.getString(12), // img3
                    cursor.getString(13), // img4
                    cursor.getString(14), // detail
                    cursor.getFloat(15), // star
                    cursor.getString(16) // status
            );
            cart.setProduct(product);
            carts.add(cart);
            cursor.moveToNext();
        }
        cursor.close();
        return carts;
    }

    public ArrayList<Cart> getAllCarts(Integer userId) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM Cart INNER JOIN Product ON Cart.productId = Product.id WHERE Cart.userId = ?",
                new String[]{userId.toString()});
        return getCart(cursor);
    }

    private ArrayList<Cart> getCartByStatus(Integer userId, String status) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM Cart INNER JOIN Product ON Cart.productId = Product.id WHERE Cart.userId = ? AND Cart.status LIKE ?",
                new String[]{userId.toString(), "%" + status + "%"});
        return getCart(cursor);
    }

    public ArrayList<Cart> getUnpaidCart(Integer userId) {
        return getCartByStatus(userId, Cart.CART_UNPAID);
    }

    public Integer getCartIdByRowId(long rowId) {
        return getCartByRowId(rowId).getId();
    }

    public Cart getCartByRowId(long rowID) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM Cart" + " WHERE rowid = ?",
                new String[]{String.valueOf(rowID)});
        Cart cart = null;
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            cart = cursorToCart(cursor);
        }
        cursor.close();
        return cart;
    }

    private double calculateSubtotal(ArrayList<Cart> carts) {
        double subtotal = 0;

        for (Cart cart : carts) {
            subtotal += cart.getProduct().getPrice() * cart.getQuantity();
        }

        return subtotal;
    }

    public double getTotalPhu(Integer userId) {
        ArrayList<Cart> carts = getAllCarts(userId);
        return calculateSubtotal(carts);
    }

}

