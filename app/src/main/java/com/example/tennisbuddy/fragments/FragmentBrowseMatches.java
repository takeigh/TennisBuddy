package com.example.tennisbuddy.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import android.location.Address;
import android.location.Geocoder;


import com.example.tennisbuddy.data.ExperienceLevel;
import com.example.tennisbuddy.data.MatchType;
import com.example.tennisbuddy.R;
import com.example.tennisbuddy.adapters.MatchDisplayAdapter;
import com.example.tennisbuddy.databases.CourtDatabase;
import com.example.tennisbuddy.databases.MatchDatabase;
import com.example.tennisbuddy.entities.Court;
import com.example.tennisbuddy.entities.Match;
import com.example.tennisbuddy.listeners.OnClickMatchListener;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class FragmentBrowseMatches extends Fragment implements OnClickMatchListener {
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
    Context context;
    MatchDisplayAdapter adapter;

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
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_browse_matches, container, false);

        prepComponents(view);

        return view;
    }

    private void prepComponents(View view) {
        context = getContext();
        matchList = MatchDatabase.getDatabase(getContext()).matchDao().getMatches();

        // Need to populate spinner options
        experienceSpinner = view.findViewById(R.id.spinnerExperienceFilter);
        List<String> optionsExperience = new ArrayList<>();
        optionsExperience.add("No Preference");
        optionsExperience.addAll(Arrays.asList(ExperienceLevel.experienceLevels));
        experienceSpinner.setAdapter(new ArrayAdapter<>(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, optionsExperience));
        experienceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                populateMatches();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        distanceSpinner = view.findViewById(R.id.spinnerDistanceFilter);
        distanceSpinner.setAdapter(new ArrayAdapter<>(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, new String[] {
                "No Preference",
                "<10 miles",
                "<25 miles",
                "<50 miles"
        }));
        distanceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                populateMatches();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        typeSpinner = view.findViewById(R.id.spinnerTypeFilter);
        List<String> optionsType = new ArrayList<>();
        optionsType.add("No Preference");
        optionsType.addAll(Arrays.asList(MatchType.matchTypes));
        typeSpinner.setAdapter(new ArrayAdapter<>(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, optionsType));
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                populateMatches();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sortSpinner = view.findViewById(R.id.spinnerSort);
        sortSpinner.setAdapter(new ArrayAdapter<>(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, new String[] {
                "Increasing Distance",
                "Decreasing Distance",
                "Increasing Date",
                "Decreasing Date",
        }));
        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                populateMatches();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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
        List<Match> filteredMatchList = filterMatches(matchList);

        List<Match> sortedMatchList = sortMatches(filteredMatchList);

        adapter = new MatchDisplayAdapter(sortedMatchList, this, context, requireActivity().getSupportFragmentManager());
        matchView.setAdapter(adapter);

    }

    private List<Match> filterMatches(List<Match> matchList){
        List<Match> step1 = new ArrayList<>();
        List<Match> step2 = new ArrayList<>();
        List<Match> filteredMatchList = new ArrayList<>();

        String distanceSetting = distanceSpinner.getSelectedItem().toString();
        switch (distanceSetting) {
            case "<10 miles":
                for (Match m : matchList) {
                    double distance = distanceCalculation(m);

                    if (distance <= 10.0) {
                        step1.add(m);
                    }
                }
                break;
            case "<25 miles":
                for (Match m : matchList) {
                    double distance = distanceCalculation(m);

                    if (distance <= 25.0) {
                        step1.add(m);
                    }
                }
                break;
            case "<50 miles":
                for (Match m : matchList) {
                    double distance = distanceCalculation(m);

                    if (distance <= 50.0) {
                        step1.add(m);
                    }
                }
                break;
            default:
                step1 = matchList;
        }

        String experienceSetting = experienceSpinner.getSelectedItem().toString();
        switch (experienceSetting) {
            case "Beginner":
                for (Match m : step1) {
                    if (m.getExperienceLevel().equals("Beginner")) {
                        step2.add(m);
                    }
                }
                break;
            case "Intermediate":
                for (Match m : step1) {
                    if (m.getExperienceLevel().equals("Intermediate")) {
                        step2.add(m);
                    }
                }
                break;
            case "Advanced":
                for (Match m : step1) {
                    if (m.getExperienceLevel().equals("Expert")) {
                        step2.add(m);
                    }
                }
                break;
            default:
                step2 = step1;
        }

        String typeSetting = typeSpinner.getSelectedItem().toString();
        switch (typeSetting) {
            case "Singles":
                for (Match m : step2) {
                    if (m.getMatchType().equals("Singles")) {
                        filteredMatchList.add(m);
                    }
                }
                break;
            case "Doubles":
                for (Match m : step2) {
                    if (m.getMatchType().equals("Doubles")) {
                        filteredMatchList.add(m);
                    }
                }
                break;
            default:
                filteredMatchList = step2;
        }

        return filteredMatchList;
    }

    private List<Match> sortMatches(List<Match> list) {
        String sortType = sortSpinner.getSelectedItem().toString();

        switch (sortType) {
            case "Increasing Distance":
                return sortDistanceIncreasing(list);
            case "Decreasing Distance":
                return sortDistanceDecreasing(list);
            case "Increasing Date":
                return sortDateIncreasing(list);
            case "Decreasing Date":
                return sortDateDecreasing(list);
        }

        return null;
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

    @Override
    public void onMatchClick(int position) {
        Toast.makeText(getContext(), "Hit: " + position, Toast.LENGTH_LONG).show();
    }

    public List<Match> sortDistanceIncreasing(List<Match> list) {
        list.sort(new Comparator<Match>() {
            @Override
            public int compare(Match match1, Match match2) {
                double distance1 = distanceCalculation(match1);
                double distance2 = distanceCalculation(match2);

                return Double.compare(distance1, distance2);
            }
        });

        return list;
    }

    public List<Match> sortDistanceDecreasing(List<Match> list) {
        list.sort(new Comparator<Match>() {
            @Override
            public int compare(Match match1, Match match2) {
                double distance1 = distanceCalculation(match1);
                double distance2 = distanceCalculation(match2);

                return Double.compare(distance2, distance1);
            }
        });

        return list;
    }

    public  List<Match> sortDateIncreasing(List<Match> list) {
        list.sort(new Comparator<Match>() {
            @Override
            public int compare(Match match1, Match match2) {
                int month1 = match1.getMonth();
                int day1 = match1.getDay();
                int hour1 = match1.getHour();
                int minute1 = match1.getMinute();

                int month2 = match2.getMonth();
                int day2 = match2.getDay();
                int hour2 = match2.getHour();
                int minute2 = match2.getMinute();

                if (month1 > month2) {
                    return 1;
                } else if (month1 < month2) {
                    return -1;
                } else {
                    if (day1 > day2) {
                        return 1;
                    } else if (day1 < day2) {
                        return -1;
                    } else {
                        if (hour1 > hour2) {
                            return 1;
                        } else if (hour1 < hour2) {
                            return -1;
                        } else {
                            return Integer.compare(minute1, minute2);
                        }
                    }
                }
            }
        });

        return list;
    }

    public  List<Match> sortDateDecreasing(List<Match> list) {
        list.sort(new Comparator<Match>() {
            @Override
            public int compare(Match match1, Match match2) {
                int month1 = match1.getMonth();
                int day1 = match1.getDay();
                int hour1 = match1.getHour();
                int minute1 = match1.getMinute();

                int month2 = match2.getMonth();
                int day2 = match2.getDay();
                int hour2 = match2.getHour();
                int minute2 = match2.getMinute();

                if (month1 < month2) {
                    return 1;
                } else if (month1 > month2) {
                    return -1;
                } else {
                    if (day1 < day2) {
                        return 1;
                    } else if (day1 > day2) {
                        return -1;
                    } else {
                        if (hour1 < hour2) {
                            return 1;
                        } else if (hour1 > hour2) {
                            return -1;
                        } else {
                            return Integer.compare(minute2, minute1);
                        }
                    }
                }
            }
        });

        return list;
    }

    public Double distanceCalculation(Match m) {
        Court court = CourtDatabase.getDatabase(requireContext()).courtDao().getCourtById(m.getCourtId());
        List<Double> destination = convertStringToCoordinates(court.getAddress());

        double destLat = destination.get(0);
        double destLon = destination.get(1);

        List<Double> start = convertStringToCoordinates("Gannon University");
        double startLat = start.get(0);
        double startLon = start.get(1);

        int Radius = 6371;// radius of earth in Km
        double dLat = Math.toRadians(destLat - startLat);
        double dLon = Math.toRadians(destLon - startLon);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(startLat))
                * Math.cos(Math.toRadians(destLat)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.parseInt(newFormat.format(valueResult));
        double meter = valueResult % 1000;
        int meterInDec = Integer.parseInt(newFormat.format(meter));
        Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
                + " Meter   " + meterInDec);

        DecimalFormat natural = new DecimalFormat("0.00");

        return Double.parseDouble(natural.format(valueResult));
    }

    public List<Double> convertStringToCoordinates(String location) {
        if (location != null) {
            Geocoder geocoder = new Geocoder(requireContext(), Locale.getDefault());

            try {
                List<Address> locationAddress = geocoder.getFromLocationName(location, 1);

                if (locationAddress.size() > 0) {
                    Double Lat = locationAddress.get(0).getLatitude();
                    Double Lon = locationAddress.get(0).getLongitude();

                    List<Double> latLon = new ArrayList<>();
                    latLon.add(Lat);
                    latLon.add(Lon);

                    return latLon;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}