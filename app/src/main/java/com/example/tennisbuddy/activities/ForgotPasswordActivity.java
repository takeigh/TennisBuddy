package com.example.tennisbuddy.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tennisbuddy.R;
import com.example.tennisbuddy.databases.UserDatabase;
import com.example.tennisbuddy.entities.User;

import java.util.List;

public class ForgotPasswordActivity extends AppCompatActivity {

    Button back;
    Button retrieve;
    EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        prepComponents();
    }

    private void prepComponents() {
        back = findViewById(R.id.buttonBackForgotPassword);
        back.setOnClickListener(l -> finish());

        retrieve = findViewById(R.id.buttonRetrievePassword);
        retrieve.setOnClickListener(l -> toastPassword());

        email = findViewById(R.id.editTextEmail);
    }

    private void toastPassword() {
        String emailString = email.getText().toString();

        if (emailString.isEmpty()) {
            Toast.makeText(this, "Please enter an email", Toast.LENGTH_LONG).show();
        } else {
            List<User> users = UserDatabase.getDatabase(this).userDao().getUsers();

            for (User u : users) {
                if (u.getEmail().equals(emailString)) {
                    Toast.makeText(this, "Password: " + u.getPassword(), Toast.LENGTH_LONG).show();
                }
            }

            Toast.makeText(this, "No account found with email: " + emailString, Toast.LENGTH_LONG).show();
        }
    }
}