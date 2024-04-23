package com.example.tennisbuddy.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.tennisbuddy.R;

public class FragmentCreateMatch extends Fragment {

    Button back;

    public static FragmentCreateMatch newInstance() {
        return new FragmentCreateMatch();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_match, container, false);

        prepComponents(view);

        return view;
    }

    private void prepComponents(View view) {
        back = view.findViewById(R.id.backButton);
        back.setOnClickListener(l -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });
    }
}