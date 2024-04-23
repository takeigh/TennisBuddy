package com.example.tennisbuddy.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.tennisbuddy.fragments.FragmentCreateMatch;
import com.example.tennisbuddy.R;
import com.example.tennisbuddy.fragments.FragmentBrowseMatches;
import com.example.tennisbuddy.fragments.FragmentChatList;
import com.example.tennisbuddy.fragments.FragmentMenuBar;
import com.example.tennisbuddy.fragments.FragmentViewProfile;

public class LandingActivity extends AppCompatActivity {
    FragmentManager fragManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        initialization();
    }

    private void initialization() {
        fragManager = getSupportFragmentManager();

        fragManager.beginTransaction()
                .add(R.id.fragmentContainerMenu, new FragmentMenuBar())
                .commit();

        fragManager.beginTransaction()
                .add(R.id.fragmentContainerMain, new FragmentBrowseMatches())
                .commit();
    }
}
