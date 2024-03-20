package com.example.myapplication.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.dbhelper.BillDBHelper;
import com.example.myapplication.dbhelper.ProductDBHelper;
import com.example.myapplication.model.Bill;
import com.example.myapplication.model.Cart;
import com.example.myapplication.model.Product;

import java.util.ArrayList;
import java.util.List;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.ViewHolder> {
    private ArrayList<Bill> bills;
    private Context context;
    int layoutResource;
    private View view;

    public BillAdapter(Context context, ArrayList<Bill> objects) {
        super();
        this.context = context;
        this.bills = objects;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        this.view = inflater.inflate(R.layout.items_bill_status, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Bill bill = bills.get(position);
        Cart cart = bill.getCart();
        Integer productId = cart.getProductId();
        ProductDBHelper productDbHelper = new ProductDBHelper(context);
        Product product = productDbHelper.getProductById(productId);

//        Glide.with(context)
//                .load(product.getImage1())
//                .into(holder.productImage);

        holder.txtTenSP.setText(product.getName());
        holder.txtGia.setText(bill.getPrice() + "đ");

        byte[] imageByteArray = product.getImage1();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);
        holder.img.setImageBitmap(bitmap);


        String status = bill.getStatus();
        if (status.equals(Bill.BILL_WAIT)) {
            holder.txtStatus.setText("Đang chuẩn bị");
            holder.btnCancel.setVisibility(View.VISIBLE);
            holder.btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BillDBHelper billDBHelper = new BillDBHelper(context.getApplicationContext());
                    billDBHelper.updateCancelBillStatus(bill.getId());
                    // Update RecyclerView after canceling the bill
                    updateBillList(bills);
                }
            });
        } else if (status.equals(Bill.BILL_SHIPPING))
            holder.txtStatus.setText("Đang giao");
        else if (status.equals(Bill.BILL_RECEIVED))
            holder.txtStatus.setText("Đã nhận");
        else if (status.equals(Bill.BILL_CANCELED))
            holder.txtStatus.setText("Đã hủy");
    }

    public void updateBillList(List<Bill> updatedBillList) {
        this.bills.clear();
        this.bills.addAll(updatedBillList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return (bills == null) ? 0 : bills.size();
    }

    public ArrayList<Bill> getBills() {
        return bills;
    }

    public void setBills(ArrayList<Bill> bills) {
        this.bills = bills;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName;
        TextView productPrice;
        TextView billQuantity;
        TextView billTotalPrice;
        TextView billDeliveryAddress;
        TextView billStatus;
        TextView billTime;

        ImageView img;
        TextView txtTenSP, txtGia, txtStatus, btnCancel;


//        TextView price = row.findViewById(R.id.txtpriceBill);
//        cartid.setText(bills.get(position).getPrice().toString());

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            txtGia = itemView.findViewById(R.id.txtPrice);
            txtTenSP = itemView.findViewById(R.id.txtNameProBill);
            btnCancel = itemView.findViewById(R.id.bntHuyDon);


//                productImage = itemView.findViewById(R.id.billProductImage);
//                productName = itemView.findViewById(R.id.billProductName);
//                productPrice = itemView.findViewById(R.id.productPrice);
//                billQuantity = itemView.findViewById(R.id.billQuantity);
//                billTotalPrice = itemView.findViewById(R.id.billTotalPrice);
//                billDeliveryAddress = itemView.findViewById(R.id.billDeliveryAddress);
//                billStatus = itemView.findViewById(R.id.billStatus);
//                billTime = itemView.findViewById(R.id.billTime);
//                billDiscount = itemView.findViewById(R.id.billDiscount);
        }
    }
}

