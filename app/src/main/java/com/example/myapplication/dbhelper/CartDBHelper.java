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
        values.put("accId", cart.getAccId());
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

    public int updateOrderStatus(int cartId) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("status", "Ordered");
        return db.update("Cart", values, "id" + " = ?", new String[]{String.valueOf(cartId)});
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
        while (!cursor.isAfterLast()) {
            Cart cart = cursorToCart(cursor);

            byte[] imageByteArray1 = cursor.getBlob(9); // img1
            byte[] imageByteArray2 = cursor.getBlob(10); // img2
            byte[] imageByteArray3 = cursor.getBlob(11); // img3
            byte[] imageByteArray4 = cursor.getBlob(12);//img4
            // Lấy dữ liệu từ trường "image"
            Product product = new Product(
                    cursor.getInt(5), // id
                    cursor.getInt(6), // type
                    cursor.getString(7), // name
                    cursor.getDouble(8), // price
                    imageByteArray1,//img1
                    imageByteArray2,//img2
                    imageByteArray3,//img3
                    imageByteArray4,// img4
                    cursor.getString(13), // detail
                    cursor.getFloat(14), // star
                    cursor.getString(15) // status
            );
            cart.setProduct(product);
            carts.add(cart);
            cursor.moveToNext();
        }
        cursor.close();
        return carts;
    }

    public ArrayList<Cart> getAllCarts(Integer accId) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM Cart INNER JOIN Product ON Cart.productId = Product.id WHERE Cart.accId = ?",
                new String[]{accId.toString()});
        return getCart(cursor);
    }

    public ArrayList<Cart> getAllCartByStatus(Integer accId, String status) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM Cart INNER JOIN Product ON Cart.productId = Product.id WHERE Cart.accId = ? AND Cart.status LIKE ?",
                new String[]{accId.toString(), "%" + status + "%"});
        return getCart(cursor);
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

    public double getTotalPhu(Integer accId) {
        ArrayList<Cart> carts = getAllCartByStatus(accId, "wait");
        return calculateSubtotal(carts);
    }


}


