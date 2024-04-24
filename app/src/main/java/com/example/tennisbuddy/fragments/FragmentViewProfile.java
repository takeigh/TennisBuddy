package com.example.tennisbuddy.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tennisbuddy.R;
import com.example.tennisbuddy.activities.LandingActivity;
import com.example.tennisbuddy.activities.LoginActivity;
import com.example.tennisbuddy.daos.UserDao;
import com.example.tennisbuddy.databases.KeepUserDatabase;
import com.example.tennisbuddy.databases.UserDatabase;
import com.example.tennisbuddy.entities.KeepUser;
import com.example.tennisbuddy.entities.User;
import com.example.tennisbuddy.daos.UserDao;
import com.example.tennisbuddy.databases.UserDatabase;
import com.example.tennisbuddy.entities.User;

public class FragmentViewProfile extends Fragment {

    Button logOut;
    Button editProfile;
    TextView firstNameTextView;
    TextView lastNameTextView;
    TextView skillLevelTextView;
    TextView emailTextView;

    public FragmentViewProfile() {
        // Required empty public constructor
    }

    public static FragmentViewProfile newInstance(String param1, String param2) {
        FragmentViewProfile fragment = new FragmentViewProfile();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_profile, container, false);

        prepComponents(view);

        return view;
    }

    private void prepComponents(View view) {
        KeepUser kUser = KeepUserDatabase.getDatabase(requireContext()).keepUserDao().getKeepUser();
        User user = UserDatabase.getDatabase(requireContext()).userDao().getUserByID(kUser.getUserId());

        firstNameTextView = view.findViewById(R.id.firstNameTextView);
        firstNameTextView.setText(user.getFirstName());

        lastNameTextView = view.findViewById(R.id.lastNameTextView);
        lastNameTextView.setText(user.getLastName());

        skillLevelTextView = view.findViewById(R.id.skillLevelTextView);
        skillLevelTextView.setText(user.getExperienceLevel());

        emailTextView = view.findViewById(R.id.email);
        emailTextView.setText(user.getEmail());

        logOut = view.findViewById(R.id.buttonLogOut);
        logOut.setOnClickListener(l -> {
            KeepUserDatabase.getDatabase(requireContext()).keepUserDao().deleteUsers();
        });

        editProfile = view.findViewById(R.id.buttonEditProfile);
    }

    private void addFriend() {
        // add the friend to the current user's friend list.
        Toast.makeText(getContext(), "Friend added!", Toast.LENGTH_SHORT).show();
    }
}