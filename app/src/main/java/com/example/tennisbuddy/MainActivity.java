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

public class MainActivity extends AppCompatActivity {
    EditText email = findViewById(R.id.editTextEmailAddress);
    EditText password = findViewById(R.id.editTextPassword);
    ImageView logo = findViewById(R.id.imageLogo);
    Button login = findViewById(R.id.buttonLogin);
    Button forgot = findViewById(R.id.buttonForgot);
    Button signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        prepComponents();
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
    private void forgot(){
        boolean userInDatabase = false;
        String emailInput = email.getText().toString();
        String password;
        //handle forgot password (toast password and then automatically log user in)
        if (userInDatabase){
            Toast.makeText(this, "Password: " + password, Toast.LENGTH_LONG).show();
            login(emailInput, password);
        } else {
            Toast.makeText(this, "Invalid Username!", Toast.LENGTH_LONG).show();
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
    private void login(String email, String password){
        boolean loginSuccess = false;

        if(!loginSuccess){
            Toast.makeText(this, "Invalid Username/Password", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Login Success!", Toast.LENGTH_LONG).show(); //FOR TESTING ONLY
            //proceed to the main application
            //goto landing page fragment
            Intent intent = new Intent(this, LandingActivity.class);
            startActivity(intent);
        }
    }
}