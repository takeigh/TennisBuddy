package com.example.tennisbuddy.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.tennisbuddy.EditProfile;
import com.example.tennisbuddy.R;
import com.example.tennisbuddy.daos.UserDao;
import com.example.tennisbuddy.databases.KeepUserDatabase;
import com.example.tennisbuddy.databases.UserDatabase;
import com.example.tennisbuddy.entities.User;

import static android.content.Context.MODE_PRIVATE;

public class FragmentViewProfile extends Fragment {

    Button logOut;
    Button editProfile;
    TextView NameTextView;
    TextView skillLevelTextView;
    TextView emailTextView;

    public FragmentViewProfile() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // Retrieve the userID from SharedPreferences
            SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("shared_prefs", MODE_PRIVATE);
            int userID = sharedPreferences.getInt("userID", 0); // 0 is the default value

            // Get the current user's data
            UserDatabase userDatabase = UserDatabase.getDatabase(getContext());
            UserDao userDao = userDatabase.userDao();
            User currentUser = userDao.getUserByID(userID);

            // Set the user's data
            String name = currentUser.getFirstName() + " " + currentUser.getLastName();
            NameTextView.setText(name);
            skillLevelTextView.setText(currentUser.getExperienceLevel());
            emailTextView.setText(currentUser.getEmail());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_profile, container, false);

        NameTextView = view.findViewById(R.id.nameTextView);
        skillLevelTextView = view.findViewById(R.id.skillLevelTextView);
        emailTextView = view.findViewById(R.id.email);
        logOut = view.findViewById(R.id.buttonLogOut);
        editProfile = view.findViewById(R.id.buttonEditProfile);

        logOut.setOnClickListener(l -> {
            KeepUserDatabase.getDatabase(requireContext()).keepUserDao().deleteUsers();
            requireActivity().finish();
        });

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a new instance of the EditProfile fragment
                EditProfile editProfileFragment = new EditProfile();

                // Replace the current fragment with the EditProfile fragment
                if (getActivity() != null) {
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragmentContainerMenu, editProfileFragment)
                            .addToBackStack(null)
                            .commit();
                }
            }
        });

        return view;
    }
}