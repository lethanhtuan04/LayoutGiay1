package com.example.myapplication.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.CartAdapter;
import com.example.myapplication.dbhelper.BillDBHelper;
import com.example.myapplication.dbhelper.CartDBHelper;
import com.example.myapplication.dbhelper.DiscountDBHelper;
import com.example.myapplication.model.Bill;
import com.example.myapplication.model.Cart;
import com.example.myapplication.utilities.SessionManager;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

public class CartFragment extends Fragment implements CartAdapter.CartUpdateListener {
    private CartDBHelper cartDBHelper;
    AppCompatButton btnOrder;
    private RecyclerView recyclerView;
    ConstraintLayout coSP;
    EditText edtname, edtphone, edtaddress;
    LinearLayout khongSP;
    private SessionManager sessionManager;
    private TextView tongPhu, tongChinh, tongThue, phiVan, txtTotal;
    private HashMap<String, String> userDetails;

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
        sessionManager = new SessionManager(getContext().getApplicationContext());
        userDetails = sessionManager.getUserDetails();
        initializeViews(view);
        setupRecyclerView();
        if (userDetails != null) {
            String userid = userDetails.get(sessionManager.KEY_IDUSER);
            if (userid != null) {
                cartDBHelper = new CartDBHelper(getContext().getApplicationContext());
                List<Cart> carts = cartDBHelper.getAllCarts(Integer.valueOf(userid));
                CartAdapter adapter = new CartAdapter(getContext(), carts, recyclerView);
                setupCartInformation(carts, userid);
                setupCartAdapter(adapter);
            }
        }
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupOrder();
            }
        });
        setupOrder();
        return view;
    }

    private void setupOrder() {
        String name = edtname.getText().toString().trim();
        String phone = edtphone.getText().toString().trim();
        String address = edtaddress.getText().toString().trim();
        String price = txtTotal.getText().toString().trim();

        String userid = userDetails.get(sessionManager.KEY_IDUSER);
        cartDBHelper = new CartDBHelper(getContext());
        List<Cart> listCart = cartDBHelper.getAllCarts(Integer.valueOf(userid));

        if (listCart != null && !listCart.isEmpty()) {
            for (Cart cart : listCart) {
                int cartId = cart.getId(); // Lấy id của giỏ hàng
                // Tạo một hóa đơn với thông tin từ giỏ hàng và các thông tin khác
                Bill bill = new Bill(Integer.parseInt(userid), cartId, phone, address, Integer.parseInt(price));

                // Sau đó, bạn có thể thực hiện thêm hóa đơn vào cơ sở dữ liệu
                BillDBHelper billDbHelper = new BillDBHelper(getContext());
                long result = billDbHelper.insert(bill);

                // Kiểm tra kết quả và xử lý tương ứng
                if (result > 0) {
                    // Xử lý khi đặt hàng thành công
                } else {
                    // Xử lý khi đặt hàng không thành công
                }
            }
        } else {
            // Xử lý khi danh sách giỏ hàng trống
        }
    }

    private void initializeViews(View view) {
        tongPhu = view.findViewById(R.id.txtTotalFee);
        tongChinh = view.findViewById(R.id.txtTotal);
        tongThue = view.findViewById(R.id.txtTax);
        txtTotal = view.findViewById(R.id.txttotal);
        phiVan = view.findViewById(R.id.txtDelivery);

        recyclerView = view.findViewById(R.id.list_item_pro_cart);
        coSP = view.findViewById(R.id.viewCoSP);
        khongSP = view.findViewById(R.id.viewKhongCoSanPham);

        btnOrder = view.findViewById(R.id.btnOrder);
        edtname = view.findViewById(R.id.edtName);
        edtphone = view.findViewById(R.id.edtPhone);
        edtaddress = view.findViewById(R.id.edtLocation);

    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void setupCartAdapter(CartAdapter adapter) {
        recyclerView.setAdapter(adapter);
        adapter.setCartUpdateListener(this);
    }

    @SuppressLint("SetTextI18n")
    private void setupCartInformation(List<Cart> carts, String id) {
        double percentTax = 0.02;
        double delivery = 20000;
        double discount = new DiscountDBHelper(getContext().getApplicationContext()).calculateDiscount(carts);
        double subTotal = cartDBHelper.getTotalPhu(Integer.valueOf(id));
        double subTotalEnd = subTotal - discount;
        double total = subTotalEnd + delivery + (subTotalEnd * percentTax);

        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String fmTax = decimalFormat.format(subTotalEnd * percentTax);
        String fmdelivery = decimalFormat.format(delivery);
        String fmTotal = decimalFormat.format(total);
        String fmSubTotalEnd = decimalFormat.format(subTotalEnd);

        tongChinh.setText(fmTotal + " đ");
        tongPhu.setText(fmSubTotalEnd + " đ");
        tongThue.setText(fmTax + " đ");
        phiVan.setText(fmdelivery + " đ");
        txtTotal.setText(fmTotal + "đ");

        if (carts != null && !carts.isEmpty()) {
            coSP.setVisibility(View.VISIBLE); // Hiển thị layout có sản phẩm
            khongSP.setVisibility(View.GONE); // Ẩn layout không có sản phẩm
        } else {
            coSP.setVisibility(View.GONE); // Ẩn layout có sản phẩm
            khongSP.setVisibility(View.VISIBLE); // Hiển thị layout không có sản phẩm
        }
    }

    @Override
    public void onCartUpdated() {
        if (userDetails != null) {
            String id = userDetails.get(sessionManager.KEY_IDUSER);

            if (id != null) {
                List<Cart> carts = cartDBHelper.getAllCarts(Integer.valueOf(id));

                setupCartInformation(carts, id);
            }
        }
    }
}
