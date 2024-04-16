package com.example.tennisbuddy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tennisbuddy.databases.KeepUserDatabase;
import com.example.tennisbuddy.databases.UserDatabase;
import com.example.tennisbuddy.entities.KeepUser;
import com.example.tennisbuddy.entities.User;

public class LoginActivity extends AppCompatActivity {
    EditText email;
    EditText password;
    ImageView logo;
    Button login;
    Button forgot;
    Button signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        prepComponents();

        if (KeepUserDatabase.getDatabase(this).keepUserDao().getKeepUser() != null) {
            Intent intent = new Intent(this, LandingActivity.class);
            startActivity(intent);
        }
    }

    private void prepComponents() {
        logo = findViewById(R.id.imageLogo);
        Drawable logoDraw = ContextCompat.getDrawable(this, R.drawable.logo);
        logo.setImageDrawable(logoDraw);

        email = findViewById(R.id.editTextEmailAddress);
        password = findViewById(R.id.editTextPassword);

        login = findViewById(R.id.buttonLogin);
        login.setOnClickListener(l -> loginButton());

        forgot = findViewById(R.id.buttonForgot);
        forgot.setOnClickListener(l -> forgot());

        signup = findViewById(R.id.buttonSignup);
        signup.setOnClickListener(l -> signup());
    }

    private void loginButton(){
        String emailInput = email.getText().toString();
        String passwordInput = password.getText().toString();

        //handle login here
        login(emailInput, passwordInput);
    }

    private void login(String email, String password){
        User user = UserDatabase.getDatabase(this).userDao().getUserByEmail(email);

        if (user.getPassword().equals(password)) {
            KeepUser kUser = new KeepUser();
            kUser.setUserId(user.getUserId());
            KeepUserDatabase.getDatabase(this).keepUserDao().addUser(kUser);

            Intent intent = new Intent(this, LandingActivity.class);
            startActivity(intent);
        }
    }

    private void forgot(){
        String emailInput = email.getText().toString();
        User user = UserDatabase.getDatabase(this).userDao().getUserByEmail(emailInput);

        if (user != null) {
            Toast.makeText(this, "Password: " + user.getPassword(), Toast.LENGTH_LONG).show();
            login(emailInput, user.getPassword());
        } else {
            Toast.makeText(this, "Invalid Email!", Toast.LENGTH_LONG).show();
            clearFields();
        }
    }

    private void signup(){
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }

    public void clearFields(){
        email.setText(null);
        password.setText(null);
    }
}