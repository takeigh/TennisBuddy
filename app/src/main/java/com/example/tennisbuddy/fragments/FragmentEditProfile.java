package com.example.tennisbuddy.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tennisbuddy.R;
import com.example.tennisbuddy.data.ExperienceLevel;
import com.example.tennisbuddy.databases.KeepUserDatabase;
import com.example.tennisbuddy.databases.UserDatabase;
import com.example.tennisbuddy.daos.UserDao;
import com.example.tennisbuddy.entities.KeepUser;
import com.example.tennisbuddy.entities.User;

public class FragmentEditProfile extends Fragment {

    KeepUser keepUser;
    User user;
    EditText firstName;
    EditText lastName;
    EditText email;
    Spinner skillLevel;
    Button save;
    Button back;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        keepUser = KeepUserDatabase.getDatabase(requireContext()).keepUserDao().getKeepUser();
        user = UserDatabase.getDatabase(requireContext()).userDao().getUserByID(keepUser.getUserId());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        prepComponents(view);

        return view;
    }

    private void prepComponents(View view) {
        firstName = view.findViewById(R.id.editFirstNameProfile);
        firstName.setHint(user.getFirstName());
        firstName.setText(user.getFirstName());

        lastName = view.findViewById(R.id.editLastNameProfile);
        lastName.setHint(user.getLastName());
        lastName.setText(user.getLastName());

        email = view.findViewById(R.id.editTextEmailProfile);
        email.setHint(user.getEmail());
        email.setText(user.getEmail());

        skillLevel = view.findViewById(R.id.spinnerSkillLevelProfile);
        skillLevel.setAdapter(new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, ExperienceLevel.experienceLevels));

        String currentSkill = user.getExperienceLevel();
        switch (currentSkill) {
            case "Beginner":
                skillLevel.setSelection(0);
                break;
            case "Intermediate":
                skillLevel.setSelection(1);
                break;
            case "Expert":
                skillLevel.setSelection(2);
                break;
        }

        save = view.findViewById(R.id.buttonSaveProfile);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firstName.getText().toString() != null) {
                    user.setFirstName(firstName.getText().toString());
                }

                if (lastName.getText().toString() != null) {
                    user.setLastName(lastName.getText().toString());
                }

                if (email.getText().toString() != null) {
                    user.setEmail(email.getText().toString());
                }

//                UserDatabase.getDatabase(requireContext()).userDao().updateUser(user);
//                getParentFragmentManager().popBackStack();
            }
        });

        back = view.findViewById(R.id.buttonBackProfile);
        back.setOnClickListener(l -> getParentFragmentManager().popBackStack());
    }
}