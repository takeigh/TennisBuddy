package com.example.tennisbuddy.fragments;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.tennisbuddy.R;
import com.example.tennisbuddy.databases.CourtDatabase;
import com.example.tennisbuddy.databases.KeepUserDatabase;
import com.example.tennisbuddy.databases.MatchDatabase;
import com.example.tennisbuddy.databases.UserDatabase;
import com.example.tennisbuddy.entities.Court;
import com.example.tennisbuddy.entities.KeepUser;
import com.example.tennisbuddy.entities.Match;
import com.example.tennisbuddy.entities.User;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FragmentViewMatch extends Fragment implements OnMapReadyCallback {

    int matchID;
    Match m;
    Button back;
    Button delete;
    Button rsvp;
    TextView date;
    TextView time;
    TextView location;
    TextView experienceLevel;
    TextView type;
    TextView hostName;
    TextView player2;
    TextView player3;
    TextView player4;
    MapView mapView;
    GoogleMap mMap;
    int playerCount = 1;
    Court c;

    public FragmentViewMatch() {
        // Required empty public constructor
    }

    public static FragmentViewMatch newInstance(Bundle args) {
        FragmentViewMatch fragment = new FragmentViewMatch();

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            matchID = getArguments().getInt("id");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_match, container, false);

        prepMap(view, savedInstanceState);

        prepComponents(view);

        return view;
    }

    private void prepMap(View view, Bundle savedInstanceState) {
        mapView = view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        markMap(c);
    }

    private void prepComponents(View view) {
        m = MatchDatabase.getDatabase(requireContext()).matchDao().getMatchById(matchID);

        back = view.findViewById(R.id.buttonBackView);
        back.setOnClickListener(l -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        delete = view.findViewById(R.id.buttonDeleteMatch);
        delete.setOnClickListener(l -> {
            Match m = MatchDatabase.getDatabase(requireContext()).matchDao().getMatchById(matchID);
            MatchDatabase.getDatabase(requireContext()).matchDao().deleteMatch(m);

            requireActivity().getSupportFragmentManager().popBackStack();
        });

        rsvp = view.findViewById(R.id.buttonRSVPMatch);
        rsvp.setOnClickListener(l -> register());

        type = view.findViewById(R.id.textViewMatchType);
        date = view.findViewById(R.id.textViewMatchDate);
        time = view.findViewById(R.id.textViewMatchTime);
        experienceLevel = view.findViewById(R.id.textViewMatchExperience);
        location = view.findViewById(R.id.textViewMatchLocation);
        hostName = view.findViewById(R.id.textViewHostName);
        player2 = view.findViewById(R.id.textViewPlayer2);
        player3 = view.findViewById(R.id.textViewPlayer3);
        player4 = view.findViewById(R.id.textViewPlayer4);

        matchData();
        players();

        buttons();
    }

    private void buttons() {
        KeepUser kUser = KeepUserDatabase.getDatabase(requireContext()).keepUserDao().getKeepUser();

        if (m.getHostId() == kUser.getUserId()) {
            delete.setVisibility(VISIBLE);
        } else {
            if (playerCount < 2 && kUser.getUserId() != m.getPlayer2Id()) {
                rsvp.setVisibility(VISIBLE);
            } else {
                if (playerCount < 4 && m.getMatchType().equals("Doubles") &&
                kUser.getUserId() != m.getPlayer3Id() && kUser.getUserId() != m.getPlayer4Id()) {
                    rsvp.setVisibility(VISIBLE);
                }
            }
        }
    }

    private void players() {
        if (m.getPlayer2Id() != 0) {
            User u = UserDatabase.getDatabase(requireContext()).userDao().getUserByID(m.getPlayer2Id());
            String name = u.getFirstName() + " " + u.getLastName();
            player2.setText(name);
            player2.setOnClickListener(l -> {
                FragmentViewProfile fragment = FragmentViewProfile.newInstance(u.getUserId(), false);
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerMain, fragment)
                        .addToBackStack(null)
                        .commit();

            });
            playerCount++;
        }

        if (m.getMatchType().equals("Doubles")) {
            if (m.getPlayer3Id() != 0) {
                User u = UserDatabase.getDatabase(requireContext()).userDao().getUserByID(m.getPlayer3Id());
                String name = u.getFirstName() + " " + u.getLastName();
                player3.setText(name);
                player3.setOnClickListener(l -> {
                    FragmentViewProfile fragment = FragmentViewProfile.newInstance(u.getUserId(), false);
                    getParentFragmentManager().beginTransaction()
                            .replace(R.id.fragmentContainerMain, fragment)
                            .addToBackStack(null)
                            .commit();

                });
                playerCount++;
            }

            if (m.getPlayer4Id() != 0) {
                User u = UserDatabase.getDatabase(requireContext()).userDao().getUserByID(m.getPlayer4Id());
                String name = u.getFirstName() + " " + u.getLastName();
                player4.setText(name);
                player4.setOnClickListener(l -> {
                    FragmentViewProfile fragment = FragmentViewProfile.newInstance(u.getUserId(), false);
                    getParentFragmentManager().beginTransaction()
                            .replace(R.id.fragmentContainerMain, fragment)
                            .addToBackStack(null)
                            .commit();

                });
                playerCount++;
            }
        }
    }

    private void matchData() {
        type.setText(m.getMatchType());

        String dates = m.getMonth() + "/" + m.getDay() + "/24";
        date.setText(dates);

        int hour = m.getHour();
        String afternoon = "AM";
        if (hour > 12) {
            hour = hour - 12;
            afternoon = "PM";
        }
        if (m.getMinute() == 0) {
            String times = hour + ":00 " + afternoon;
            time.setText(times);
        } else {
            String times = hour + ":" + m.getMinute() + " " + afternoon;
            time.setText(times);
        }

        experienceLevel.setText(m.getExperienceLevel());

        c = CourtDatabase.getDatabase(requireContext()).courtDao().getCourtById(m.getCourtId());
        location.setText(c.getAddress());
        if (mMap != null) {
            markMap(c);
        }

        User host = UserDatabase.getDatabase(requireContext()).userDao().getUserByID(m.getHostId());
        String hosts = "Host: " + host.getFirstName() + " " + host.getLastName();
        hostName.setText(hosts);
        hostName.setOnClickListener(l -> {
            FragmentViewProfile fragment = FragmentViewProfile.newInstance(host.getUserId(), false);
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerMain, fragment)
                    .addToBackStack(null)
                    .commit();

        });

    }

    private void markMap(Court court) {
        mMap.clear();

        List<Double> coords = convertStringToCoordinates(court.getCourtName());

        if (coords != null) {
            LatLng marker = new LatLng(coords.get(0), coords.get(1));
            mMap.addMarker(new MarkerOptions().position(marker).title(court.getCourtName()));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(marker));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker, 11));
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

    private void register() {
        KeepUser kUser = KeepUserDatabase.getDatabase(requireContext()).keepUserDao().getKeepUser();
        User u = UserDatabase.getDatabase(requireContext()).userDao().getUserByID(kUser.getUserId());
        rsvp.setVisibility(GONE);

        if (m.getPlayer2Id() == 0) {
            m.setPlayer2Id(kUser.getUserId());
            String name = u.getFirstName() + " " + u.getLastName();
            player2.setText(name);
            playerCount++;
        } else if (m.getPlayer3Id() == 0) {
            m.setPlayer3Id(kUser.getUserId());
            String name = u.getFirstName() + " " + u.getLastName();
            player3.setText(name);
            playerCount++;
        } else {
            m.setPlayer4Id(kUser.getUserId());
            String name = u.getFirstName() + " " + u.getLastName();
            player4.setText(name);
            playerCount++;
        }

        MatchDatabase.getDatabase(requireContext()).matchDao().updateMatch(m);
    }
}
