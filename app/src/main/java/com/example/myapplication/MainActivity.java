package com.example.myapplication;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.Adapter.AdapterProduct;
import com.example.myapplication.fragment.CartFragment;
import com.example.myapplication.fragment.CategoryFragment;
import com.example.myapplication.fragment.HomeFragment;
import com.example.myapplication.fragment.ProfileFragment;
import com.example.myapplication.model.Product;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;
    public String DB_NAME = "AppShoes";
    public String DB_SUFFIX_PATH = "/databases/";
    public static SQLiteDatabase database = null;
    ListView listView;
    ArrayList<Product> list;
    AdapterProduct adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();
        readData();
        processCopy();

        //mặc đinh khi run app sẽ hiển thị trang HomeFragment
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new HomeFragment()).commit();
        // Khởi tạo và cấu hình BottomNavigationView
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fm;
                if (item.getItemId() == R.id.mnHome) {
                    fm = new HomeFragment();
                    loadFragment(fm);
                    return true;
                }
                if (item.getItemId() == R.id.mnCategory) {
                    fm = new CategoryFragment();
                    loadFragment(fm);
                    return true;
                }
                if (item.getItemId() == R.id.mnCart) {
                    fm = new CartFragment();
                    loadFragment(fm);
                    return true;
                }
                if (item.getItemId() == R.id.mnProfile) {
                    fm = new ProfileFragment();
                    loadFragment(fm);
                    return true;
                }

                return false;
            }
        });

    }

    private void readData() {
        database=openOrCreateDatabase(DB_NAME,MODE_PRIVATE, null);
        Cursor cursor= database.rawQuery("select * from tbUser", null);
        list.clear();
      // for(int i =0; i < cursor.getCount(); i++){
          // cursor.moveToPosition(i);
        while(cursor.moveToNext()) {
            String id = cursor.getString(0);
            String type = cursor.getString(1);
            String name = cursor.getString(2);
            Double price  = cursor.getDouble(3);
            String image = cursor.getString(4);
            String detail = cursor.getString(5);
            Float star = cursor.getFloat(6);
            String status =cursor.getString(7);
         list.add(new Product(id, type, name, price, image, detail, star, status));
       }
        adapter.notifyDataSetChanged();
    }

    private void addControls() {
        bottomNav = findViewById(R.id.bottomNav);
        listView = (ListView) findViewById(R.id.listView);
        list = new ArrayList<>();
        adapter = new AdapterProduct(this, list);
        listView.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.bottom_menu, menu);
        return true;
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }



    public String getDBPath() {
        return getApplicationInfo().dataDir + DB_SUFFIX_PATH + DB_NAME;
    }

    private void processCopy() {
        try {
            File file = getDatabasePath(DB_NAME);
            if (!file.exists()) {
                copyDBFromAssets();
                Toast.makeText(this, "copy DB successful", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception ex) {
            Toast.makeText(this, "copy DB fail", Toast.LENGTH_SHORT).show();
        }

    }

    private void copyDBFromAssets() {
        try {
            InputStream inputFile = getAssets().open(DB_NAME);
            String outputFileName = getDBPath();
            File file = new File(outputFileName); // Thay đổi thành tên tệp đầu ra
            if (!file.exists()) {
                File dir = new File(getApplicationInfo().dataDir + DB_SUFFIX_PATH);
                if (!dir.exists()) {
                    dir.mkdirs(); // Tạo thư mục nếu chúng không tồn tại
                }
                OutputStream outputFile = new FileOutputStream(outputFileName);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputFile.read(buffer)) > 0) outputFile.write(buffer, 0, length);
                outputFile.flush();
                outputFile.close();
            }
            inputFile.close(); // Di chuyển vào bên trong khối if để đóng chỉ khi cần sao chép
        } catch (Exception ex) {
            Log.e("Error", ex.toString());
        }
    }
}