package com.example.myapplication.activity.admin;

import static java.lang.Integer.parseInt;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.dbhelper.DiscountDBHelper;
import com.example.myapplication.model.Discount;
import com.google.android.material.textfield.TextInputEditText;

public class AdminDiscountActivity extends AppCompatActivity {

    TextInputEditText txtAdIdDiscount,
            txtAdIdProductDiscount,
            txtAdValueDiscount,
            txtAdStatusDiscount;
    Button btnAdd,
            btnDelete,
            btnUpdate;
    ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_discount);

//        CreateData create = new CreateData(this);
        addControl();
        handleEvent();
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminDiscountActivity.this, ShowAdDiscountActivity.class);
                startActivity(intent);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void addControl() {
        txtAdIdDiscount = findViewById(R.id.txtAdIdDiscount);
        txtAdIdProductDiscount = findViewById(R.id.txtAdIdProductDiscount);
        txtAdValueDiscount = findViewById(R.id.txtAdValueDiscount);
        txtAdStatusDiscount = findViewById(R.id.txtAdStatusDiscount);

        btnAdd = findViewById(R.id.btnAdAdd);
        btnDelete = findViewById(R.id.btnBackToDashboard);
        btnUpdate = findViewById(R.id.btnAdUpdate);

        Discount discount = (Discount) getIntent().getSerializableExtra("discount");

        if (discount != null) {
            txtAdIdDiscount.setText(discount.getId().toString());
            txtAdIdProductDiscount.setText(discount.getProductId().toString());
            txtAdValueDiscount.setText(discount.getValue()+"");
            txtAdStatusDiscount.setText(discount.getStatus().toString());
        }

    }

    private void handleEvent() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertData();

            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteData();
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateData();
            }
        });
    }

    private boolean validate(String id, String productId, String value) {
        if (id.equals("")) {
            txtAdIdDiscount.setError("Không được bỏ trống");
            return false;
        }
        if (productId.equals("")) {
            txtAdIdProductDiscount.setError("Không được bỏ trống");
            return false;
        }
        if (value.equals("")) {
            txtAdValueDiscount.setError("Không được bỏ trống");
            return false;
        }
        return true;
    }

    private void insertData() {
        String id = txtAdIdDiscount.getText().toString();
        String productId = txtAdIdProductDiscount.getText().toString();
        String status = txtAdStatusDiscount.getText().toString();
        String value = txtAdValueDiscount.getText().toString();

        try {

            if (Integer.valueOf(value) < 0) {
                txtAdValueDiscount.setError("Lỗi");
                return;
            }

            if (!validate(id, productId, value)) {
                Toast.makeText(AdminDiscountActivity.this, "Nhập thông tin sai", Toast.LENGTH_SHORT).show();
                return;
            } else {
                Discount discount = new Discount(parseInt(id), parseInt(productId), parseInt(value), status);
                DiscountDBHelper discountDbHelper = new DiscountDBHelper(this);
                long rowId = discountDbHelper.insert(discount);
                if (rowId > 0) {
                    Toast.makeText(this, " Thành công !", Toast.LENGTH_SHORT).show();
                    txtAdIdDiscount.setText("");
                    txtAdIdProductDiscount.setText("");
                    txtAdStatusDiscount.setText("");
                    txtAdValueDiscount.setText("");
                } else
                    Toast.makeText(this, "Đã xảy ra lỗi .", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Sai định dạng", Toast.LENGTH_SHORT).show();
        }
    }


    private void updateData() {
        String id = txtAdIdDiscount.getText().toString();
        String productId = txtAdIdProductDiscount.getText().toString();
        String status = txtAdStatusDiscount.getText().toString();
        String value = txtAdValueDiscount.getText().toString();
        try {
            if (Integer.valueOf(value) < 0) {
                txtAdValueDiscount.setError("Lỗi");
                return;
            }
            if (!validate(id, productId, value)) {
                Toast.makeText(AdminDiscountActivity.this, "Nhập thông tin sai", Toast.LENGTH_SHORT).show();
                return;
            }

            Discount discount = new Discount(parseInt(id), parseInt(productId), parseInt(value), status);
            DiscountDBHelper discountDbHelper = new DiscountDBHelper(this);

            if (discountDbHelper.getDiscountById(parseInt(id)) == null) {
                Toast.makeText(AdminDiscountActivity.this, "Không tìm thấy", Toast.LENGTH_SHORT).show();
                return;
            }

            long rowId = discountDbHelper.update(discount);
            if (rowId > 0) {
                Toast.makeText(this, " Thành công !", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AdminDiscountActivity.this, ShowAdDiscountActivity.class));
                finish();
            } else
                Toast.makeText(this, "Đã xảy ra lỗi .", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Sai định dạng .", Toast.LENGTH_SHORT).show();
        }

    }


    private void deleteData() {
        String id = txtAdIdDiscount.getText().toString();


        if (id.equals("")) {
            Toast.makeText(AdminDiscountActivity.this, "Nhập thông tin sai", Toast.LENGTH_SHORT).show();
            return;
        }

        DiscountDBHelper discountDbHelper = new DiscountDBHelper(this);
        Discount discount = discountDbHelper.getDiscountById(parseInt(id));
        if (discount == null) {
            Toast.makeText(AdminDiscountActivity.this, "Không tìm thấy", Toast.LENGTH_SHORT).show();
            return;
        }

        long rowId = discountDbHelper.delete(discount);
        if (rowId > 0) {
            Toast.makeText(this, " Thành công !", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(AdminDiscountActivity.this, ShowAdDiscountActivity.class));
            finish();
        } else
            Toast.makeText(this, "Đã xảy ra lỗi .", Toast.LENGTH_SHORT).show();
    }
}
