package com.example.myapplication.activity.admin;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static java.lang.Integer.valueOf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.dbhelper.DiscountDBHelper;
import com.example.myapplication.dbhelper.ProductDBHelper;
import com.example.myapplication.model.Discount;
import com.example.myapplication.model.Product;

import java.util.PrimitiveIterator;

public class AdminProductActivity extends AppCompatActivity {

    EditText edtMaSP, edtTenSP, edtLoaiSP, edtGiaSP, edtMoTaSP, edtHinhAnhSP;
    Button btnThemSP, btnSuaSP, btnXoaSP;
    ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_product);
        addControls();
        handleEvent();

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminProductActivity.this, ShowAdProductActivity.class);
                startActivity(intent);
            }
        });
    }

    private void handleEvent() {
        btnThemSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertData();
            }
        });
        btnXoaSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteData();
            }
        });
        btnSuaSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
            }
        });
    }

    private boolean validate(String id, String name, String type, String price, String detail) {
        if (id.equals("")) {
            edtMaSP.setError("Không được bỏ trống");
            return false;
        }
        if (type.equals("")) {
            edtLoaiSP.setError("Không được bỏ trống");
            return false;
        }
        if (name.equals("")) {
            edtTenSP.setError("Không được bỏ trống");
            return false;
        }
        if (price.equals("")) {
            edtGiaSP.setError("Không được bỏ trống");
            return false;
        }
        if (detail.equals("")) {
            edtMoTaSP.setError("Không được bỏ trống");
            return false;
        }
        return true;
    }

    private void insertData() {
        String id = edtMaSP.getText().toString();
        String type = edtLoaiSP.getText().toString();
        String name = edtTenSP.getText().toString();
        String price = edtGiaSP.getText().toString();
        String detail = edtMoTaSP.getText().toString();


        try {
            if (parseDouble(price) <= 0) {
                edtGiaSP.setError("Không hợp lệ");
                return;
            }

            if (!validate(id, name, type, price, detail)) {
                Toast.makeText(this, "Nhập thông tin sai", Toast.LENGTH_SHORT).show();
            } else {

                Product product = new Product(parseInt(id), name, parseInt(type), parseInt(price), detail);
                ProductDBHelper productDbHelper = new ProductDBHelper(this);
                long rowId = productDbHelper.insert(product);


                if (rowId > 0) {
                    Toast.makeText(this, " Thành công !", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(this, "Đã xảy ra lỗi.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Sai định dạng", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteData() {
        String id = edtMaSP.getText().toString();

        if (id.equals("")) {
            Toast.makeText(AdminProductActivity.this, "Nhập thông tin sai", Toast.LENGTH_SHORT).show();
            return;
        }

        ProductDBHelper productDBHelper = new ProductDBHelper(this);
        Product product = productDBHelper.getProductByID(parseInt(id));
        if (product == null) {
            Toast.makeText(AdminProductActivity.this, "Không tìm thấy", Toast.LENGTH_SHORT).show();
            return;
        }

        long rowId = productDBHelper.delete(product);
        if (rowId > 0) {
            Toast.makeText(this, " Thành công !", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(AdminProductActivity.this, ShowAdProductActivity.class));
            finish();
        } else
            Toast.makeText(this, "Đã xảy ra lỗi .", Toast.LENGTH_SHORT).show();
    }

    private void updateData() {
        String id = edtMaSP.getText().toString();
        String type = edtLoaiSP.getText().toString();
        String name = edtTenSP.getText().toString();
        String price = edtGiaSP.getText().toString();
        String detail = edtMoTaSP.getText().toString();

        try {
            if (!validate(id, type, name, price, detail)) {
                Toast.makeText(AdminProductActivity.this, "Nhập thông tin sai", Toast.LENGTH_SHORT).show();
                return;
            }

            Product product = new Product(parseInt(id), name, parseInt(type), parseInt(price), detail);
            ProductDBHelper productDBHelper = new ProductDBHelper(this);
            int numRowsAffected = productDBHelper.update(product);

            if (numRowsAffected > 0) {
                Toast.makeText(this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                edtMaSP.setText("");
                edtLoaiSP.setText("");
                edtTenSP.setText("");
                edtGiaSP.setText("");
                edtMoTaSP.setText("");
            } else {
                Toast.makeText(this, "Cập nhật thất bại hoặc không có dữ liệu để cập nhật.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Sai định dạng", Toast.LENGTH_SHORT).show();
        }
    }

    private void addControls() {
        edtMaSP = findViewById(R.id.edtMaSP);
        edtTenSP = findViewById(R.id.edtTenSP);
        edtLoaiSP = findViewById(R.id.edtLoaiSP);
        edtMoTaSP = findViewById(R.id.edtMoTaSP);
        edtGiaSP = findViewById(R.id.edtGiaSP);
        edtHinhAnhSP = findViewById(R.id.edtHinhAnhSP);

        btnThemSP = findViewById(R.id.btnThemSP1);
        btnXoaSP = findViewById(R.id.btnXoaSP);
        btnSuaSP = findViewById(R.id.btnSuaSP);

        Product product = (Product) getIntent().getSerializableExtra("product");

        if (product != null) {
            edtMaSP.setText(product.getId().toString());
            edtTenSP.setText(product.getName().toString());
            edtLoaiSP.setText(product.getType().toString());
            edtMoTaSP.setText(product.getDetail().toString());
            edtGiaSP.setText(product.getPrice() + "");
            edtHinhAnhSP.setText(product.getImage().toString());

        }
    }
}