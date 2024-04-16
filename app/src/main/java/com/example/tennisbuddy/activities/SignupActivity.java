package com.example.tennisbuddy.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tennisbuddy.R;

public class SignupActivity extends AppCompatActivity {
    ImageView logo = findViewById(R.id.imageLogo);
    EditText fName = findViewById(R.id.editTextfName);
    EditText lName = findViewById(R.id.editTextlName);
    EditText email = findViewById(R.id.editTextEmailAddress);
    EditText password = findViewById(R.id.editTextPassword);
    EditText confirmPassword = findViewById(R.id.editTextConfirmPassword);
    Spinner difficulty = findViewById(R.id.spinnerDifficulty);
    Button submit = findViewById(R.id.buttonSubmit);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        logo.setImageResource(R.drawable.logo);
        //Add difficulty options to dropdown
        submit.setOnClickListener(l -> {
            submit();
        });
    }

    public void submit(){
        String f = fName.getText().toString();
        String l = lName.getText().toString();
        String e = email.getText().toString();
        String p = password.getText().toString();
        String cP = confirmPassword.getText().toString();
        //Add logic to deal with spinner here and make it pass to register function
        if (f.isEmpty() || l.isEmpty() || e.isEmpty() || p.isEmpty() || cP.isEmpty()){
            Toast.makeText(this, "All Fields Must Be Filled Out!", Toast.LENGTH_LONG).show();
        } else if (!p.equals(cP)){
            Toast.makeText(this, "Passwords Do Not Match", Toast.LENGTH_LONG).show();
        } else register(f, l, e, p, cP);
    }

    public void register(String f, String l, String e, String p, String cP){
        //logic to register user here
    }
}
