package com.example.tennisbuddy;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tennisbuddy.databases.UserDatabase;
import com.example.tennisbuddy.daos.UserDao;
import com.example.tennisbuddy.databases.UserDatabase;
import com.example.tennisbuddy.entities.User;

public class EditProfile extends Fragment {

    private User currentUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // Retrieve the userID from SharedPreferences
            SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("MySharedPref", MODE_PRIVATE);
            int userID = sharedPreferences.getInt("userID", 0); // 0 is the default value

            // Get the current user's data
            UserDatabase userDatabase = UserDatabase.getDatabase(getContext());
            UserDao userDao = userDatabase.userDao();
            currentUser = userDao.getUserByID(userID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        EditText editFirstName = view.findViewById(R.id.editFirstName);
        EditText editLastName = view.findViewById(R.id.editLastName);
        Spinner spinnerSkillLevel = view.findViewById(R.id.spinnerSkillLevel);
        Button buttonSave = view.findViewById(R.id.buttonSave);

        // Show the user's data in the form
        editFirstName.setText(currentUser.getFirstName());
        editLastName.setText(currentUser.getLastName());

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = editFirstName.getText().toString();
                String lastName = editLastName.getText().toString();

                // Update the current user's data with the new data
                currentUser.setFirstName(firstName);
                currentUser.setLastName(lastName);

                // Update the user's data in the database
                UserDatabase userDatabase = UserDatabase.getDatabase(getContext());
                UserDao userDao = userDatabase.userDao();
                userDao.updateUser(currentUser);

                Toast.makeText(getContext(), "Profile saved!", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}