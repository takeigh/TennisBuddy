package com.example.tennisbuddy.activities;

import android.os.Bundle;
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
    String currentFrag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        initialization();
        setFragment("Browse", null);
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

    public void setFragment(String fragName, Bundle bundle) {
        if (!currentFrag.equals(fragName)) {
            currentFrag = fragName;

            switch (fragName) {
                case "Browse":
                    fragManager.beginTransaction()
                            .replace(R.id.fragmentContainerMain, new FragmentBrowseMatches())
                            .commit();
                    break;
                case "Chat List":
                    fragManager.beginTransaction()
                            .replace(R.id.fragmentContainerMain, new FragmentChatList())
                            .commit();
                    break;
                case "Profile":
                    fragManager.beginTransaction()
                            .replace(R.id.fragmentContainerMain, new FragmentViewProfile())
                            .commit();
                    break;
                case "New Match":
                    fragManager.beginTransaction()
                            .replace(R.id.fragmentContainerMain, new FragmentCreateMatch())
                            .commit();
                    break;
                case "Chat Room":
                    //FragmentChat chatFrag = FragmentChat.newInstance(bundle);

//                    fragManager.beginTransaction()
//                            .replace(R.id.fragmentContainerMain, chatFrag)
//                            .commit();
                    break;
                default:
                    Toast.makeText(this, "Something went wrong changing fragments", Toast.LENGTH_LONG).show();
                    break;
            }

            currentFrag = fragName;
        }
    }
}
