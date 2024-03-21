package com.example.myapplication.activity.admin;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.dbhelper.ProductDBHelper;
import com.example.myapplication.dbhelper.ProductTypeDBHelper;
import com.example.myapplication.model.Product;
import com.example.myapplication.model.ProductType;

public class AdminProductTypeActivity extends AppCompatActivity {
    EditText edtIDProType, edtNameType, edtStatusType;
    Button btnProTypeAdd, btnProTypeUpdate, btnProTypeDelete;
    ImageView btnBack2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_product_type);
        addControls();
        handEvents();
    }

    private void handEvents() {
        btnProTypeAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertDataPro();
            }
        });
        btnProTypeUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteDataPro();
            }
        });
        btnProTypeDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDataPro();
            }
        });
    }

    private boolean validate(String id, String name, String status) {
        if (id.equals("")) {
            edtIDProType.setError("Không được bỏ trống");
            return false;
        }
        if (name.equals("")) {
            edtNameType.setError("Không được bỏ trống");
            return false;
        }
        return true;
    }

    private void insertDataPro() {
        String id = edtIDProType.getText().toString();
        String name = edtNameType.getText().toString();
        String status = edtStatusType.getText().toString();


        try {
            if (parseInt(id) <= 0) {
                edtIDProType.setError("Không hợp lệ");
                return;
            }

            if (!validate(id, name, status)) {
                Toast.makeText(this, "Nhập thông tin sai", Toast.LENGTH_SHORT).show();
            } else {

                ProductType productType = new ProductType(parseInt(id), name, status);
                ProductTypeDBHelper productTypeDBHelper=new ProductTypeDBHelper(this);
                long rowId = productTypeDBHelper.insertProType(productType);

                if (rowId > 0) {
                    Toast.makeText(this, " Thành công !", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(this, "Đã xảy ra lỗi.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Sai định dạng", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateDataPro() {
        String id = edtIDProType.getText().toString();
        String name = edtNameType.getText().toString();
        String status = edtStatusType.getText().toString();

        try {
            if (!validate(id, name, status)) {
                Toast.makeText(AdminProductTypeActivity.this, "Nhập thông tin sai", Toast.LENGTH_SHORT).show();
                return;
            }

            ProductType productType = new ProductType(parseInt(id), name, status);
            ProductTypeDBHelper productTypeDBHelper = new ProductTypeDBHelper(this);
            int numRowsAffected = productTypeDBHelper.updateProType(productType);

            if (numRowsAffected > 0) {
                Toast.makeText(this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                edtIDProType.setText("");
                edtNameType.setText("");
                edtStatusType.setText("");
            } else {
                Toast.makeText(this, "Cập nhật thất bại hoặc không có dữ liệu để cập nhật.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Sai định dạng", Toast.LENGTH_SHORT).show();
        }
    }
    private void deleteDataPro() {
        String id = edtIDProType.getText().toString();

        if (id.equals("")) {
            Toast.makeText(AdminProductTypeActivity.this, "Nhập thông tin sai", Toast.LENGTH_SHORT).show();
            return;
        }

        ProductTypeDBHelper productTypeDBHelper = new ProductTypeDBHelper(this);
        ProductType productType = productTypeDBHelper.getProductTypeByID(parseInt(id));
        if (productType == null) {
            Toast.makeText(AdminProductTypeActivity.this, "Không tìm thấy", Toast.LENGTH_SHORT).show();
            return;
        }

        long rowId = productTypeDBHelper.deleteProType(productType);
        if (rowId > 0) {
            Toast.makeText(this, " Thành công !", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(AdminProductTypeActivity.this, ShowAdProductActivity.class));
            finish();
        } else
            Toast.makeText(this, "Đã xảy ra lỗi .", Toast.LENGTH_SHORT).show();
    }



    private void addControls() {
        edtIDProType=findViewById(R.id.edtIDProType);
        edtNameType=findViewById(R.id.edtNameType);
        edtStatusType=findViewById(R.id.edtStatusType);
        btnProTypeAdd=findViewById(R.id.btnProTypeAdd);
        btnProTypeUpdate=findViewById(R.id.btnProTypeUpdate);
        btnProTypeDelete=findViewById(R.id.btnProTypeDelete);


        ProductType productType = (ProductType) getIntent().getSerializableExtra("productType");

        if (productType != null) {
            edtIDProType.setText(productType.getId().toString());
            edtNameType.setText(productType.getName().toString());
            edtStatusType.setText(productType.getStatus().toString());
        }
    }
}