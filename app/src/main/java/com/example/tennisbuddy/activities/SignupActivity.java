package com.example.tennisbuddy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tennisbuddy.ExperienceLevel;
import com.example.tennisbuddy.R;
import com.example.tennisbuddy.databases.KeepUserDatabase;
import com.example.tennisbuddy.databases.UserDatabase;
import com.example.tennisbuddy.entities.KeepUser;
import com.example.tennisbuddy.entities.User;

public class SignupActivity extends AppCompatActivity {
    ImageView logo;
    EditText fName;
    EditText lName;
    EditText email;
    EditText password;
    EditText confirmPassword;
    Spinner difficulty;
    Button submit;
    Button back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        prepComponents();
    }

    private void prepComponents() {
        logo = findViewById(R.id.imageLogo);
        logo.setImageResource(R.drawable.logo);

        fName = findViewById(R.id.editTextfName);
        lName = findViewById(R.id.editTextlName);
        email = findViewById(R.id.editTextEmailAddress);
        password = findViewById(R.id.editTextPassword);
        confirmPassword = findViewById(R.id.editTextConfirmPassword);

        difficulty = findViewById(R.id.spinnerDifficulty);
        difficulty.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ExperienceLevel.experienceLevels));

        submit = findViewById(R.id.buttonSubmit);
        submit.setOnClickListener(l -> submit());

        back = findViewById(R.id.buttonBackSignUp);
        back.setOnClickListener(l -> finish());
    }

    public void submit(){
        String f = fName.getText().toString();
        String l = lName.getText().toString();
        String e = email.getText().toString();
        String p = password.getText().toString();
        String cP = confirmPassword.getText().toString();
        String exp = difficulty.getSelectedItem().toString();

        //Add logic to deal with spinner here and make it pass to register function
        if (f.isEmpty() || l.isEmpty() || e.isEmpty() || p.isEmpty() || cP.isEmpty()){
            Toast.makeText(this, "All Fields Must Be Filled Out!", Toast.LENGTH_LONG).show();
        } else if (!p.equals(cP)){
            Toast.makeText(this, "Passwords Do Not Match", Toast.LENGTH_LONG).show();
        } else {
            register(f, l, e, p, exp);
        }
    }

    public void register(String firstName, String lastName, String email, String pass, String experience){
        User user = new User();
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(pass);
        user.setExperienceLevel(experience);

        UserDatabase.getDatabase(this).userDao().addUser(user);

        KeepUser keep = new KeepUser();
        keep.setUserId(user.getUserId());
        KeepUserDatabase.getDatabase(this).keepUserDao().deleteUsers();
        KeepUserDatabase.getDatabase(this).keepUserDao().addUser(keep);

        Intent intent = new Intent(this, LandingActivity.class);
        startActivity(intent);
    }
}
