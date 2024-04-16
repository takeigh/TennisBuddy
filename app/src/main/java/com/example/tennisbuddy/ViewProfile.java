package com.example.tennisbuddy;

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

import com.example.tennisbuddy.daos.UserDao;
import com.example.tennisbuddy.databases.UserDatabase;
import com.example.tennisbuddy.entities.User;
import com.example.tennisbuddy.daos.UserDao;
import com.example.tennisbuddy.databases.UserDatabase;
import com.example.tennisbuddy.entities.User;
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewProfile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewProfile extends Fragment {
    private TextView firstNameTextView;
    private TextView lastNameTextView;
    private TextView skillLevelTextView;
    private TextView emailTextView;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ViewProfile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewProfile.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewProfile newInstance(String param1, String param2) {
        ViewProfile fragment = new ViewProfile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_profile, container, false);

        firstNameTextView = view.findViewById(R.id.firstNameTextView);
        lastNameTextView = view.findViewById(R.id.lastNameTextView);
        skillLevelTextView = view.findViewById(R.id.skillLevelTextView);
        emailTextView = view.findViewById(R.id.email);

        // Rest of your code...

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        UserDatabase userDatabase = UserDatabase.getDatabase(getContext());
        UserDao userDao = userDatabase.userDao();
        User user = userDao.getUserByID(1); // Replace 1 with the actual user ID

        firstNameTextView.setText(user.getFirstName());
        lastNameTextView.setText(user.getLastName());
        // skillLevelTextView.setText(user.getSkillLevel()); // Uncomment this line once you add a getSkillLevel() method in your User class
        emailTextView.setText(user.getEmail());
    }

    private void addFriend() {
        // add the friend to the current user's friend list.
        Toast.makeText(getContext(), "Friend added!", Toast.LENGTH_SHORT).show();
    }
}