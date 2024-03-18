package com.example.myapplication.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.activity.AllProductActivity;
import com.example.myapplication.activity.NotificationActivity;
import com.example.myapplication.activity.SearchViewActivity;
import com.example.myapplication.adapter.HorizontalProductAdapter;
import com.example.myapplication.adapter.ProductRecyclerViewAdapter;
import com.example.myapplication.dbhelper.ProductDBHelper;
import com.example.myapplication.model.Product;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private Context mContext;
    private TextView searchView, btnseeAllPro;
    private ViewFlipper viewFlipper;
    private RecyclerView recyclerView;
    private ProductRecyclerViewAdapter productRecyclerViewAdapter;
    private ArrayList<Product> productList;
    private ImageView btnNotifromHome;
    private ProductDBHelper productDBHelper;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initViews(view);
        setupSearchView();
        setupVerticalRecyclerView();
        setupHorizontalRecyclerView(view);
        setupViewFlipper();
        return view;
    }

    private void initViews(View view) {
        searchView = view.findViewById(R.id.searchView1);
        recyclerView = view.findViewById(R.id.vRecyclerView);
        viewFlipper = view.findViewById(R.id.viewlipper);
        btnNotifromHome = view.findViewById(R.id.btnNotifromHome);
        btnseeAllPro = view.findViewById(R.id.btnSeeAll);
        btnseeAllPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AllProductActivity.class));
            }
        });
        btnNotifromHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), NotificationActivity.class));
            }
        });
    }

    private void setupSearchView() {
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SearchViewActivity.class));
            }
        });
    }

    private void setupVerticalRecyclerView() {
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        productDBHelper = new ProductDBHelper(mContext);
        productList = productDBHelper.getAllProducts();
        productRecyclerViewAdapter = new ProductRecyclerViewAdapter(productList, mContext);
        recyclerView.setAdapter(productRecyclerViewAdapter);
    }

    private void setupHorizontalRecyclerView(View view) {
        RecyclerView rcvHot = view.findViewById(R.id.rcvHot);
        rcvHot.setHasFixedSize(true);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        rcvHot.setLayoutManager(horizontalLayoutManager);
        List<Product> discountedProducts = productDBHelper.getDiscountProducts();
        HorizontalProductAdapter horizontalProductAdapter = new HorizontalProductAdapter(discountedProducts, mContext);
        rcvHot.setAdapter(horizontalProductAdapter);
    }

    private void setupViewFlipper() {
        List<String> imageUrls = new ArrayList<>();
        imageUrls.add("https://lambanner.com/wp-content/uploads/2022/10/MNT-DESIGN-BANNER-GIAY-01.jpg");
        imageUrls.add("https://lambanner.com/wp-content/uploads/2022/10/MNT-DESIGN-BANNER-GIAY-07.jpg");
        imageUrls.add("https://lambanner.com/wp-content/uploads/2022/10/MNT-DESIGN-BANNER-GIAY-08.jpg");

        for (String url : imageUrls) {
            ImageView imageView = new ImageView(getContext());
            Glide.with(mContext).load(url).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }

        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);

        Animation slideIn = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_right);
        Animation slideOut = AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_right);
        viewFlipper.setInAnimation(slideIn);
        viewFlipper.setOutAnimation(slideOut);
    }
}
