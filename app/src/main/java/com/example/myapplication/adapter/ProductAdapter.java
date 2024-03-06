package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.myapplication.model.Product;

import java.util.List;

public class ProductAdapter extends BaseAdapter {
    private List<Product> productList;
    private LayoutInflater inflater;
    private Context context;

    public ProductAdapter(Context context, List<Product> productList) {
        inflater = LayoutInflater.from(context);
        this.productList = productList;
        this.context = context;
    }



    public LayoutInflater getInflater() {
        return inflater;
    }

    public void setInflater(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    @Override
    public int getCount() {
        return productList == null ? 0 : productList.size();
    }

    @Override
    public Object getItem(int position) {
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return productList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        View gridView;
//        if (convertView == null) {
//            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            gridView = inflater.inflate(R.layout.items_product_home, null);
//        } else {
//            gridView = convertView;
//        }
//
//        // Lấy ra sản phẩm tại vị trí position
//        Product product = productList.get(position);
//
//        // Ánh xạ dữ liệu sản phẩm vào các thành phần của layout grid_item_layout.xml
//        ImageView imageView = gridView.findViewById(R.id.hinhSP);
//        TextView nameTextView = gridView.findViewById(R.id.txtTenSP);
//        TextView priceTextView = gridView.findViewById(R.id.txtGiaSP);
//
//        // Đặt tên và giá sản phẩm
//
//
//
//
//        nameTextView.setText(product.getName());
//        priceTextView.setText(String.valueOf(product.getPrice()));
//
//        // Hiển thị hình ảnh từ mảng byte (BLOB)
//        byte[] imageByteArray = product.getImage();
//        Bitmap bitmap = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);
//        imageView.setImageBitmap(bitmap);

        return null;
    }

}