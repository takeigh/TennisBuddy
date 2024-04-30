package com.example.tennisbuddy.fragments;

import android.graphics.Rect;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.tennisbuddy.R;
import com.example.tennisbuddy.adapters.ChatMessageAdapter;
import com.example.tennisbuddy.databases.ChatDatabase;
import com.example.tennisbuddy.databases.KeepUserDatabase;
import com.example.tennisbuddy.databases.UserDatabase;
import com.example.tennisbuddy.entities.Chat;
import com.example.tennisbuddy.entities.KeepUser;
import com.example.tennisbuddy.entities.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class FragmentChatMessages extends Fragment {

    Button back;
    Button send;
    TextView name;
    EditText messageBox;
    RecyclerView messages;
    int receiverId;

    public FragmentChatMessages() {
        // Required empty public constructor
    }

    public static FragmentChatMessages newInstance(int receiverId) {
        FragmentChatMessages fragment = new FragmentChatMessages();
        Bundle args = new Bundle();
        args.putInt("receiver", receiverId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            receiverId = getArguments().getInt("receiver");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_messages, container, false);

        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                view.getWindowVisibleDisplayFrame(r);

                int screenHeight = view.getHeight();
                int keyboardHeight = screenHeight - r.bottom;

                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();

                if (keyboardHeight > screenHeight * 0.15) {
                    layoutParams.height = screenHeight - keyboardHeight;
                } else {
                    layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
                }

                view.setLayoutParams(layoutParams);
            }
        });

        prepComponents(view);

        return view;
    }

    private void prepComponents(View view) {
        back = view.findViewById(R.id.buttonBackChat);
        back.setOnClickListener(l -> requireActivity().getSupportFragmentManager().popBackStack());

        send = view.findViewById(R.id.buttonSend);
        send.setOnClickListener(l -> sendMessage());

        name = view.findViewById(R.id.textViewChatName);
        User partner = UserDatabase.getDatabase(requireContext()).userDao().getUserByID(receiverId);
        String partnerName = partner.getFirstName() + " " + partner.getLastName();
        name.setText(partnerName);

        messageBox = view.findViewById(R.id.editTextMessageBox);

        messages = view.findViewById(R.id.recyclerViewChatMessages);
        updateMessages();
    }

    private void sendMessage() {
        KeepUser user = KeepUserDatabase.getDatabase(requireContext()).keepUserDao().getKeepUser();
        LocalDateTime date = LocalDateTime.now();

        int month = date.getMonth().getValue();
        int day = date.getDayOfMonth();
        int hour = date.getHour();
        int minute = date.getMinute();
        int second = date.getSecond();

        Chat chat = new Chat();
        chat.setSenderId(user.getUserId());
        chat.setReceiverId(receiverId);
        chat.setMessage(messageBox.getText().toString());
        chat.setMonth(month);
        chat.setDay(day);
        chat.setHour(hour);
        chat.setMinute(minute);
        chat.setSecond(second);

        ChatDatabase.getDatabase(requireContext()).chatDao().addChat(chat);

        updateMessages();
    }

    private void updateMessages() {
        List<Chat> chats = getChats();
        KeepUser user = KeepUserDatabase.getDatabase(requireContext()).keepUserDao().getKeepUser();

        ChatMessageAdapter adapter = new ChatMessageAdapter(chats, user.getUserId());
        messages.setAdapter(adapter);
    }

    private List<Chat> getChats() {
        KeepUser user = KeepUserDatabase.getDatabase(requireContext()).keepUserDao().getKeepUser();
        List<Chat> chats = ChatDatabase.getDatabase(requireContext()).chatDao().getChats();

        List<Chat> personalList = new ArrayList<>();

        if (chats.size() != 0) {
            for (Chat c : chats) {
                if (c.getSenderId() == user.getUserId()) {
                    personalList.add(c);
                } else if (c.getReceiverId() == user.getUserId()) {
                    personalList.add(c);
                }
            }
        }

        return personalList;
    }
}