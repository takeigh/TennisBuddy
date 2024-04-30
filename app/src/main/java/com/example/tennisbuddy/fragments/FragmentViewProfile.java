package com.example.tennisbuddy.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tennisbuddy.R;
import com.example.tennisbuddy.daos.FriendsDao;
import com.example.tennisbuddy.daos.UserDao;
import com.example.tennisbuddy.databases.FriendsDatabase;
import com.example.tennisbuddy.databases.KeepUserDatabase;
import com.example.tennisbuddy.databases.UserDatabase;
import com.example.tennisbuddy.entities.Friends;
import com.example.tennisbuddy.entities.KeepUser;
import com.example.tennisbuddy.entities.User;

import static android.content.Context.MODE_PRIVATE;

import java.util.List;

public class FragmentViewProfile extends Fragment {

    User viewedUser;
    User loggedUser;
    Button logOut;
    Button editProfile;
    Button addFriend;
    Button back;
    TextView name;
    TextView skillLevel;
    TextView email;
    boolean menuNav;
    LinearLayout emailLayout;

    public FragmentViewProfile() {
        // Required empty public constructor
    }

    public static FragmentViewProfile newInstance(int id, boolean menu) {
        FragmentViewProfile fragment = new FragmentViewProfile();
        Bundle args = new Bundle();
        args.putInt("viewedUser", id);
        args.putBoolean("menuNav", menu);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            viewedUser = UserDatabase.getDatabase(requireContext()).userDao().getUserByID(getArguments().getInt("viewedUser"));
            menuNav = getArguments().getBoolean("menuNav");
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
        email.setText(viewedUser.getEmail());

        logOut = view.findViewById(R.id.buttonLogOut);
        logOut.setOnClickListener(l -> {
            KeepUserDatabase.getDatabase(requireContext()).keepUserDao().deleteUsers();
            requireActivity().finish();
        });

        editProfile = view.findViewById(R.id.buttonEditProfile);
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
        addFriend.setOnClickListener(l -> addFriend());

        emailLayout = view.findViewById(R.id.layoutEmail);

        if (loggedUser.getUserId() == viewedUser.getUserId()) {
            emailLayout.setVisibility(View.VISIBLE);
            addFriend.setVisibility(View.GONE);
        } else {
            emailLayout.setVisibility(View.GONE);
            List<Friends> friendsList = FriendsDatabase.getDatabase(requireContext()).friendsDao().getFriendsById(loggedUser.getUserId());

            addFriend.setVisibility(View.VISIBLE);
            for (Friends f : friendsList) {
                if (f.getUser1Id() == viewedUser.getUserId() || f.getUser2Id() == viewedUser.getUserId()) {
                    addFriend.setVisibility(View.GONE);
                }
            }
        }

        back = view.findViewById(R.id.buttonBackViewProfile);
        back.setOnClickListener(l -> {
            getParentFragmentManager().popBackStack();
        });


        if (!menuNav) {
            back.setVisibility(View.VISIBLE);
            logOut.setVisibility(View.GONE);

            editProfile.setVisibility(View.GONE);
            email.setVisibility(View.GONE);
        } else {
            back.setVisibility(View.GONE);
            logOut.setVisibility(View.VISIBLE);

            if (loggedUser.getUserId() == viewedUser.getUserId()) {
                editProfile.setVisibility(View.VISIBLE);
            } else {
                editProfile.setVisibility(View.GONE);
            }
        }
    }

    private void addFriend() {
        Friends friends = new Friends();
        friends.setUser1Id(loggedUser.getUserId());
        friends.setUser2Id(viewedUser.getUserId());

        FriendsDatabase.getDatabase(requireContext()).friendsDao().addFriends(friends);
        addFriend.setVisibility(View.GONE);
    }
}