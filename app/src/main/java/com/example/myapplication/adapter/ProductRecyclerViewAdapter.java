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

import java.util.List;

public class ProductRecyclerViewAdapter extends RecyclerView.Adapter<ProductRecyclerViewAdapter.ViewHolder> {
    List<Product> productList;
    Context context;
    DiscountDBHelper discountDbHelper;

    public ProductRecyclerViewAdapter(List<Product> productList, Context context) {
        this.productList = productList;
        this.context = context;
        discountDbHelper = new DiscountDBHelper(context);

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_product_home, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = productList.get(position);
        Discount discount = null;
        // Sử dụng AsyncTask để lấy discount
        new GetDiscountTask(holder).execute(product.getId());
        // Các phần còn lại của onBindViewHolder()
        holder.txtTenSp.setText(product.getName());
        if (discount != null && discount.getValue() > 0) {
            holder.txtGiaKhiDiscount.setText(String.valueOf(product.getPrice()));
            holder.txtGiaSP.setText(String.valueOf(product.afterDiscount()));

        } else {
            holder.txtGiaKhiDiscount.setVisibility(View.GONE);
            holder.txtGiaSP.setText(String.valueOf(product.getPrice()));
        }

        // Hiển thị hình ảnh từ mảng byte (BLOB)
        byte[] imageByteArray = product.getImage();
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
    }

    private class GetDiscountTask extends AsyncTask<Integer, Void, Discount> {
        private ViewHolder viewHolder;

        public GetDiscountTask(ViewHolder viewHolder) {
            this.viewHolder = viewHolder;
        }

        @Override
        protected Discount doInBackground(Integer... productIds) {
            // Thực hiện truy vấn cơ sở dữ liệu ở đây
            int productId = productIds[0];
            return discountDbHelper.getDiscountByProductID(productId);
        }

        @Override
        protected void onPostExecute(Discount discount) {
            // Cập nhật giao diện sau khi nhận được kết quả từ truy vấn
            if (discount != null && discount.getValue() > 0) {
                viewHolder.lbDiscount.setVisibility(View.VISIBLE);
                viewHolder.lbDiscount.setText(discount.getValue() + "%");
            } else {
                viewHolder.lbDiscount.setVisibility(View.GONE);
            }
        }
    }


    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView hinh;
        public TextView txtTenSp, txtGiaSP, lbDiscount, txtGiaKhiDiscount;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtGiaKhiDiscount = itemView.findViewById(R.id.txtGiakhiDiscount);
            lbDiscount = itemView.findViewById(R.id.lbDiscount);
            txtTenSp = itemView.findViewById(R.id.txtTenSP);
            txtGiaSP = itemView.findViewById(R.id.txtGiaSP);
            hinh = itemView.findViewById(R.id.hinhSP);
        }
    }
}
