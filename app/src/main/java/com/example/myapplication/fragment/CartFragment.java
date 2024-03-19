package com.example.myapplication.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.adapter.CartAdapter;
import com.example.myapplication.dbhelper.BillDBHelper;
import com.example.myapplication.dbhelper.CartDBHelper;
import com.example.myapplication.dbhelper.DiscountDBHelper;
import com.example.myapplication.dbhelper.NotificationDBHelper;
import com.example.myapplication.model.Bill;
import com.example.myapplication.model.Cart;
import com.example.myapplication.model.Notification;
import com.example.myapplication.utilities.SessionManager;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CartFragment extends Fragment implements CartAdapter.CartUpdateListener {
    MainActivity mainActivity;
    private CartDBHelper cartDBHelper;
    LinearLayout layoutOrder;
    AppCompatButton btnOrder;
    private RecyclerView recyclerView;
    ConstraintLayout coSP;
    EditText edtphone, edtaddress, edtname;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        sessionManager = new SessionManager(requireContext().getApplicationContext());
        userDetails = sessionManager.getUserDetails();
        mainActivity = new MainActivity();
        initializeViews(view);
        setupRecyclerView();
        if (userDetails != null) {
            String userid = userDetails.get(sessionManager.KEY_IDUSER);
            if (userid != null) {
                cartDBHelper = new CartDBHelper(requireContext().getApplicationContext());
                List<Cart> carts = cartDBHelper.getAllCartByStatus(Integer.valueOf(userid), "wait");
                CartAdapter adapter = new CartAdapter(requireContext(), carts, recyclerView);
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
        return view;
    }

    private boolean validate(String name, String phone, String address) {

        if (name.equals("")) {
            edtname.setError("Không được bỏ trống");
            return false;
        }
        if (phone.equals("")) {
            edtphone.setError("Không được bỏ trống");
            return false;
        }
        if (address.equals("")) {
            edtaddress.setError("Không được bỏ trống");
            return false;
        }
        return true;
    }

    private void setupOrder() {
        String name = edtname.getText().toString().trim();
        String phone = edtphone.getText().toString().trim();
        String address = edtaddress.getText().toString().trim();
        //Nếu không đủ thông tin thì báo lỗi
        if (!validate(name, phone, address)) {
            Toast.makeText(requireContext(), "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Xác nhận đặt hàng");
        builder.setMessage("Bạn có chắc chắn muốn đặt hàng?");
        builder.setPositiveButton("Đặt hàng", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String userid = userDetails.get(sessionManager.KEY_IDUSER);
                cartDBHelper = new CartDBHelper(requireContext());
                List<Cart> listCart = cartDBHelper.getAllCartByStatus(Integer.valueOf(userid), "wait");

                if (listCart != null && !listCart.isEmpty()) {
                    for (Cart cart : listCart) {
                        int cartId = cart.getId(); // Lấy id của giỏ hàng
                        //tính giá cuối cùng của 1 giỏ hàng
                        double price = PriceEachPro(cart);
                        // Tạo một hóa đơn với thông tin từ giỏ hàng và các thông tin khác
                        Bill bill = new Bill(Integer.parseInt(userid), cartId, phone, address, (int) price);
                        // Thêm hóa đơn vào cơ sở dữ liệu
                        BillDBHelper billDbHelper = new BillDBHelper(requireContext());
                        long result = billDbHelper.insert(bill);

                        if (result > 0) {
                            byte[] img = cart.getProduct().getImage1();
                            String tenPro = cart.getProduct().getName();
                            Notification notification = new Notification(Integer.parseInt(userid), Notification.NOTIFY_ORDER_PRODUCT,
                                    "Bạn đã đặt thành công đơn hàng " + tenPro, img);
                            NotificationDBHelper notificationDBHelper = new NotificationDBHelper(requireContext());
                            notificationDBHelper.insert(notification);
                            //sau khi đặt hàng thành công thì chuyển trạng thái phẩm thành đã đặt hàng
                            for (Cart cart1 : listCart) {
                                cartDBHelper.updateOrderStatus(cart1.getId());
                            }
                            setDisplayCart(new ArrayList<>());
                            Toast.makeText(requireContext(), "Đã đặt hàng thành công.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(requireContext(), "Không thành công.", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(requireContext(), "Không có sản phẩm trong giỏ hàng", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Đóng hộp thoại nếu người dùng chọn hủy
                dialogInterface.dismiss();
            }
        });
        builder.show();
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
        edtphone = view.findViewById(R.id.edtPhoneBill);
        edtaddress = view.findViewById(R.id.edtLocation);
        edtname = view.findViewById(R.id.edtName);
        layoutOrder = view.findViewById(R.id.layoutThanhToan);


    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
    }

    private void setupCartAdapter(CartAdapter adapter) {
        recyclerView.setAdapter(adapter);
        adapter.setCartUpdateListener(this);
    }

    private double PriceEachPro(Cart cart) {
        double percentTax = 0.02;
        double delivery = 20000;

        double totaldiscount = new DiscountDBHelper(requireContext().getApplicationContext()).discountForEachPro(cart);
        double subTotal = cart.getProduct().getPrice();
        double subTotalEnd = subTotal - totaldiscount;
        double total = subTotalEnd + delivery + (subTotalEnd * percentTax);
        return total;
    }

    @SuppressLint("SetTextI18n")
    private void setupCartInformation(List<Cart> carts, String id) {
        double percentTax = 0.02;
        double delivery = 20000;
        double discount = new DiscountDBHelper(requireContext().getApplicationContext()).calculateDiscount(carts);
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

        setDisplayCart(carts);
    }

    private void setDisplayCart(List<Cart> carts) {
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
                List<Cart> carts = cartDBHelper.getAllCartByStatus(Integer.valueOf(id), "wait");
                setupCartInformation(carts, id);
            }
        }
    }
}
