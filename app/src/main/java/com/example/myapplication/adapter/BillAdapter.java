package com.example.myapplication.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.ViewHolder> {
    private ArrayList<Bill> bills;
    private View view;
    private Context context;

    public BillAdapter(ArrayList<Bill> bills, Context context) {
        this.bills = bills;
        this.context = context;
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

        byte[] imageByteArray = product.getImage1();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);
        holder.img.setImageBitmap(bitmap);
        holder.txttenSP.setText(product.getName());
        holder.productPrice.setText(bill.getPrice() + "đ");
        //   holder.billQuantity.setText(cart.getQuantity().toString());
        //  holder.billTotalPrice.setText(bill.getPrice().toString());
        //    holder.billDeliveryAddress.setText(bill.getAddress());
        //    holder.billTime.setText(bill.getDate());
        String status = bill.getStatus();
        if (status.equals(Bill.BILL_WAIT)) {
            holder.txtStatus.setText("Đang chuẩn bị");
            holder.btnHuyDon.setVisibility(View.VISIBLE);
            holder.btnHuyDon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Xác nhận hủy đơn");
                    builder.setMessage("Bạn có chắc chắn muốn hủy đơn này?");
                    builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Nếu người dùng chọn "Có", thực hiện hủy đơn
                            BillDBHelper billDBHelper = new BillDBHelper(context.getApplicationContext());
                            billDBHelper.updateCancelBillStatus(bill.getId());
                            // Cập nhật trạng thái của hóa đơn thành "hủy" trong danh sách và cập nhật giao diện
                            updateBillStatus(position, Bill.BILL_CANCELED);
                        }
                    });
                    builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Nếu người dùng chọn "Không", đóng hộp thoại
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });
        } else if (status.equals(Bill.BILL_SHIPPING))
            holder.txtStatus.setText("Đang giao");
        else if (status.equals(Bill.BILL_RECEIVED))
            holder.txtStatus.setText("Đã nhận");
        else if (status.equals(Bill.BILL_CANCELED))
            holder.txtStatus.setText("Đã hủy");
    }

    public void updateBillStatus(int position, String newStatus) {
        Bill bill = bills.get(position);
        bill.setStatus(newStatus);
        notifyDataSetChanged(); // Thông báo cho RecyclerView cập nhật lại giao diện
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
        //        ImageView productImage;
//        TextView productName;
//        TextView productPrice;
//        TextView billQuantity;
//        TextView billTotalPrice;
//        TextView billDeliveryAddress;
//        TextView billStatus;
//        TextView billTime;
        //  TextView billDiscount;
        ImageView img;
        TextView txttenSP, txtStatus, btnHuyDon, productPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            txttenSP = itemView.findViewById(R.id.txtNameProBill);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            btnHuyDon = itemView.findViewById(R.id.bntHuyDon);
            productPrice = itemView.findViewById(R.id.txtPrice);


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

