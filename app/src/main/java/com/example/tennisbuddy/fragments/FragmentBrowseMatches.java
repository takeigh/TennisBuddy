package com.example.tennisbuddy.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.tennisbuddy.data.ExperienceLevel;
import com.example.tennisbuddy.data.MatchType;
import com.example.tennisbuddy.R;
import com.example.tennisbuddy.adapters.MatchDisplayAdapter;
import com.example.tennisbuddy.databases.CourtDatabase;
import com.example.tennisbuddy.databases.MatchDatabase;
import com.example.tennisbuddy.entities.Court;
import com.example.tennisbuddy.entities.Match;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FragmentBrowseMatches extends Fragment {
    Button createMatch;
    Button filter;
    Button sort;
    LinearLayout filterLayout;
    LinearLayout sortLayout;
    RecyclerView matchView;
    List<Match> matchList;
    Spinner experienceSpinner;
    Spinner distanceSpinner;
    Spinner typeSpinner;
    Spinner sortSpinner;

    public FragmentBrowseMatches() {
        // Required empty public constructor
    }


    public static FragmentBrowseMatches newInstance() {
        return new FragmentBrowseMatches();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_browse_matches, container, false);

        prepComponents(view);
//        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        // List<TennisMatch> tennisMatches = new ArrayList<>(); // populate/replace this
        // TennisMatch Adapter is a class that holds tennis match data-Not created yet.
//        TennisMatchAdapter adapter = new TennisMatchAdapter(tennisMatches);
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    private void prepComponents(View view) {
        matchList = MatchDatabase.getDatabase(getContext()).matchDao().getMatches();

        // Need to populate spinner options
        experienceSpinner = view.findViewById(R.id.spinnerExperienceFilter);
        List<String> optionsExperience = new ArrayList<>();
        optionsExperience.add("No Preference");
        optionsExperience.addAll(Arrays.asList(ExperienceLevel.experienceLevels));
        experienceSpinner.setAdapter(new ArrayAdapter<>(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, optionsExperience));

        distanceSpinner = view.findViewById(R.id.spinnerDistanceFilter);
        distanceSpinner.setAdapter(new ArrayAdapter<>(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, new String[] {
                "No Preference",
                "<10",
                "<25",
                "<50"
        }));

        typeSpinner = view.findViewById(R.id.spinnerTypeFilter);
        List<String> optionsType = new ArrayList<>();
        optionsType.add("No Preference");
        optionsType.addAll(Arrays.asList(MatchType.matchTypes));
        typeSpinner.setAdapter(new ArrayAdapter<>(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, optionsType));

        sortSpinner = view.findViewById(R.id.spinnerSort);
        sortSpinner.setAdapter(new ArrayAdapter<>(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, new String[] {
                "Increasing Distance",
                "Decreasing Distance"
        }));

        createMatch = view.findViewById(R.id.buttonNewMatch);
        createMatch.setOnClickListener(l -> newMatch());

        filter = view.findViewById(R.id.buttonFilter);
        filter.setOnClickListener(l -> toggleFilter());

        sort = view.findViewById(R.id.buttonSort);
        sort.setOnClickListener(l -> toggleSort());

        filterLayout = view.findViewById(R.id.layoutFilters);
        sortLayout = view.findViewById(R.id.layoutSort);

        matchView = view.findViewById(R.id.recyclerViewMatches);
        matchView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        populateMatches();
    }

    private void newMatch() {
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainerMain, new FragmentCreateMatch());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void populateMatches() {
        List<Match> filteredMatchList = new ArrayList<>();

        for (Match m : matchList) {
            // Filter matches
            filteredMatchList.add(m);
        }

        // Remove empty matches below as needed
        Match match = new Match();
        filteredMatchList.add(match);

        Match match2 = new Match();
        filteredMatchList.add(match2);

        Match match3 = new Match();
        filteredMatchList.add(match3);

        MatchDisplayAdapter adapter = new MatchDisplayAdapter(filteredMatchList);
        matchView.setAdapter(adapter);
    }

    private double getDistance(Match match) {
        Court court = CourtDatabase.getDatabase(getContext()).courtDao().getCourtById(match.getCourtId());

        // Get distance from current location to court
        double distance = 0;

        return distance;
    }

    private void toggleFilter() {
        sortLayout.setVisibility(View.GONE);

        if (filterLayout.getVisibility() == View.GONE) {
            filterLayout.setVisibility(View.VISIBLE);
        } else {
            filterLayout.setVisibility(View.GONE);
        }
    }

    private void toggleSort() {
        filterLayout.setVisibility(View.GONE);

        if (sortLayout.getVisibility() == View.GONE) {
            sortLayout.setVisibility(View.VISIBLE);
        } else {
            sortLayout.setVisibility(View.GONE);
        }
    }
}