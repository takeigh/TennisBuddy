package com.example.tennisbuddy;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditProfile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditProfile extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EditProfile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditProfile.
     */
    // TODO: Rename and change types and number of parameters
    public static EditProfile newInstance(String param1, String param2) {
        EditProfile fragment = new EditProfile();
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
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        EditText editFirstName = view.findViewById(R.id.editFirstName);
        EditText editLastName = view.findViewById(R.id.editLastName);
        Spinner spinnerSkillLevel = view.findViewById(R.id.spinnerSkillLevel);
        Button buttonSave = view.findViewById(R.id.buttonSave);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = editFirstName.getText().toString();
                String lastName = editLastName.getText().toString();
                String skillLevel = spinnerSkillLevel.getSelectedItem().toString();

                // Create a new User object with the new data
                User user = new User();
                user.setFirstName(firstName);
                user.setLastName(lastName);
                //user.setSkillLevel(skillLevel);

                // Get the UserDao and update the user in the database
                UserDatabase userDatabase = UserDatabase.getDatabase(getContext());
                UserDao userDao = userDatabase.userDao();
                userDao.addUser(user);

                Toast.makeText(getContext(), "Profile saved!", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}