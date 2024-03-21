package com.example.myapplication.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;
import com.example.myapplication.dbhelper.CartDBHelper;
import com.example.myapplication.model.Bill;
import com.example.myapplication.model.Cart;

import java.text.DecimalFormat;
import java.util.List;

public class ManagerBill extends ArrayAdapter<Bill> {
    ImageView productImage;
    TextView productName;
    TextView txtbillId;
    TextView txtaccId;
    TextView billTotalPrice;
    TextView billDeliveryAddress;
    TextView billStatus;
    TextView billTime, txtphoneBill;

    private Context mContext;
    private List<Bill> listBills;

    public ManagerBill(@NonNull Context context, List<Bill> orderList) {
        super(context, 0, orderList);
        mContext = context;
        listBills = orderList;
    }

    @SuppressLint("ResourceAsColor")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.bill_item, parent, false);
        }

        // Initialize views
        productImage = listItem.findViewById(R.id.billProductImage);
        productName = listItem.findViewById(R.id.billProductName);
        billTotalPrice = listItem.findViewById(R.id.billTotalPrice);
        billDeliveryAddress = listItem.findViewById(R.id.txtaddressBill);
        txtaccId = listItem.findViewById(R.id.txtaccId);
        billTime = listItem.findViewById(R.id.txttimeBill);
        txtbillId = listItem.findViewById(R.id.txtbillId);
        billStatus = listItem.findViewById(R.id.txtStatus);
        txtphoneBill = listItem.findViewById(R.id.txtphoneBill);

        // Get the current bill object
        Bill currentBill = listBills.get(position);
        CartDBHelper cartDBHelper = new CartDBHelper(getContext());
        Cart cart = cartDBHelper.getCartById(currentBill.getCartId());
        // Check for null values and set data accordingly

        byte[] img = cart.getProduct().getImage1();
        Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
        productImage.setImageBitmap(bitmap);
        productName.setText(cart.getProduct().getName());


        if (currentBill != null) {
            DecimalFormat decimalFormat = new DecimalFormat("#,###");
            String price = decimalFormat.format(currentBill.getPrice());

            billTotalPrice.setText(price + "đ");
            billDeliveryAddress.setText(currentBill.getAddress());
            txtaccId.setText(String.valueOf(currentBill.getAccId()) + "");
            billTime.setText(currentBill.getDate() + "");
            txtbillId.setText(String.valueOf(currentBill.getId()) + "");
            txtphoneBill.setText(currentBill.getPhone());
            String status = currentBill.getStatus();
            if (status.equals(Bill.BILL_WAIT)) {
                billStatus.setText(currentBill.getStatus());
                billStatus.setTextColor(R.color.black);
            } else if (status.equals(Bill.BILL_SHIPPING)) {
                billStatus.setText(currentBill.getStatus());
                billStatus.setTextColor(R.color.main_blue);
            } else if (status.equals(Bill.BILL_RECEIVED)) {
                billStatus.setText(currentBill.getStatus());
                billStatus.setTextColor(R.color.green);
            } else if (status.equals(Bill.BILL_CANCELED)) {
                billStatus.setText(currentBill.getStatus());
                billStatus.setTextColor(R.color.Red);
            }
        }
        return listItem;
    }
}
