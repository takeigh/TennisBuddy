package com.example.tennisbuddy.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.tennisbuddy.R;
import com.example.tennisbuddy.daos.UserDao;
import com.example.tennisbuddy.databases.KeepUserDatabase;
import com.example.tennisbuddy.databases.UserDatabase;
import com.example.tennisbuddy.entities.KeepUser;
import com.example.tennisbuddy.entities.User;

import static android.content.Context.MODE_PRIVATE;

public class FragmentViewProfile extends Fragment {

    User viewedUser;
    User loggedUser;
    Button logOut;
    Button editProfile;
    Button addFriend;
    TextView name;
    TextView skillLevel;
    TextView email;

    public FragmentViewProfile() {
        // Required empty public constructor
    }

    public static FragmentViewProfile newInstance(int id) {
        FragmentViewProfile fragment = new FragmentViewProfile();
        Bundle args = new Bundle();
        args.putInt("viewedUser", id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            viewedUser = UserDatabase.getDatabase(requireContext()).userDao().getUserByID(getArguments().getInt("viewedUser"));
        }
        KeepUser kU = KeepUserDatabase.getDatabase(requireContext()).keepUserDao().getKeepUser();
        loggedUser = UserDatabase.getDatabase(requireContext()).userDao().getUserByID(kU.getUserId());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_profile, container, false);

        prepComponents(view);

        return view;
    }

    private void prepComponents(View view) {
        name = view.findViewById(R.id.nameTextView);
        String userName = viewedUser.getFirstName() + " " + viewedUser.getLastName();
        name.setText(userName);

        skillLevel = view.findViewById(R.id.skillLevelTextView);
        skillLevel.setText(viewedUser.getExperienceLevel());

        email = view.findViewById(R.id.email);
        if (loggedUser.getUserId() == viewedUser.getUserId()) {
            email.setVisibility(View.VISIBLE);
            email.setText(viewedUser.getEmail());
        } else {
            email.setVisibility(View.GONE);
        }

        logOut = view.findViewById(R.id.buttonLogOut);
        logOut.setOnClickListener(l -> {
            KeepUserDatabase.getDatabase(requireContext()).keepUserDao().deleteUsers();
            requireActivity().finish();
        });

        editProfile = view.findViewById(R.id.buttonEditProfile);
        if (loggedUser.getUserId() == viewedUser.getUserId()) {
            editProfile.setVisibility(View.VISIBLE);
        } else {
            editProfile.setVisibility(View.GONE);
        }
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a new instance of the EditProfile fragment
                FragmentEditProfile editProfileFragment = new FragmentEditProfile();

                // Replace the current fragment with the EditProfile fragment
                if (getActivity() != null) {
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragmentContainerMain, editProfileFragment)
                            .addToBackStack(null)
                            .commit();
                }
            }
        });

        addFriend = view.findViewById(R.id.buttonAddFriend);
        if (loggedUser.getUserId() == viewedUser.getUserId()) {
            addFriend.setVisibility(View.GONE);
        } else {
            addFriend.setVisibility(View.VISIBLE);
            // Need logic to add friend
        }
    }
}