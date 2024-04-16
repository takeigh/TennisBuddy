package com.example.tennisbuddy.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.tennisbuddy.R;
import com.example.tennisbuddy.fragments.FragmentBrowseMatches;
import com.example.tennisbuddy.fragments.FragmentChatList;
import com.example.tennisbuddy.fragments.FragmentMenuBar;
import com.example.tennisbuddy.fragments.FragmentViewProfile;

public class LandingActivity extends AppCompatActivity {
    FragmentManager fragManager;
    String currentFrag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        initialization();
        setFragment("Browse");
    }

    private void initialization() {
        fragManager = getSupportFragmentManager();

        fragManager.beginTransaction()
                .add(R.id.fragmentContainerMenu, new FragmentMenuBar())
                .commit();

        currentFrag = "Browse";
        fragManager.beginTransaction()
                .add(R.id.fragmentContainerMain, new FragmentBrowseMatches())
                .commit();
    }

    public void setFragment(String fragName) {
        switch (fragName) {
            case "Browse":
                if (currentFrag.equals(fragName)) {
                    fragManager.beginTransaction()
                            .replace(R.id.fragmentContainerMain, new FragmentBrowseMatches())
                            .commit();
                }
                break;
            case "Chat":
                if (currentFrag.equals(fragName)) {
                    fragManager.beginTransaction()
                            .replace(R.id.fragmentContainerMain, new FragmentChatList())
                            .commit();
                }
                break;
            case "Profile":
                if (currentFrag.equals(fragName)) {
                    fragManager.beginTransaction()
                            .replace(R.id.fragmentContainerMain, new FragmentViewProfile())
                            .commit();
                }
                break;
            default:
                Toast.makeText(this, "Something went wrong changing views", Toast.LENGTH_LONG).show();
                break;
        }
    }
}
