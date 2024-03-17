package com.example.myapplication.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;
import com.example.myapplication.model.Product;

import java.util.List;


public class ProductAdapter extends ArrayAdapter<Product> {
    Activity context;
    List<Product> arrayListProduct;
    int layoutResource;

    public ProductAdapter(Activity context, int resource, List<Product> listProduct) {
        super(context, resource, listProduct);
        this.context = context;
        this.arrayListProduct = listProduct;
        this.layoutResource = resource;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;

        if (row == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            row = inflater.inflate(layoutResource, parent, false);
            holder = new ViewHolder();
            holder.productId = row.findViewById(R.id.txt_row_show_idProduct);
            holder.name = row.findViewById(R.id.txt_row_show_nameProduct);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        holder.productId.setText(String.valueOf(arrayListProduct.get(position).getId()));
        holder.name.setText(arrayListProduct.get(position).getName());
        return row;
    }

    static class ViewHolder {
        TextView productId;
        TextView name;
    }
}

//public class ProductAdapter extends BaseAdapter {
//    private List<Product> productList;
//    private LayoutInflater inflater;
//    private Context context;
//
//    public ProductAdapter(Context context, int items_ad_product, List<Product> productList) {
//        inflater = LayoutInflater.from(context);
//        this.productList = productList;
//        this.context = context;
//    }
//
//
//    public LayoutInflater getInflater() {
//        return inflater;
//    }
//
//    public void setInflater(LayoutInflater inflater) {
//        this.inflater = inflater;
//    }
//
//    public List<Product> getProductList() {
//        return productList;
//    }
//
//    public void setProductList(List<Product> productList) {
//        this.productList = productList;
//    }
//
//    @Override
//    public int getCount() {
//        return productList == null ? 0 : productList.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return productList.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return productList.get(position).getId();
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//
//        LayoutInflater layoutInflater = this.context.layoutResource();//build layout thanh java de android su dung
//        View row = layoutInflater.inflate(this.layoutResource, null);
//
//        TextView discountId = row.findViewById(R.id.txt_row_show_idDiscount);
//        discountId.setText(arrayListProduct.get(position).getId().toString());
//        TextView productId = row.findViewById(R.id.txt_row_show_productIdDiscount);
//        productId.setText(arrayListDiscount.get(position).getProductId().toString());
//        TextView value = row.findViewById(R.id.txt_row_show_valueDiscount);
//        value.setText(arrayListDiscount.get(position).getValue() + "");
//
//        return row;
//
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
//        nameTextView.setText(product.getName());
//        priceTextView.setText(String.valueOf(product.getPrice()));
//
//        // Hiển thị hình ảnh từ mảng byte (BLOB)
//        byte[] imageByteArray = product.getImage();
//        Bitmap bitmap = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);
//        imageView.setImageBitmap(bitmap);
//    }
//}
