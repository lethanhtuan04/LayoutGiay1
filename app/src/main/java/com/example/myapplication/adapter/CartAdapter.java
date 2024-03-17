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
import com.example.myapplication.R;
import com.example.myapplication.dbhelper.CartDBHelper;
import com.example.myapplication.dbhelper.DiscountDBHelper;
import com.example.myapplication.model.Cart;
import com.example.myapplication.model.Discount;
import com.example.myapplication.utilities.CartUpdateListener;

import java.text.DecimalFormat;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> implements CartUpdateListener {

    private List<Cart> carts;
    private Context context;
    static RecyclerView recyclerView;
    private CartUpdateListener cartUpdateListener;

    //    public CartAdapter(Context context, List<Cart> carts) {
//        this.context = context;
//        this.carts = carts;
//    }
    public interface CartUpdateListener {
        void onCartUpdated();
    }
    public CartAdapter(Context context, List<Cart> carts, RecyclerView recyclerView) {
        this.context = context;
        this.carts = carts;
        this.recyclerView = recyclerView;
    }

    public void setCartUpdateListener(CartUpdateListener listener) {
        this.cartUpdateListener = listener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_pro_cart, parent, false);
        return new CartViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Cart cart = carts.get(position);
        DiscountDBHelper discountDBHelper = new DiscountDBHelper(context.getApplicationContext());
        Discount discount = discountDBHelper.getDiscountByProductID(cart.getProduct().getId());
        holder.tangSL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentQuantity = cart.getQuantity();
                currentQuantity++;
                cart.setQuantity(currentQuantity);

                // Cập nhật giá mới cho sản phẩm

                setUpdateData(cart);
                holder.cartQuantity.setText(String.valueOf(currentQuantity));

                // Cập nhật giá sau khi tăng số lượng

                // Gọi phương thức để thông báo sự thay đổi đến CartFragment
                if (cartUpdateListener != null) {
                    cartUpdateListener.onCartUpdated();
                }

                // Cập nhật lại giao diện RecyclerView
                notifyDataSetChanged();
            }
        });

        holder.giamSL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentQuantity = cart.getQuantity();
                if (currentQuantity > 1) {
                    currentQuantity--;
                    cart.setQuantity(currentQuantity);

                    // Cập nhật giá mới cho sản phẩm


                    setUpdateData(cart);
                    holder.cartQuantity.setText(String.valueOf(currentQuantity));

                    // Cập nhật giá sau khi giảm số lượng

                    // Gọi phương thức để thông báo sự thay đổi đến CartFragment
                    if (cartUpdateListener != null) {
                        cartUpdateListener.onCartUpdated();
                    }

                    // Cập nhật lại giao diện RecyclerView
                    notifyDataSetChanged();
                }
            }
        });

        if (discount != null) {
            DecimalFormat decimalFormat = new DecimalFormat("#,###");
            double discountedPrice = DiscountDBHelper.calculateDiscountedPrice(cart.getProduct(), discount);
            String price = decimalFormat.format(cart.getProduct().getPrice());
            String disPrice = decimalFormat.format(discountedPrice);

            holder.cartPrice.setText(disPrice + "đ");
            holder.giakhiDis.setText(price);

            Glide.with(context)
                    .load(cart.getProduct().getImage())
                    .into(holder.cartImage);
            holder.lbDis.setText(discount.getValue() + "%");
            holder.cartTitle.setText(cart.getProduct().getName());

            holder.cartQuantity.setText(String.valueOf(cart.getQuantity()));
        } else {
            DecimalFormat decimalFormat = new DecimalFormat("#,###");
            String price = decimalFormat.format(cart.getProduct().getPrice());
            holder.cartPrice.setText(price);
            holder.lbDis.setVisibility(View.GONE);
            holder.giakhiDis.setVisibility(View.GONE);
            Glide.with(context)
                    .load(cart.getProduct().getImage())
                    .into(holder.cartImage);
            holder.cartTitle.setText(cart.getProduct().getName());
            holder.cartQuantity.setText(String.valueOf(cart.getQuantity()));

        }

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý sự kiện xóa ở đây
            }
        });

        if (cartUpdateListener != null) {
            cartUpdateListener.onCartUpdated();
        }

    }

    public void setUpdateData(Cart cart) {
        if (cart != null) {
            CartDBHelper cartDBHelper = new CartDBHelper(context.getApplicationContext());
            cartDBHelper.update(cart);
        }
    }



    @Override
    public int getItemCount() {
        return carts.size();
    }

    @Override
    public void onCartUpdated() {
        notifyDataSetChanged();
    }


    static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView cartImage;
        TextView cartQuantity;
        TextView cartTitle, btnDelete;
        TextView cartPrice, lbDis, giakhiDis;
        ImageView tangSL, giamSL;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            tangSL = itemView.findViewById(R.id.tangSL);
            giamSL = itemView.findViewById(R.id.giamSL);
            cartQuantity = itemView.findViewById(R.id.txtQuantity);
            cartImage = itemView.findViewById(R.id.cartImage);
            cartTitle = itemView.findViewById(R.id.cartProductName);
            cartPrice = itemView.findViewById(R.id.txtgia);
            lbDis = itemView.findViewById(R.id.lbDiscountCart);
            giakhiDis = itemView.findViewById(R.id.txtgiaDiscount);
            btnDelete = itemView.findViewById(R.id.btnDelete);

        }

    }

}
