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
        setChangeNumber(holder, cart);

        if (discount != null) {
            DecimalFormat decimalFormat = new DecimalFormat("#,###");
            double discountedPrice = DiscountDBHelper.calculateDiscountedPrice(cart.getProduct(), discount);
            String price = decimalFormat.format(cart.getProduct().getPrice());
            String disPrice = decimalFormat.format(discountedPrice);

            holder.cartPrice.setText(disPrice + "đ");
            holder.giakhiDis.setText(price);

            byte[] imageByteArray = cart.getProduct().getImage1();
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);
            holder.cartImage.setImageBitmap(bitmap);


            holder.lbDis.setText(discount.getValue() + "%");
            holder.cartTitle.setText(cart.getProduct().getName());
            holder.cartQuantity.setText(String.valueOf(cart.getQuantity()));
        } else {
            DecimalFormat decimalFormat = new DecimalFormat("#,###");
            String price = decimalFormat.format(cart.getProduct().getPrice());
            holder.cartPrice.setText(price + " đ");
            holder.lbDis.setVisibility(View.GONE);
            holder.giakhiDis.setVisibility(View.GONE);

            byte[] imageByteArray = cart.getProduct().getImage1();
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);
            holder.cartImage.setImageBitmap(bitmap);

            holder.cartTitle.setText(cart.getProduct().getName());
            holder.cartQuantity.setText(String.valueOf(cart.getQuantity()));

        }

        btnDeleteItems(holder);


        updateToFragment();
    }

    // phương thức để thông báo sự thay đổi đến CartFragment
    private void updateToFragment() {
        if (cartUpdateListener != null) {
            cartUpdateListener.onCartUpdated();
        }
    }

    //nút bấm xóa items
    private void btnDeleteItems(CartViewHolder holder) {
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    // Xóa dữ liệu từ cơ sở dữ liệu SQLite
                    setDeleteData(carts.get(position).getId());
                    // Xóa mục khỏi danh sách hiển thị trên RecyclerView
                    removeItem(position);
                    // Cập nhật giá sau khi xóa mục
                    // Gọi phương thức để thông báo sự thay đổi đến CartFragment
                    if (cartUpdateListener != null) {
                        cartUpdateListener.onCartUpdated();
                    }
                }
            }
        });
    }

    public void removeItem(int position) {
        carts.remove(position);
        notifyItemRemoved(position);
        setDeleteData(position);
        notifyItemRangeChanged(position, carts.size());
    }

    private void setChangeNumber(CartViewHolder holder, Cart cart) {
        holder.tangSL.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
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
                updateToFragment();
                // Cập nhật lại giao diện RecyclerView
                onCartUpdated();
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
                    updateToFragment();
                    // Cập nhật lại giao diện RecyclerView
                    onCartUpdated();
                }
            }
        });
    }

    public void setUpdateData(Cart cart) {
        if (cart != null) {
            CartDBHelper cartDBHelper = new CartDBHelper(context.getApplicationContext());
            cartDBHelper.update(cart);
        }
    }

    public void setDeleteData(Integer id) {
        CartDBHelper cartDBHelper = new CartDBHelper(context.getApplicationContext());
        cartDBHelper.delete(id);
    }


    @Override
    public int getItemCount() {
        return carts.size();
    }

    @SuppressLint("NotifyDataSetChanged")
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
