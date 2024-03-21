package com.example.myapplication.activity.admin;//AdminProduct

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

    EditText edtMaSP, edtTenSP, edtLoaiSP, edtGiaSP, edtMoTaSP;
    Button btnThemSP, btnSuaSP, btnXoaSP;
    ImageView btnBack, addHinh1, addHinh2, addHinh3, addHinh4;

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int PICK_IMAGE_REQUEST2 = 2;
    private static final int PICK_IMAGE_REQUEST3 = 3;
    private static final int PICK_IMAGE_REQUEST4 = 4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_product);
        addControls();
        handleEvent();
        addHinh1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImageFromPhone();
            }
        });
        addHinh2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImageFromPhone2();
            }
        });
        addHinh3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImageFromPhone3();
            }
        });
        addHinh4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImageFromPhone4();
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

    private void chooseImageFromPhone2() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        Uri uri = Uri.parse(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getPath());
        intent.setDataAndType(uri, "image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST2);
    }

    private void chooseImageFromPhone3() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        Uri uri = Uri.parse(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getPath());
        intent.setDataAndType(uri, "image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST3);
    }

    private void chooseImageFromPhone4() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        Uri uri = Uri.parse(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getPath());
        intent.setDataAndType(uri, "image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST4);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                // Convert Uri to Bitmap
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                // Set the Bitmap to the appropriate ImageView based on the request code
                switch (requestCode) {
                    case PICK_IMAGE_REQUEST:
                        addHinh1.setImageBitmap(bitmap);
                        break;
                    case PICK_IMAGE_REQUEST2:
                        addHinh2.setImageBitmap(bitmap);
                        break;
                    case PICK_IMAGE_REQUEST3:
                        addHinh3.setImageBitmap(bitmap);
                        break;
                    case PICK_IMAGE_REQUEST4:
                        addHinh4.setImageBitmap(bitmap);
                        break;
                    // Add cases for other ImageViews if needed
                }
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
                byte[] imageBytes2 = convertImageToByteArray2();
                byte[] imageBytes3 = convertImageToByteArray3();
                byte[] imageBytes4 = convertImageToByteArray4();

                Product product = new Product(parseInt(id), name, parseInt(type), parseInt(price), imageBytes, imageBytes2, imageBytes3, imageBytes4, detail);
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
        Bitmap bitmap1 = ((BitmapDrawable) addHinh1.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap1.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    private byte[] convertImageToByteArray2() {
        Bitmap bitmap2 = ((BitmapDrawable) addHinh2.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap2.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    private byte[] convertImageToByteArray3() {
        Bitmap bitmap3 = ((BitmapDrawable) addHinh3.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap3.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    private byte[] convertImageToByteArray4() {
        Bitmap bitmap4 = ((BitmapDrawable) addHinh4.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap4.compress(Bitmap.CompressFormat.PNG, 100, stream);
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
            byte[] imageBytes2 = convertImageToByteArray2();
            byte[] imageBytes3 = convertImageToByteArray3();
            byte[] imageBytes4 = convertImageToByteArray4();

            Product product = new Product(parseInt(id), name, parseInt(type), parseInt(price), imageBytes, imageBytes2, imageBytes3, imageBytes4, detail);
            ProductDBHelper productDbHelper = new ProductDBHelper(this);
            int numRowsAffected = productDbHelper.update(product);

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

        btnThemSP = findViewById(R.id.btnThemSP1);
        btnXoaSP = findViewById(R.id.btnXoaSP);
        btnSuaSP = findViewById(R.id.btnSuaSP);

        addHinh1 = findViewById(R.id.addHinh10);
        addHinh2 = findViewById(R.id.addHinh2);
        addHinh3 = findViewById(R.id.addHinh3);
        addHinh4 = findViewById(R.id.addHinh4);


        int IDproduct = (int) getIntent().getSerializableExtra("idpro");
        Product product;
        ProductDBHelper productDBHelper = new ProductDBHelper(this);
        product = productDBHelper.getProductById(IDproduct);

        if (product != null) {
            edtMaSP.setText(product.getId().toString() + "");
            edtTenSP.setText(product.getName().toString());
            edtLoaiSP.setText(product.getType().toString() + "");
            edtMoTaSP.setText(product.getDetail().toString());
            edtGiaSP.setText(product.getPrice() + "");

            byte[] imageByteArray = product.getImage1();
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);
            addHinh1.setImageBitmap(bitmap);

            byte[] imageByteArray2 = product.getImage2();
            Bitmap bitmap2 = BitmapFactory.decodeByteArray(imageByteArray2, 0, imageByteArray2.length);
            addHinh2.setImageBitmap(bitmap2);

            byte[] imageByteArray3 = product.getImage3();
            Bitmap bitmap3 = BitmapFactory.decodeByteArray(imageByteArray3, 0, imageByteArray3.length);
            addHinh3.setImageBitmap(bitmap3);

            byte[] imageByteArray4 = product.getImage4();
            Bitmap bitmap4 = BitmapFactory.decodeByteArray(imageByteArray4, 0, imageByteArray4.length);
            addHinh4.setImageBitmap(bitmap4);
        }

    }
}