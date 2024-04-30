package com.example.tennisbuddy.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.tennisbuddy.FragmentNewChat;
import com.example.tennisbuddy.R;
import com.example.tennisbuddy.adapters.ChatListAdapater;
import com.example.tennisbuddy.daos.ChatDao;
import com.example.tennisbuddy.databases.ChatDatabase;
import com.example.tennisbuddy.databases.KeepUserDatabase;
import com.example.tennisbuddy.databases.UserDatabase;
import com.example.tennisbuddy.entities.Chat;
import com.example.tennisbuddy.entities.KeepUser;
import com.example.tennisbuddy.entities.User;

import java.util.ArrayList;
import java.util.List;

public class FragmentChatList extends Fragment {

    Button newChat;
    ListView chatList;

    public FragmentChatList() {
        // Required empty public constructor
    }

    public static FragmentChatList newInstance(String param1, String param2) {
        FragmentChatList fragment = new FragmentChatList();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_list, container, false);

        prepComponents(view);

        return view;
    }

    private void prepComponents(View view) {
        newChat = view.findViewById(R.id.buttonNewChat);
        newChat.setOnClickListener(l -> {
            FragmentNewChat fragment = new FragmentNewChat();
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerMain, fragment)
                    .addToBackStack(null)
                    .commit();
        });

        List<Chat> personalChats = getChats();
        List<User> users = getUsers(personalChats);

        chatList = view.findViewById(R.id.list_view_chat_list);
        ChatListAdapater adapter = new ChatListAdapater(users, requireContext());
        chatList.setAdapter(adapter);
        chatList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentChatMessages frag = FragmentChatMessages.newInstance(users.get(position).getUserId());

                FragmentManager fragManager = getParentFragmentManager();
                fragManager.beginTransaction()
                        .replace(R.id.fragmentContainerMain, frag)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    private List<Chat> getChats() {
        KeepUser user = KeepUserDatabase.getDatabase(requireContext()).keepUserDao().getKeepUser();
        List<Chat> chats = ChatDatabase.getDatabase(requireContext()).chatDao().getChats();

        if (chats.size() == 0) {
            Chat newChat = new Chat();
            newChat.setSenderId(9);
            newChat.setReceiverId(11);
            newChat.setMessage("Hello");

            ChatDatabase.getDatabase(requireContext()).chatDao().addChat(newChat);
        }

        List<Chat> personalList = new ArrayList<>();

        for (Chat c : chats) {
            if (c.getSenderId() == user.getUserId()) {
                if (personalList.size() > 0) {
                    for (Chat c2 : personalList) {
                        if (c.getReceiverId() != c2.getSenderId() && c.getReceiverId() != c2.getReceiverId()) {
                            personalList.add(c);
                        }
                    }
                } else {
                    personalList.add(c);
                }
            } else if (c.getReceiverId() == user.getUserId()) {
                if (personalList.size() > 0) {
                    for (Chat c2 : personalList) {
                        if (c.getSenderId() != c2.getSenderId() && c.getSenderId() != c2.getReceiverId()) {
                            personalList.add(c);
                        }
                    }
                } else {
                    personalList.add(c);
                }
            }
        }

        return personalList;
    }

    private List<User> getUsers(List<Chat> chats) {
        KeepUser user = KeepUserDatabase.getDatabase(requireContext()).keepUserDao().getKeepUser();
        List<User> users = new ArrayList<>();

        for (Chat c : chats) {
            if (c.getSenderId() == user.getUserId()) {
                User partner = UserDatabase.getDatabase(requireContext()).userDao().getUserByID(c.getReceiverId());

                users.add(partner);
            } else {
                User partner = UserDatabase.getDatabase(requireContext()).userDao().getUserByID(c.getSenderId());

                users.add(partner);
            }
        }

        return users;
    }
}