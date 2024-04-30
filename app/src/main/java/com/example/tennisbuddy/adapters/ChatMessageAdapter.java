package com.example.tennisbuddy.adapters;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tennisbuddy.R;
import com.example.tennisbuddy.entities.Chat;

import java.util.List;

public class ChatMessageAdapter extends RecyclerView.Adapter<ChatMessageAdapter.MyChatHolder> {
    List<Chat> chatList;
    int userId;

    public ChatMessageAdapter(List<Chat> chatList, int userId) {
        this.chatList = chatList;
        this.userId = userId;
    }

    @NonNull
    @Override
    public MyChatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        if (viewType == 1) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_message_right, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_message_left, parent, false);
        }

        return new MyChatHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyChatHolder holder, int position) {
        Chat c = chatList.get(position);

        holder.bind(c);
    }

    @Override
    public int getItemViewType(int position) {
        Chat c = chatList.get(position);

        if (c.getSenderId() == userId) {
            return 1;
        } else {
            return -1;
        }
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public static class MyChatHolder extends RecyclerView.ViewHolder {
        TextView message;
        public MyChatHolder(@NonNull View itemView) {
            super(itemView);

            message = itemView.findViewById(R.id.textViewChatMessage);
        }

        public void bind(Chat c) {
            message.setText(c.getMessage());
        }
    }
}
