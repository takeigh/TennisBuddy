package com.example.tennisbuddy.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

import com.example.tennisbuddy.R;

public class FragmentCreateMatch extends Fragment {

    Button back;
    Button create;
    Spinner spinnerCourts;
    Spinner spinnerMonth;
    Spinner spinnerDay;
    Spinner spinnerYear;
    Spinner spinnerHour;
    Spinner spinnerMinute;
    Spinner spinnerAMPM;
    Spinner spinnerType;


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