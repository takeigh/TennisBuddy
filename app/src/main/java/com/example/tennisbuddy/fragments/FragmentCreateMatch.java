package com.example.tennisbuddy.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.tennisbuddy.R;
import com.example.tennisbuddy.data.MatchType;
import com.example.tennisbuddy.databases.CourtDatabase;
import com.example.tennisbuddy.entities.Court;
import com.example.tennisbuddy.entities.Match;

import java.util.List;

public class FragmentCreateMatch extends Fragment {

    Button back;
    Button create;
    Spinner spinnerCourts;
    Spinner spinnerMonth;
    Spinner spinnerDay;
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

        create = view.findViewById(R.id.buttonCreateMatch);
        create.setOnClickListener(l -> {
            Match m = new Match();
        });

        spinnerCourts = view.findViewById(R.id.spinnerLocation);
        List<Court> courtList = CourtDatabase.getDatabase(requireContext()).courtDao().getCourts();
        spinnerCourts.setAdapter(new ArrayAdapter<>(requireActivity(), androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item,
                courtList
        ));


        spinnerMonth = view.findViewById(R.id.spinnerMonth);
        spinnerMonth.setAdapter(new ArrayAdapter<>(requireActivity(), androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item, new String[] {
                "1", "2", "3", "4", "5", "6",
                "7", "8", "9", "10", "11", "12"
        }));

        spinnerDay = view.findViewById(R.id.spinnerDay);
        spinnerDay.setAdapter(new ArrayAdapter<>(requireActivity(), androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item, new String[] {
                "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
                "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
                "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"
        }));

        spinnerHour = view.findViewById(R.id.spinnerHour);
        spinnerHour.setAdapter(new ArrayAdapter<>(requireActivity(), androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item, new String[] {
                "1", "2", "3", "4", "5", "6",
                "7", "8", "9", "10", "11", "12"
        }));

        spinnerMinute = view.findViewById(R.id.spinnerMinute);
        spinnerMinute.setAdapter(new ArrayAdapter<>(requireActivity(), androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item, new String[] {
                "00", "15",
                "30", "45"
        }));

        spinnerAMPM = view.findViewById(R.id.spinnerAMPM);
        spinnerAMPM.setAdapter(new ArrayAdapter<>(requireActivity(), androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item, new String[] {
                "AM", "PM"
        }));

        spinnerType = view.findViewById(R.id.spinnerMatchType);
        spinnerType.setAdapter(new ArrayAdapter<>(requireActivity(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                MatchType.matchTypes
        ));
    }
}