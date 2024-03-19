package com.example.myapplication.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.activity.AccountSettingsActivity;
import com.example.myapplication.activity.HistoryUserActivity;
import com.example.myapplication.activity.LoginActivity;
import com.example.myapplication.activity.NotificationActivity;
import com.example.myapplication.activity.StatusBillActivity;
import com.example.myapplication.utilities.SessionManager;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    SessionManager sessionManager;
    TextView txtusername, txtemail;

    AppCompatButton btnlogout;
    LinearLayout btnaccountSettings, deliveryStatus, btnNotiProfile, historyBill;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        // Khởi tạo SessionManager
        sessionManager = new SessionManager(requireActivity().getApplicationContext());

        txtusername = view.findViewById(R.id.txtusernamepro);
        txtemail = view.findViewById(R.id.txtemailpro);
        btnNotiProfile = view.findViewById(R.id.btnNotiProfile);
        historyBill = view.findViewById(R.id.historyBill);


        HashMap<String, String> userDetails = sessionManager.getUserDetails();
        String username = userDetails.get(SessionManager.KEY_USERNAME);
        String email = userDetails.get(SessionManager.KEY_EMAIL);
        txtusername.setText(username);
        txtemail.setText(email);

        btnlogout = view.findViewById(R.id.btnlogout);
        btnaccountSettings = view.findViewById(R.id.btnaccountSettings);

        deliveryStatus = view.findViewById(R.id.deliveryStatus);
        deliveryStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), StatusBillActivity.class));
            }
        });
        historyBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), HistoryUserActivity.class));
            }
        });
        btnNotiProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), NotificationActivity.class));
            }
        });

        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                sessionManager.logoutUser();
                // Chuyển đến LoginActivity sau khi đăng xuất
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });


        btnaccountSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển đến AccountSettingsActivity
                startActivity(new Intent(getActivity(), AccountSettingsActivity.class));
            }
        });
        return view;
    }
}