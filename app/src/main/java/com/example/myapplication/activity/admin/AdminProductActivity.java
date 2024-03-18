package com.example.myapplication.activity.admin;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.dbhelper.ProductDBHelper;
import com.example.myapplication.model.Product;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AdminProductActivity extends AppCompatActivity {

    EditText edtMaSP, edtTenSP, edtLoaiSP, edtGiaSP, edtMoTaSP, edtHinhAnhSP;
    Button btnThemSP, btnSuaSP, btnXoaSP;
    ImageView btnBack, hinhSP;
    ImageButton addHinh;
    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_product);
        addControls();
        handleEvent();
        addHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImageFromPhone();
            }
        });
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

    private void chooseImageFromPhone() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        Uri uri = Uri.parse(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getPath());
        intent.setDataAndType(uri, "image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                // Convert Uri to Bitmap
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                hinhSP.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
                // Chuyển đổi hình ảnh thành mảng byte
                byte[] imageBytes = convertImageToByteArray();

                Product product = new Product(parseInt(id), name, parseInt(type), parseInt(price), imageBytes, detail);
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

    // Phương thức để chuyển đổi hình ảnh thành mảng byte
    private byte[] convertImageToByteArray() {
        Bitmap bitmap = ((BitmapDrawable) hinhSP.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
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
            // Chuyển đổi hình ảnh thành mảng byte
            byte[] imageBytes = convertImageToByteArray();

            Product product = new Product(parseInt(id), name, parseInt(type), parseInt(price), imageBytes, detail);
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

    @SuppressLint("SetTextI18n")
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

        hinhSP = findViewById(R.id.hinhSP);
        addHinh = findViewById(R.id.addHinh);

        Product product = (Product) getIntent().getSerializableExtra("product");

        if (product != null) {
            edtMaSP.setText(product.getId().toString() + "");
            edtTenSP.setText(product.getName().toString());
            edtLoaiSP.setText(product.getType().toString() + "");
            edtMoTaSP.setText(product.getDetail().toString());
            edtGiaSP.setText(product.getPrice() + "");
            edtHinhAnhSP.setText(product.getImage1().toString());
        }
    }
}