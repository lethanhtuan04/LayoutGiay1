    package com.example.myapplication.dbhelper;

    import android.content.ContentValues;
    import android.content.Context;
    import android.database.Cursor;
    import android.database.sqlite.SQLiteDatabase;
    import android.database.sqlite.SQLiteOpenHelper;

    import androidx.annotation.Nullable;

    import com.example.myapplication.model.Bill;
    import com.example.myapplication.model.Cart;
    import com.example.myapplication.model.Product;

    import org.jetbrains.annotations.NotNull;

    import java.util.ArrayList;

    public class BillDBHelper extends SQLiteOpenHelper {

        private final Context context;

        public BillDBHelper(@Nullable Context context) {
            super(context, DBHelper.DATABASE_NAME, null, DBHelper.DATABASE_VERSION);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }

    private Bill cursorToBill(Cursor cursor) {
        return new Bill(
                cursor.getInt(0),//id
                cursor.getInt(1),//userid
                cursor.getInt(2),//cartid
                cursor.getString(3),//phone
                cursor.getString(4),//address
                cursor.getInt(5),//price
                cursor.getString(6),//date
                cursor.getString(7)//status
        );
    }

    private ContentValues createContentValues(Bill bill) {
        ContentValues values = new ContentValues();
        values.put("id", bill.getId());
        values.put("userID", bill.getUserId());
        values.put("cartID", bill.getCartId());
        values.put("address", bill.getAddress());
        values.put("phone", bill.getPhone());
        values.put("date", bill.getDate());
        values.put("price", bill.getPrice());
        values.put("status", bill.getStatus());
        return values;
    }

        public long insert(Bill bill) {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = createContentValues(bill);
            return db.insert("Bill", null, values);
        }

    public int update(Bill bill) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = createContentValues(bill);
        return db.update("Bill", values, "id" + " = ?", new String[]{String.valueOf(bill.getId())});
    }
    public int updateCancelBillStatus(int billtId) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("status",Bill.BILL_CANCELED);
        return db.update("Bill", values, "id" + " = ?", new String[]{String.valueOf(billtId)});
    }

        public int delete(@NotNull Bill bill) {
            SQLiteDatabase db = getWritableDatabase();
            return db.delete("Bill", "id" + " = ?", new String[]{String.valueOf(bill.getId())});
        }

    public ArrayList<Bill> getAllBills(Integer userId) {
        ArrayList<Bill> bills = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM Bill INNER JOIN Cart ON Bill.cartID = Cart.id WHERE Bill.userID = ?",
                new String[]{String.valueOf(userId)});
        try {
            if (cursor.moveToFirst()) {
                do {
                    Bill bill = cursorToBill(cursor);
                    Cart cart = new Cart(
                            cursor.getInt(8),
                            cursor.getInt(9),
                            cursor.getInt(10),
                            cursor.getInt(11),
                            cursor.getString(12)
                    );
                    ProductDBHelper productDbHelper = new ProductDBHelper(this.context);
                    Product product = productDbHelper.getProductById(cart.getProductId());
                    cart.setProduct(product);
                    bill.setCart(cart);
                    bills.add(bill);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("getAllBills", "Error while fetching bills: " + e.getMessage());
        } finally {
            cursor.close();
        }
        Log.d("getAllBills", "Total bills fetched: " + bills.size());
        return bills;
    }

    public ArrayList<Bill> getAllBillsExceptReceived(Integer userId) {
        ArrayList<Bill> bills = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM Bill INNER JOIN Cart ON Bill.cartID = Cart.id WHERE Bill.userID = ? AND Bill.status != ?",
                new String[]{String.valueOf(userId), Bill.BILL_RECEIVED});
        try {
            if (cursor.moveToFirst()) {
                do {
                    Bill bill = cursorToBill(cursor);
                    Cart cart = new Cart(
                            cursor.getInt(8),
                            cursor.getInt(9),
                            cursor.getInt(10),
                            cursor.getInt(11),
                            cursor.getString(12)
                    );
                    ProductDBHelper productDbHelper = new ProductDBHelper(this.context);
                    Product product = productDbHelper.getProductById(cart.getProductId());
                    cart.setProduct(product);
                    bill.setCart(cart);
                    bills.add(bill);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("getAllBillsExceptReceived", "Error while fetching bills: " + e.getMessage());
        } finally {
            cursor.close();
        }
        Log.d("getAllBillsExceptReceived", "Total bills fetched: " + bills.size());
        return bills;
    }




    public ArrayList<Bill> getReceivedBills(Integer userId) {
        ArrayList<Bill> bills = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM Bill INNER JOIN Cart ON Bill.cartId = Cart.id WHERE Bill.userId = ? AND Bill.status = 'Received'",
                new String[]{String.valueOf(userId)});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Bill bill = cursorToBill(cursor);
            Cart cart = new Cart(
                    cursor.getInt(8),
                    cursor.getInt(9),
                    cursor.getInt(10),
                    cursor.getInt(11),
                    cursor.getString(12)
            );
            ProductDBHelper productDbHelper = new ProductDBHelper(this.context);
            Product product = productDbHelper.getProductById(cart.getProductId());
            cart.setProduct(product);
            bill.setCart(cart);
            bills.add(bill);
            cursor.moveToNext();
        }
        cursor.close();
        return bills;
    }

    //    public ArrayList<Bill> getAllUnpaidBills() {
    //        ArrayList<Bill> bills = new ArrayList<>();
    //        SQLiteDatabase db = getReadableDatabase();
    //        Cursor cursor = db.rawQuery(
    //                "SELECT * FROM Bill INNER JOIN Cart ON Bill.cartId = Cart.id WHERE  Bill.status = 'Unpaid'", null);
    //        cursor.moveToFirst();
    //        while (!cursor.isAfterLast()) {
    //            Bill bill = cursorToBill(cursor);
    //            Cart cart = new Cart(
    //                    cursor.getInt(9),
    //                    cursor.getInt(10),
    //                    cursor.getInt(11),
    //                    cursor.getInt(12),
    //                    cursor.getString(13)
    //            );
    //            ProductDbHelper productDbHelper = new ProductDbHelper(this.context);
    //            Product product = productDbHelper.getProductById(cart.getProductId());
    //            cart.setProduct(product);
    //            bill.setCart(cart);
    //            bills.add(bill);
    //            cursor.moveToNext();
    //        }
    //        cursor.close();
    //        return bills;
    //    }

    //    public ArrayList<StatisDTO> statis() {
    //        ArrayList<StatisDTO> statis = new ArrayList<>();
    //        SQLiteDatabase db = getReadableDatabase();
    //        Cursor cursor = null;
    //        cursor = db.rawQuery("SELECT SUM(Cart.quantity) as total_quantity, ProductType.name, ProductType.id " +
    //                        "FROM Bill INNER JOIN Cart INNER JOIN Product  INNER JOIN ProductType " +
    //                        "WHERE Bill.cartId = Cart.id AND Cart.productId = Product.id " +
    //                        "AND Product.type = ProductType.id AND Bill.status = 'Paid' GROUP BY ProductType.id",
    //                null);
    //        cursor.moveToFirst();
    //        while (!cursor.isAfterLast()) {
    //            StatisDTO statisDTO = new StatisDTO(
    //                    cursor.getInt(2),
    //                    cursor.getString(1),
    //                    cursor.getInt(0)
    //            );
    //            statis.add(statisDTO);
    //            cursor.moveToNext();
    //        }
    //        cursor.close();
    //        return statis;
    //    }

        public Bill getBillById(Integer id) {
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + "Bill" + " WHERE id = ?", new String[]{id.toString()});
            Bill bill = null;
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                bill = cursorToBill(cursor);
            }
            cursor.close();
            return bill;
        }

    }


