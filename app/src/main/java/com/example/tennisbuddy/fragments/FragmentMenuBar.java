package com.example.tennisbuddy.fragments;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.tennisbuddy.R;
import com.example.tennisbuddy.activities.LandingActivity;
import com.example.tennisbuddy.databases.KeepUserDatabase;
import com.example.tennisbuddy.entities.KeepUser;

public class FragmentMenuBar extends Fragment {
    ImageButton browse;
    ImageButton chat;
    ImageButton profile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu_bar, container, false);

        prepComponents(view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void prepComponents(View view) {
        browse = view.findViewById(R.id.btnBrowse);
        browse.setOnClickListener(l -> {
            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainerMain, new FragmentBrowseMatches());
            transaction.commit();
        });

        chat = view.findViewById(R.id.btnChat);
        chat.setOnClickListener(l -> {
            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainerMain, new FragmentChatList());
            transaction.commit();
        });

        KeepUser user = KeepUserDatabase.getDatabase(requireContext()).keepUserDao().getKeepUser();

        profile = view.findViewById(R.id.btnProfile);
        profile.setOnClickListener(l -> {
            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainerMain, FragmentViewProfile.newInstance(user.getUserId()));
            transaction.commit();
        });
    }
}
