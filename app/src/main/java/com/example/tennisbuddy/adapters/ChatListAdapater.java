package com.example.tennisbuddy.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tennisbuddy.R;
import com.example.tennisbuddy.entities.User;

import java.util.List;

public class ChatListAdapater extends BaseAdapter {
    List<User> users;
    Context context;

    public ChatListAdapater(List<User> users, Context context) {
        this.users = users;
        this.context = context;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            listItemView = inflater.inflate(R.layout.chat_list_item, parent, false);
        }

        // Get the data item for this position
        String name = users.get(position).getFirstName() + " " + users.get(position).getLastName();

        // Set the data onto the views
        TextView nameView = listItemView.findViewById(R.id.userName);
        nameView.setText(name);

        return listItemView;
    }
}
