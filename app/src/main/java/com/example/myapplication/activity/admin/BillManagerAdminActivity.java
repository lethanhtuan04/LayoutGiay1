package com.example.myapplication.activity.admin;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.adapter.ManagerBill;
import com.example.myapplication.dbhelper.BillDBHelper;
import com.example.myapplication.dbhelper.CartDBHelper;
import com.example.myapplication.dbhelper.NotificationDBHelper;
import com.example.myapplication.model.Bill;
import com.example.myapplication.model.Cart;
import com.example.myapplication.model.Notification;

import java.util.List;

public class BillManagerAdminActivity extends AppCompatActivity {
    List<Bill> bills;
    ListView listView;
    BillDBHelper billDBHelper;
    Bill bill;
    ManagerBill managerBill;
    NotificationDBHelper notificationDBHelper;

    TextView allBill, billWait, billShipping, billDaNhan, billCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_manager_admin);
        listView = findViewById(R.id.listviewBill);
        allBill = findViewById(R.id.allBill);
        billWait = findViewById(R.id.billWait);
        billShipping = findViewById(R.id.billShipping);
        billDaNhan = findViewById(R.id.billDaNhan);
        billCancel = findViewById(R.id.billCancel);

        allBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                billDBHelper = new BillDBHelper(BillManagerAdminActivity.this);
                bills = billDBHelper.getAllBills();
                // Khởi tạo Adapter
                managerBill = new ManagerBill(BillManagerAdminActivity.this, bills);
                // Gán Adapter cho ListView
                listView.setAdapter(managerBill);
            }
        });

        billWait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                billDBHelper = new BillDBHelper(BillManagerAdminActivity.this);
                bills = billDBHelper.getBillsByStatus(Bill.BILL_WAIT);
                // Khởi tạo Adapter
                managerBill = new ManagerBill(BillManagerAdminActivity.this, bills);
                // Gán Adapter cho ListView
                listView.setAdapter(managerBill);
            }
        });
        billShipping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                billDBHelper = new BillDBHelper(BillManagerAdminActivity.this);
                bills = billDBHelper.getBillsByStatus(Bill.BILL_SHIPPING);
                // Khởi tạo Adapter
                managerBill = new ManagerBill(BillManagerAdminActivity.this, bills);
                // Gán Adapter cho ListView
                listView.setAdapter(managerBill);
            }
        });
        billDaNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                billDBHelper = new BillDBHelper(BillManagerAdminActivity.this);
                bills = billDBHelper.getBillsByStatus(Bill.BILL_RECEIVED);
                // Khởi tạo Adapter
                managerBill = new ManagerBill(BillManagerAdminActivity.this, bills);
                // Gán Adapter cho ListView
                listView.setAdapter(managerBill);
            }
        });
        billCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                billDBHelper = new BillDBHelper(BillManagerAdminActivity.this);
                bills = billDBHelper.getBillsByStatus(Bill.BILL_CANCELED);
                // Khởi tạo Adapter
                managerBill = new ManagerBill(BillManagerAdminActivity.this, bills);
                // Gán Adapter cho ListView
                listView.setAdapter(managerBill);
            }
        });

        billDBHelper = new BillDBHelper(this);
        bills = billDBHelper.getAllBills();

        // Khởi tạo Adapter
        managerBill = new ManagerBill(this, bills);
        notificationDBHelper = new NotificationDBHelper(this);

        // Gọi phương thức để tạo kênh thông báo
        createNotificationChannel();
        // Gán Adapter cho ListView
        listView.setAdapter(managerBill);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(BillManagerAdminActivity.this);
                builder.setTitle("Xác nhận đơn hàng hoặc Hủy đơn hàng?");
                builder.setItems(new CharSequence[]{"Xác nhận đơn hàng", "Hủy đơn hàng", "Đã nhận hàng"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                // Xác nhận đơn hàng
                                confirmOrder(position);
                                break;
                            case 1:
                                // Hủy đơn hàng
                                cancelOrder(position);
                                bill = bills.get(position);

                                CartDBHelper cartDBHelper = new CartDBHelper(BillManagerAdminActivity.this);
                                Cart cart = cartDBHelper.getCartById(bill.getCartId());
                                byte[] img = cart.getProduct().getImage1();
                                String tenPro = cart.getProduct().getName();
                                Notification notification = new Notification(bill.getAccId(), Notification.NOTIFY_CANCEL_ORDER_PRODUCT,
                                        "Đơn hàng " + tenPro + " đã bị hủy. Xin lỗi vì sự bất tiện này", img);
                                NotificationDBHelper notificationDBHelper = new NotificationDBHelper(BillManagerAdminActivity.this);
                                notificationDBHelper.insert(notification);
                                showPurchaseSuccessNotification();
                                break;
                            case 2:
                                // Đã nhận hàng
                                receivedOrder(position);
                                break;
                        }
                    }
                });
                builder.create().show();
                return true;
            }
        });
    }

    private void confirmOrder(int position) {
        Bill confirmedBill = bills.get(position);
        int billId = confirmedBill.getId();

        if (billDBHelper.updateBillStatus(billId, Bill.BILL_SHIPPING)) {
            bills.get(position).setStatus(Bill.BILL_SHIPPING);
            managerBill.notifyDataSetChanged(); // Cập nhật giao diện
        } else {

            Toast.makeText(this, "Đã xảy ra lỗi khi xác nhận đơn hàng", Toast.LENGTH_SHORT).show();
        }
    }

    private void cancelOrder(int position) {
        Bill confirmedBill = bills.get(position);
        int billId = confirmedBill.getId();

        if (billDBHelper.updateBillStatus(billId, Bill.BILL_CANCELED)) {
            bills.get(position).setStatus(Bill.BILL_CANCELED);
            managerBill.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "Đã xảy ra lỗi khi xác nhận đơn hàng", Toast.LENGTH_SHORT).show();
        }
    }

    private void receivedOrder(int position) {
        Bill receivedBill = bills.get(position);
        int billId = receivedBill.getId();
        if (billDBHelper.updateBillStatus(billId, Bill.BILL_RECEIVED)) {
            bills.get(position).setStatus(Bill.BILL_RECEIVED);
            managerBill.notifyDataSetChanged(); // Cập nhật giao diện
        } else {

            Toast.makeText(this, "Đã xảy ra lỗi khi xác nhận đã nhận hàng", Toast.LENGTH_SHORT).show();
        }
    }

    private void showPurchaseSuccessNotification() {
        // Gọi hàm hiển thị thông báo từ lớp NotificationHelper
        NotificationDBHelper.showNotification(this, "Đơn hàng đã bị hủy", "Xin lỗi vì đơn hàng đã bị hủy. Bạn có thể lựa chọn các mặt hàng khác tại shop chúng tôi");
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.titleCancel);
            String description = getString(R.string.contentCancel);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("Your_Channel_ID", name, importance);
            channel.setDescription(description);
            // Đăng ký kênh với hệ thống
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}

