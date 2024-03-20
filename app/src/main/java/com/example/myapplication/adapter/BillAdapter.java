package com.example.myapplication.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.dbhelper.ProductDBHelper;
import com.example.myapplication.model.Bill;
import com.example.myapplication.model.Cart;
import com.example.myapplication.model.Product;

import java.util.ArrayList;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.ViewHolder> {
    private ArrayList<Bill> bills;
    private View view;
    private Context context;

public class BillAdapter extends ArrayAdapter<Bill> {
    Activity context;
    List<Bill> bills;
    int layoutResource;

    public BillAdapter(Activity context, int resource, List<Bill> objects) {
        super(context, resource, objects);
        this.context = context;
        this.bills = objects;
        this.layoutResource = resource;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        // this.view = inflater.inflate(R.layout.bill_item, parent, false);
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

        Glide.with(context)
                .load(product.getImage1())
                .into(holder.productImage);

        holder.productName.setText(product.getName());
        holder.productPrice.setText(product.getPrice().toString() + "đ");
        holder.billQuantity.setText(cart.getQuantity().toString());
      //  holder.billTotalPrice.setText(bill.getPrice().toString());
        holder.billDeliveryAddress.setText(bill.getAddress());
        holder.billTime.setText(bill.getDate());
        String status = bill.getStatus();
        if (status.equals(Bill.BILL_UNPAID))
            holder.billStatus.setText("Chưa thanh toán");
        else if (status.equals(Bill.BILL_PAID))
            holder.billStatus.setText("Đã thanh toán");
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
        TextView billDiscount;

        TextView billid = row.findViewById(R.id.txtbillId);
        billid.setText(bills.get(position).getId().toString());
        TextView cartid = row.findViewById(R.id.txtcartId);
        cartid.setText(bills.get(position).getCartId().toString());
        TextView userid = row.findViewById(R.id.txtuserId);
        cartid.setText(bills.get(position).getUserId().toString());
        TextView phone = row.findViewById(R.id.txtphoneBill);
        cartid.setText(bills.get(position).getPhone().toString());
        TextView address = row.findViewById(R.id.txtaddressBill);
        cartid.setText(bills.get(position).getAddress().toString());
//        TextView price = row.findViewById(R.id.txtpriceBill);
//        cartid.setText(bills.get(position).getPrice().toString());

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

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
//        @NonNull
//        public View View (int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//            LayoutInflater layoutInflater = this.context.getLayoutInflater();
//            View row = layoutInflater.inflate(this.layoutResource, null);
//
//            TextView userId = row.findViewById(R.id.txt_iduser);
//            userId.setText(users.get(position).getId().toString());
//            TextView userName = row.findViewById(R.id.txt_nameuser);
//            userName.setText(users.get(position).getFullname());
//            return row;
//        }
}

