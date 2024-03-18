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

public class ProductRecyclerViewAdapter extends RecyclerView.Adapter<ProductRecyclerViewAdapter.ViewHolder> {
    private List<Product> productList;
    private Context context;
    private DiscountDBHelper discountDbHelper;

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

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.bind(product);
        new GetDiscountTask(holder, product).execute(product.getId());
        byte[] imageByteArray = product.getImage1();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);
        holder.hinh.setImageBitmap(bitmap);
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailProActivity.class);
            intent.putExtra("product", product);
            context.startActivity(intent);
        });
    }

    private class GetDiscountTask extends AsyncTask<Integer, Void, Discount> {
        private ViewHolder viewHolder;
        private Product product;

        public GetDiscountTask(ViewHolder viewHolder, Product product) {
            this.viewHolder = viewHolder;
            this.product = product;
        }

        @Override
        protected Discount doInBackground(Integer... productIds) {
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
                viewHolder.hideDiscount();
            }
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView hinh;
        private Product product;
        private TextView txtTenSp, txtGiaSP, lbDiscount, txtGiaKhiDiscount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtGiaKhiDiscount = itemView.findViewById(R.id.txtGiakhiDiscount);
            lbDiscount = itemView.findViewById(R.id.lbDiscount);
            txtTenSp = itemView.findViewById(R.id.txtTenSP);
            txtGiaSP = itemView.findViewById(R.id.txtGiaSP);
            hinh = itemView.findViewById(R.id.hinhSP);
        }

        public void bind(Product product) {
            this.product = product;
            txtTenSp.setText(product.getName());
        }

        @SuppressLint("SetTextI18n")
        public void updateDiscount(Discount discount) {
            lbDiscount.setVisibility(View.VISIBLE);
            lbDiscount.setText(discount.getValue() + "%");
            txtGiaSP.setVisibility(View.VISIBLE);
            txtGiaKhiDiscount.setVisibility(View.VISIBLE);
            double priceDiscount = product.getPrice() - (product.getPrice() * ((double) discount.getValue()) / 100);
            String formattedPrice = new DecimalFormat("#,###").format(priceDiscount);
            String originalPrice = new DecimalFormat("#,###").format(product.getPrice());
            txtGiaKhiDiscount.setText(originalPrice + "đ");
            txtGiaSP.setText(formattedPrice + "đ");
        }

        @SuppressLint("SetTextI18n")
        public void hideDiscount() {
            lbDiscount.setVisibility(View.GONE);
            txtGiaKhiDiscount.setVisibility(View.GONE);
            txtGiaSP.setVisibility(View.VISIBLE);
            String originalPrice = new DecimalFormat("#,###").format(product.getPrice());
            txtGiaSP.setText(originalPrice + "đ");
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateList(List<Product> newProductList) {
        productList.clear();
        productList.addAll(newProductList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


    @SuppressLint("NotifyDataSetChanged")
    public void updateData(List<Product> newData) {
        productList.clear();
        productList.addAll(newData);
        notifyDataSetChanged();
    }
}
