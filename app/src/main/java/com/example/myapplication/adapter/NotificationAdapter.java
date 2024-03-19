package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.dbhelper.NotificationDBHelper;
import com.example.myapplication.model.Notification;

import java.util.List;


public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    //private static final HashMap<String, Integer> mapNotify_Image;
    private static final String NOTIFY_CART = "Cart";
    private static final String NOTIFY_SALE = "Sale";
    private static final String NOTIFY_GUIDE = "Guide";
    private static final String NOTIFY_ACCOUNT = "Account";

    static {
//        mapNotify_Image = new HashMap<>();
//        mapNotify_Image.put(NOTIFY_CART, R.drawable.shopping_cart);
//        mapNotify_Image.put(NOTIFY_SALE, R.drawable.land_sales);
//        mapNotify_Image.put(NOTIFY_GUIDE, R.drawable.guide);
//        mapNotify_Image.put(NOTIFY_ACCOUNT, R.drawable.person);
    }

    private List<Notification> notifications;

    public NotificationAdapter(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.items_notification, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Notification notification = notifications.get(position);
      //  holder.notificationImg.setImageResource(mapNotify_Image.get(notification.getType()).intValue());
        holder.txtNotification.setText(notification.getMessage());
        holder.dismissNotification.setOnClickListener(view -> {
            notification.setStatusRead();
            NotificationDBHelper notificationDbHelper = new NotificationDBHelper(holder.dismissNotification.getContext());
            int result = notificationDbHelper.update(notification);
            if (result > 0) {
                notifications.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, notifications.size());
            }


        });
    }

    @Override
    public int getItemCount() {

        return notifications == null ? 0 : notifications.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView notificationImg;
        TextView txtNotification;
        ImageView dismissNotification;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
//
//            notificationImg = itemView.findViewById(R.id.notify_img);
//            txtNotification = itemView.findViewById(R.id.notify_detail);
//            dismissNotification = itemView.findViewById(R.id.dismissNotification);
        }
    }
}
