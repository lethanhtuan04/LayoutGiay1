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
import com.example.myapplication.model.User;

import java.util.ArrayList;
import java.util.List;


public  class UserAdapter extends ArrayAdapter<User> {
        Activity context;
        List<User> users;
        int layoutResource;

        public UserAdapter(Activity context, int resource, List<User> objects) {
            super(context, resource, objects);
            this.context = context;
            this.users = objects;
            this.layoutResource = resource;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater=this.context.getLayoutInflater();//build layout thanh java de android su dung
            View row=layoutInflater.inflate(this.layoutResource,null);

            TextView userid = row.findViewById(R.id.txt_iduser);
            userid.setText(users.get(position).getId().toString());
            TextView name = row.findViewById(R.id.txt_nameuser);
            name.setText(users.get(position).getFullname().toString());

            return row;
        }
//        @NonNull
//        public View View (int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//            LayoutInflater layoutInflater = this.context.getLayoutInflater();
//            View row = layoutInflater.inflate(this.layoutResource, null);
//
//            TextView userId = row.findViewById(R.id.txt_iduser);
//            userId.setText(users.get(position).getId().toString());
//            TextView userName = row.findViewById(R.id.txt_nameuser);
//            userName.setText(users.get(position).getFullname());
//            return row;
//        }
    }

