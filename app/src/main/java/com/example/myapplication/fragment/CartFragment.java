package com.example.myapplication.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.CartAdapter;
import com.example.myapplication.dbhelper.CartDBHelper;
import com.example.myapplication.dbhelper.DiscountDBHelper;
import com.example.myapplication.model.Cart;
import com.example.myapplication.utilities.SessionManager;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

public class CartFragment extends Fragment implements CartAdapter.CartUpdateListener {
    CartDBHelper cartDBHelper;
    RecyclerView recyclerView;
    SessionManager sessionManager;
    TextView tongPhu, tongChinh, tongThue, phiVan, txttotal;
    HashMap<String, String> userDetails;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public CartFragment() {
        // Required empty public constructor
    }

    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        tongPhu = view.findViewById(R.id.txtTotalFee);
        tongChinh = view.findViewById(R.id.txtTotal);
        tongThue = view.findViewById(R.id.txtTax);
        txttotal = view.findViewById(R.id.txttotal);

        phiVan = view.findViewById(R.id.txtDelivery);
        recyclerView = view.findViewById(R.id.list_item_pro_cart);
        sessionManager = new SessionManager(getContext().getApplicationContext());
        userDetails = sessionManager.getUserDetails();

        if (userDetails != null) {
            String id = userDetails.get(sessionManager.KEY_IDUSER);

            if (id != null) {
                CartDBHelper cartDBHelper = new CartDBHelper(getContext().getApplicationContext());
                List<Cart> carts = cartDBHelper.getAllCarts(Integer.valueOf(id));
                CartAdapter adapter = new CartAdapter(getContext(), carts, recyclerView);
                DiscountDBHelper discountDBHelper = new DiscountDBHelper(getContext().getApplicationContext());
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                DecimalFormat decimalFormat = new DecimalFormat("#,###");
                // Gọi hàm tính toán tổng giỏ hàng
                double percentTax = 0.02;
                double delivery = 20000;
                double discount = discountDBHelper.calculateDiscount(carts); // Tính tổng giảm giá
                double subTotal = cartDBHelper.getTotalPhu(Integer.valueOf(id)); // Tính tổng phụ
                double subTotal_end = subTotal - discount;
                double total = subTotal_end + delivery + (subTotal_end * percentTax);

                // Format các giá trị
                String fmTax = decimalFormat.format(subTotal_end * percentTax);
                String fmdelivery = decimalFormat.format(delivery);
                String fmTotal = decimalFormat.format(total);
                String fmSubTotal_end = decimalFormat.format(subTotal_end);


                tongChinh.setText(fmTotal + " đ");
                tongPhu.setText(fmSubTotal_end + " đ");
                tongThue.setText(fmTax + " đ");
                phiVan.setText(fmdelivery + " đ");
                txttotal.setText(fmTotal + "đ");

                CartAdapter adapter1 = new CartAdapter(getContext(), carts, recyclerView);
                adapter1.setCartUpdateListener(this); // Gán đối tượng lắng nghe sự kiện từ Fragment
                recyclerView.setAdapter(adapter1);
            }
        }
        return view;
    }

    @SuppressLint("SetTextI18n")
    public void onCartUpdated() {
        if (userDetails != null) {
            String id = userDetails.get(sessionManager.KEY_IDUSER);

            if (id != null) {
                CartDBHelper cartDBHelper = new CartDBHelper(getContext().getApplicationContext());
                List<Cart> carts = cartDBHelper.getAllCarts(Integer.valueOf(id));
                DiscountDBHelper discountDBHelper = new DiscountDBHelper(getContext().getApplicationContext());

                // Tính toán lại các giá trị
                double percentTax = 0.02;
                double delivery = 20000;
                double discount = discountDBHelper.calculateDiscount(carts); // Tính tổng giảm giá
                double subTotal = cartDBHelper.getTotalPhu(Integer.valueOf(id)); // Tính tổng phụ
                double subTotal_end = subTotal - discount;
                double total = subTotal_end + delivery + (subTotal_end * percentTax);

                // Format các giá trị
                DecimalFormat decimalFormat = new DecimalFormat("#,###");
                String fmTax = decimalFormat.format(subTotal_end * percentTax);
                String fmdelivery = decimalFormat.format(delivery);
                String fmTotal = decimalFormat.format(total);
                String fmSubTotal_end = decimalFormat.format(subTotal_end);

                // Cập nhật giá trị mới cho các TextView
                tongChinh.setText(fmTotal + " đ");
                tongPhu.setText(fmSubTotal_end + " đ");
                tongThue.setText(fmTax + " đ");
                phiVan.setText(fmdelivery + " đ");
                txttotal.setText(fmTotal + "đ");
            }
        }
    }
}