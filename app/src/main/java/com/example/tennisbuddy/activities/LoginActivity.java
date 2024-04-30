package com.example.tennisbuddy.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tennisbuddy.R;
import com.example.tennisbuddy.databases.ChatDatabase;
import com.example.tennisbuddy.databases.CourtDatabase;
import com.example.tennisbuddy.databases.KeepUserDatabase;
import com.example.tennisbuddy.databases.MatchDatabase;
import com.example.tennisbuddy.databases.UserDatabase;
import com.example.tennisbuddy.entities.Chat;
import com.example.tennisbuddy.entities.Court;
import com.example.tennisbuddy.entities.KeepUser;
import com.example.tennisbuddy.entities.Match;
import com.example.tennisbuddy.entities.User;

import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private static final int MAP_ACTIVITY_FINE_LOCATION_REQ_CODE = 0;
    private static final int MAP_ACTIVITY_COARSE_LOCATION_REQ_CODE = 0;
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

        permissions();
        prepComponents();
        registerCourts();
//        clearPersistence();
        printDB();

        if (KeepUserDatabase.getDatabase(this).keepUserDao().getKeepUser() != null) {
            Intent intent = new Intent(this, LandingActivity.class);
            startActivity(intent);
        }
    }

    private void clearPersistence() {
        KeepUserDatabase.getDatabase(this).keepUserDao().deleteUsers();
//        UserDatabase.getDatabase(this).userDao().removeAll();
    }

    private  void registerCourts() {
        List<Court> courts = CourtDatabase.getDatabase(this).courtDao().getCourts();

        // Only add the courts if the database is empty
        if (courts.size() == 0) {
            Court c = new Court();
            c.setAddress("1501 W 6th St, Erie, PA 16505");
            c.setCourtName("Frontier Park Tennis Courts");

            CourtDatabase.getDatabase(this).courtDao().addCourt(c);

            c = new Court();
            c.setAddress("4712 College Dr, Erie, PA 16510");
            c.setCourtName("Penn State Behrend Tennis Courts");

            CourtDatabase.getDatabase(this).courtDao().addCourt(c);

            c = new Court();
            c.setAddress("2660 Zuck Rd Unit A, Erie, PA 16506");
            c.setCourtName("Westwood Racquet Club");

            CourtDatabase.getDatabase(this).courtDao().addCourt(c);

            c = new Court();
            c.setAddress("2403 W 8th St, Erie, PA 16505");
            c.setCourtName("Salata Tennis Complex");

            CourtDatabase.getDatabase(this).courtDao().addCourt(c);

            c = new Court();
            c.setAddress("2419 Geist Rd, Erie, PA 16502");
            c.setCourtName("Baldwin Park Pickleball Courts");

            CourtDatabase.getDatabase(this).courtDao().addCourt(c);
        }
    }

    private void printDB() {
        List<User> users = UserDatabase.getDatabase(this).userDao().getUsers();
        Log.d("DEBUG", "Database Users:");
        for (User u : users) {
            Log.d("DEBUG", u.getFirstName() + " " + u.getLastName());
        }

        List<Court> courts = CourtDatabase.getDatabase(this).courtDao().getCourts();
        Log.d("DEBUG", "Database Courts:");
        for (Court c : courts) {
            Log.d("DEBUG", c.getCourtName() + ", id: " + c.getCourtId());
        }

        Log.d("DEBUG", "Match List:");
        List<Match> matches = MatchDatabase.getDatabase(this).matchDao().getMatches();
        for (Match m : matches) {
            Log.d("DEBUG", m.getMatchId() + ", " + m.getHostId());
        }

        Log.d("DEBUG", "Chat List:");
        List<Chat> chats = ChatDatabase.getDatabase(this).chatDao().getChats();
        for (Chat c : chats) {
            Log.d("DEBUG", c.getSenderId() + ", " + c.getReceiverId() + ": " + c.getMessage());
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

    private void login(String emailInput, String passwordInput){
        User user = UserDatabase.getDatabase(this).userDao().getUserByEmail(emailInput);

        if (user != null) {
            if (user.getPassword().equals(passwordInput)) {
                email.setText("");
                password.setText("");

                KeepUser kUser = new KeepUser();
                kUser.setUserId(user.getUserId());
                KeepUserDatabase.getDatabase(this).keepUserDao().addUser(kUser);

                Intent intent = new Intent(this, LandingActivity.class);
                startActivity(intent);
            }else {
                Toast.makeText(this, "Email or Password not found", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Email or Password not found", Toast.LENGTH_LONG).show();
        }
    }

    private void forgot(){
        Intent intent = new Intent(this, ForgotPasswordActivity.class);
        startActivity(intent);
    }

    private void signup(){
        email.setText("");
        password.setText("");

        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }

    private void permissions() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MAP_ACTIVITY_FINE_LOCATION_REQ_CODE);
            }
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        MAP_ACTIVITY_COARSE_LOCATION_REQ_CODE);
            }
        }
    }
}