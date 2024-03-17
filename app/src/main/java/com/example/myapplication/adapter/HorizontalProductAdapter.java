package com.example.myapplication.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.activity.DetailProActivity;
import com.example.myapplication.dbhelper.DiscountDBHelper;
import com.example.myapplication.model.Discount;
import com.example.myapplication.model.Product;

import java.text.DecimalFormat;
import java.util.List;

public class HorizontalProductAdapter extends RecyclerView.Adapter<HorizontalProductAdapter.ViewHolder> {


    List<Product> productList;
    Context context;
    DiscountDBHelper discountDbHelper;
    public HorizontalProductAdapter(List<Product> productList, Context context) {
        this.productList = productList;
        this.context = context;
        discountDbHelper = new DiscountDBHelper(context);

    }


    @NonNull
    @Override
    public HorizontalProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_hori_pro_home, parent, false);
        return new HorizontalProductAdapter.ViewHolder(view);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull HorizontalProductAdapter.ViewHolder holder, int position) {
        Product product = productList.get(position);

        if (product.isDiscounted()) {
            holder.bind(product);
            new GetDiscountTask(holder, product).execute(product.getId());
            // Hiển thị hình ảnh từ mảng byte (BLOB)
            byte[] imageByteArray = product.getImage1();
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);
            holder.hinh.setImageBitmap(bitmap);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailProActivity.class);
                    intent.putExtra("product", product);
                    context.startActivity(intent);
                }
            });
        } else {
            // Nếu sản phẩm không có giảm giá, ẩn item đi
            holder.itemView.setVisibility(View.GONE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
        }
    }


    private class GetDiscountTask extends AsyncTask<Integer, Void, Discount> {
        private HorizontalProductAdapter.ViewHolder viewHolder;
        private Product product;

        public GetDiscountTask(HorizontalProductAdapter.ViewHolder viewHolder, Product product) {
            this.viewHolder = viewHolder;
            this.product = product;
        }

        @Override
        protected Discount doInBackground(Integer... productIds) {
            // Thực hiện truy vấn cơ sở dữ liệu ở đây
            int productId = productIds[0];
            return discountDbHelper.getDiscountByProductID(productId);
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(Discount discount) {
            DecimalFormat decimalFormat = new DecimalFormat("#,###");

            if (discount != null) {
                double priceDiscount = product.getPrice() - product.getPrice() * discount.getValue() / 100;
                String formattedPrice = decimalFormat.format(priceDiscount);
                viewHolder.txtGiaKhiDiscount.setText(formattedPrice + "đ");
                viewHolder.updateDiscount(discount);
            } else {

                String Price = decimalFormat.format(product.getPrice());
                viewHolder.txtGiaKhiDiscount.setText(String.valueOf(product.getPrice()));
                viewHolder.lbDiscount.setVisibility(View.GONE);
                viewHolder.txtGiaKhiDiscount.setVisibility(View.GONE);
                viewHolder.txtGiaSP.setVisibility(View.VISIBLE);
                viewHolder.txtGiaSP.setText(String.valueOf(Price) + "đ");
                viewHolder.hasDiscount = false;
            }
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView hinh;
        Product product;
        public TextView  txtGiaSP, lbDiscount, txtGiaKhiDiscount;
        boolean hasDiscount = false; // Biến kiểm tra xem có giảm giá hay không
        double originalPrice; // Giá gốc của sản phẩm

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtGiaKhiDiscount = itemView.findViewById(R.id.txtGiakhiDiscount);
            lbDiscount = itemView.findViewById(R.id.lbDiscount);
            txtGiaSP = itemView.findViewById(R.id.txtGiaSP);
            hinh = itemView.findViewById(R.id.hinhSP);
        }

        public void updateDiscount(Discount discount) {
            DecimalFormat decimalFormat = new DecimalFormat("#,###");

            // Kiểm tra xem product có null không
            if (product != null) {
                if (discount != null && discount.getId() != null) {
                    lbDiscount.setVisibility(View.VISIBLE);
                    lbDiscount.setText(discount.getValue() + "%");
                    txtGiaSP.setVisibility(View.VISIBLE);
                    double priceDiscount = product.getPrice() - (product.getPrice() * ((double) discount.getValue()) / 100);
                    String formattedPrice = decimalFormat.format(priceDiscount);
                    String Price = decimalFormat.format(originalPrice);
                    txtGiaKhiDiscount.setVisibility(View.VISIBLE);
                    txtGiaKhiDiscount.setText(String.valueOf(Price) + "đ");
                    txtGiaSP.setText(String.valueOf(formattedPrice) + "đ");
                    hasDiscount = true; // Đánh dấu là có giảm giá
                } else {
                    lbDiscount.setVisibility(View.GONE);
                    txtGiaKhiDiscount.setVisibility(View.GONE);
                    txtGiaSP.setVisibility(View.VISIBLE);
                    String Price = decimalFormat.format(originalPrice);
                    txtGiaSP.setText(String.valueOf(Price) + "đ");
                    hasDiscount = false; // Đánh dấu là không có giảm giá
                }
            }
        }

        public void bind(Product product) {
            this.product = product; // Gán product từ onBindViewHolder
            this.originalPrice = product.getPrice(); // Lưu giá gốc của sản phẩm
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}


