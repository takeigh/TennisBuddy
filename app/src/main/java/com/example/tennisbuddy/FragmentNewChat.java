package com.example.tennisbuddy;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.tennisbuddy.databases.ChatDatabase;
import com.example.tennisbuddy.databases.FriendsDatabase;
import com.example.tennisbuddy.databases.KeepUserDatabase;
import com.example.tennisbuddy.databases.UserDatabase;
import com.example.tennisbuddy.entities.Chat;
import com.example.tennisbuddy.entities.Friends;
import com.example.tennisbuddy.entities.KeepUser;
import com.example.tennisbuddy.entities.User;
import com.example.tennisbuddy.fragments.FragmentChatMessages;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FragmentNewChat extends Fragment {

    Button back;
    Spinner receiver;
    Button send;
    EditText messageBox;
    int receiverId;


    public FragmentNewChat() {
        // Required empty public constructor
    }

    public static FragmentNewChat newInstance() {
        return new FragmentNewChat();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_chat, container, false);

        prepComponents(view);

        return view;
    }

    private void prepComponents(View view) {
        back = view.findViewById(R.id.buttonBackChatNew);
        back.setOnClickListener(l -> {
            getParentFragmentManager().popBackStack();
        });

        send = view.findViewById(R.id.buttonSendNew);
        send.setOnClickListener(l -> sendMessage());

        messageBox = view.findViewById(R.id.editTextMessageBoxNew);

        receiver = view.findViewById(R.id.spinnerReceiverNewChat);
        KeepUser kU = KeepUserDatabase.getDatabase(requireContext()).keepUserDao().getKeepUser();
        List<Friends> friendsList = FriendsDatabase.getDatabase(requireContext()).friendsDao().getFriendsById(kU.getUserId());
        List<User> friends = new ArrayList<>();
        for (Friends f : friendsList) {
            if (f.getUser1Id() == kU.getUserId()) {
                User u = UserDatabase.getDatabase(requireContext()).userDao().getUserByID(f.getUser2Id());
                friends.add(u);
            } else {
                User u = UserDatabase.getDatabase(requireContext()).userDao().getUserByID(f.getUser1Id());
                friends.add(u);
            }
        }

        List<String> friendNames = new ArrayList<>();
        for (User u : friends) {
            friendNames.add(u.getFirstName() + " " + u.getLastName());
        }

        receiver.setAdapter(new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, friendNames));
        receiver.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                receiverId = friends.get(position).getUserId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
        getParentFragmentManager().popBackStack();

        FragmentChatMessages frag = FragmentChatMessages.newInstance(receiverId);
        FragmentManager fragManager = getParentFragmentManager();
        fragManager.beginTransaction()
                .replace(R.id.fragmentContainerMain, frag)
                .addToBackStack(null)
                .commit();
    }
}