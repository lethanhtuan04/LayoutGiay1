package com.example.myapplication.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.adapter.HorizontalProductAdapter;
import com.example.myapplication.adapter.ProductRecyclerViewAdapter;
import com.example.myapplication.dbhelper.ProductDBHelper;
import com.example.myapplication.model.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    private Context mContext;

    ViewFlipper viewFlipper;
    RecyclerView recyclerView;
    ProductRecyclerViewAdapter productRecyclerViewAdapter;
    List<Product> productList;
    ProductDBHelper productDBHelper;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
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

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        if (mContext != null) {
            recyclerView = view.findViewById(R.id.vRecyclerView);
            productDBHelper = new ProductDBHelper(mContext);
            productList = productDBHelper.getAllProducts();
            productRecyclerViewAdapter = new ProductRecyclerViewAdapter(productList, mContext);
            recyclerView.setAdapter(productRecyclerViewAdapter);
            recyclerView.setHasFixedSize(true);
            GridLayoutManager layoutManager = new GridLayoutManager(mContext, 2);
            recyclerView.setLayoutManager(layoutManager);
//            recyclerView.setFocusable(false);
//            recyclerView.setNestedScrollingEnabled(false);


            RecyclerView rcvHot = view.findViewById(R.id.rcvHot);
            rcvHot.setHasFixedSize(true);
            LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
            rcvHot.setLayoutManager(horizontalLayoutManager);
            List<Product> discountedProducts = productDBHelper.getDiscountProducts();

            HorizontalProductAdapter horizontalProductAdapter = new HorizontalProductAdapter(discountedProducts, mContext);
                   rcvHot.setAdapter(horizontalProductAdapter);
        } else {
            Toast.makeText(getActivity(), "Loi roi ba oi", Toast.LENGTH_SHORT).show();
        }
        viewFlipper = view.findViewById(R.id.viewlipper);
        ActionViewFlipper();
        return view;
    }

    private void ActionViewFlipper() {
        List<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("https://lambanner.com/wp-content/uploads/2022/10/MNT-DESIGN-BANNER-GIAY-01.jpg");
        mangquangcao.add("https://lambanner.com/wp-content/uploads/2022/10/MNT-DESIGN-BANNER-GIAY-07.jpg");
        mangquangcao.add("https://lambanner.com/wp-content/uploads/2022/10/MNT-DESIGN-BANNER-GIAY-08.jpg");
        for (int i = 0; i < mangquangcao.size(); i++) {
            ImageView imageView = new ImageView(getContext());
            Glide.with(getContext()).load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        Animation slide_in = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_right);
        Animation slide_out = AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_right);
        viewFlipper.setInAnimation(slide_in);
        viewFlipper.setOutAnimation(slide_out);
    }

}
