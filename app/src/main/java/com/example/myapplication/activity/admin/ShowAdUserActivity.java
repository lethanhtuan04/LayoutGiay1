package com.example.myapplication.activity.admin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class ShowAdUserActivity extends AppCompatActivity {
    ListView listView;
    ImageView back;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_ad_user);

     //UserDBHelper userDbHelper = new UserDBHelper(this);
//      ArrayList<User> user = userDbHelper.getAllUser();
//      UserAdapter adapter=new UserAdapter(this,R.layout.items_ad_user,user);
//      listView = findViewById(R.id.listviewUser);
//       listView.setAdapter(adapter);



        back = findViewById(R.id.btnUsertoDB);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShowAdUserActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        });

    }
}