package com.example.tennisbuddy.fragments;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tennisbuddy.R;
import com.example.tennisbuddy.data.MatchType;
import com.example.tennisbuddy.databases.CourtDatabase;
import com.example.tennisbuddy.databases.KeepUserDatabase;
import com.example.tennisbuddy.databases.MatchDatabase;
import com.example.tennisbuddy.databases.UserDatabase;
import com.example.tennisbuddy.entities.Court;
import com.example.tennisbuddy.entities.Match;
import com.example.tennisbuddy.entities.User;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FragmentCreateMatch extends Fragment implements OnMapReadyCallback{

    Button back;
    Button create;
    Spinner spinnerCourts;
    Spinner spinnerMonth;
    Spinner spinnerDay;
    Spinner spinnerHour;
    Spinner spinnerMinute;
    Spinner spinnerAMPM;
    Spinner spinnerType;
    GoogleMap mMap;
    MapView mapView;


    public static FragmentCreateMatch newInstance() {
        return new FragmentCreateMatch();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_match, container, false);

        prepMap(view, savedInstanceState);

        prepComponents(view);

        return view;
    }

    private void prepMap(View view, Bundle savedInstanceState) {
        mapView = view.findViewById(R.id.mapCreate);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
    }

    private void prepComponents(View view) {
        back = view.findViewById(R.id.backButton);
        back.setOnClickListener(l -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        create = view.findViewById(R.id.buttonCreateMatch);
        create.setOnClickListener(l -> newMatch());

        spinnerCourts = view.findViewById(R.id.spinnerLocation);
        List<Court> courtList = CourtDatabase.getDatabase(requireContext()).courtDao().getCourts();
        List<String> courtNames = new ArrayList<>();
        courtNames.add("No Selection");
        for (Court c : courtList) {
            courtNames.add(c.getCourtName());
        }
        spinnerCourts.setAdapter(new ArrayAdapter<>(requireActivity(), androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item,
                courtNames
        ));
        spinnerCourts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Handle item selection
                String selectedOption = courtNames.get(position);

                if (position == 0) {
                    markMap(courtList);

                    return;
                }

                for (Court c : courtList) {
                    if (c.getCourtName().equals(selectedOption)) {
                        List<Court> court = new ArrayList<>();
                        court.add(c);
                        markMap(court);
                        break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Handle no selection
            }
        });

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

        if (mMap != null) {
            markMap(courtList);
        }
    }

    private void newMatch() {
        if (!spinnerCourts.getSelectedItem().toString().equals("No Selection")) {
            Match m = new Match();
            Court c = CourtDatabase.getDatabase(requireContext()).courtDao().getCourtByName(spinnerCourts.getSelectedItem().toString());
            int hostId = KeepUserDatabase.getDatabase(requireContext()).keepUserDao().getKeepUser().getUserId();
            User u = UserDatabase.getDatabase(requireContext()).userDao().getUserByID(hostId);

            m.setCourtId(c.getCourtId());
            m.setMonth(Integer.parseInt(spinnerMonth.getSelectedItem().toString()));
            m.setDay(Integer.parseInt(spinnerDay.getSelectedItem().toString()));

            if (spinnerAMPM.getSelectedItem().toString().equals("AM")) {
                if (Integer.parseInt(spinnerHour.getSelectedItem().toString()) == 12) {
                    m.setHour(0);
                } else {
                    m.setHour(Integer.parseInt(spinnerHour.getSelectedItem().toString()));
                }
            } else {
                if (Integer.parseInt(spinnerHour.getSelectedItem().toString()) == 12) {
                    int hour = Integer.parseInt(spinnerHour.getSelectedItem().toString());
                    m.setHour(hour);
                } else {
                    int hour = Integer.parseInt(spinnerHour.getSelectedItem().toString()) + 12;
                    m.setHour(hour);
                }
            }

            m.setMinute(Integer.parseInt(spinnerMinute.getSelectedItem().toString()));
            m.setHostId(hostId);
            m.setExperienceLevel(u.getExperienceLevel());
            m.setMatchType(spinnerType.getSelectedItem().toString());

            MatchDatabase.getDatabase(requireContext()).matchDao().addMatch(m);

            requireActivity().getSupportFragmentManager().popBackStack();
        }
    }

    private void markMap(List<Court> courts) {
        mMap.clear();

        for (int i = 0; i < courts.size(); i++) {
            List<Double> coords = convertStringToCoordinates(courts.get(i).getCourtName());

            if (coords != null) {
                LatLng marker = new LatLng(coords.get(0), coords.get(1));
                mMap.addMarker(new MarkerOptions().position(marker).title(courts.get(i).getCourtName()));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(marker));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker, 11));
            }
        }
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