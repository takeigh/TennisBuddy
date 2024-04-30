package com.example.tennisbuddy.adapters;

import static androidx.core.content.ContentProviderCompat.requireContext;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tennisbuddy.R;
import com.example.tennisbuddy.databases.CourtDatabase;
import com.example.tennisbuddy.databases.UserDatabase;
import com.example.tennisbuddy.entities.Court;
import com.example.tennisbuddy.entities.Match;
import com.example.tennisbuddy.entities.User;
import com.example.tennisbuddy.fragments.FragmentViewMatch;
import com.example.tennisbuddy.listeners.OnClickMatchListener;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class MatchDisplayAdapter extends RecyclerView.Adapter<MatchDisplayAdapter.MyViewHolder> {

    List<Match> matchList;
    OnClickMatchListener listener;
    Context context;
    FragmentManager fragManager;

    public MatchDisplayAdapter(List<Match> matchList, OnClickMatchListener listener, Context context, FragmentManager frag) {
        this.matchList = matchList;
        this.listener = listener;
        this.context = context;
        this.fragManager = frag;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.match_display, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Match m = matchList.get(position);
        holder.bind(m, listener, context);

        holder.itemView.setOnClickListener(l -> {
            Bundle bundle = new Bundle();
            bundle.putInt("id", m.getMatchId());

            Fragment frag = FragmentViewMatch.newInstance(bundle);

            fragManager.beginTransaction()
                    .replace(R.id.fragmentContainerMain, frag)
                    .addToBackStack(null)
                    .commit();
        });
    }

    @Override
    public int getItemCount() {
        return matchList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView court;
        TextView distance;
        TextView experienceLevel;
        TextView name;
        TextView type;
        TextView date;
        TextView time;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            court = itemView.findViewById(R.id.textViewDisplayCourt);
            distance = itemView.findViewById(R.id.textViewDisplayDistance);
            experienceLevel = itemView.findViewById(R.id.textViewDisplayExperience);
            name = itemView.findViewById(R.id.textViewDisplayHost);
            type = itemView.findViewById(R.id.textViewDisplayType);
            date = itemView.findViewById(R.id.textViewDisplayDate);
            time = itemView.findViewById(R.id.textViewDisplayTime);
        }

        public void bind(Match m, OnClickMatchListener listener, Context context) {
            Court c = CourtDatabase.getDatabase(context).courtDao().getCourtById(m.getCourtId());

            court.setText(c.getAddress());
            experienceLevel.setText(m.getExperienceLevel());

            User u = UserDatabase.getDatabase(context).userDao().getUserByID(m.getHostId());
            String hostName = u.getFirstName() + " " + u.getLastName();
            name.setText(hostName);

            String length = distanceCalculation() + " miles";
            distance.setText(length);

            type.setText(m.getMatchType());

            String fullDate = m.getMonth() + "/" + m.getDay() + "/24";
            date.setText(fullDate);

            int hour = m.getHour();
            String amPm;
            if (hour > 12) {
                hour = hour - 12;
                amPm = "PM";
            } else {
                if (hour == 12) {
                    amPm = "PM";
                } else {
                    amPm = "AM";
                    if (hour == 0) {
                        hour = 12;
                    }
                }
            }
            int minute = m.getMinute();
            if (minute == 0) {
                String fullTime = hour + ":00 " + amPm;
                time.setText(fullTime);
            } else {
                String fullTime = hour + ":" + minute + " " + amPm;
                time.setText(fullTime);
            }
        }

        public Double distanceCalculation() {
            List<Double> destination = convertStringToCoordinates(court.getText().toString());

            double destLat = destination.get(0);
            double destLon = destination.get(1);

            List<Double> start = convertStringToCoordinates("Gannon University");
            double startLat = start.get(0);
            double startLon = start.get(1);

            int Radius = 3959;// radius of earth in Km
            double dLat = Math.toRadians(destLat - startLat);
            double dLon = Math.toRadians(destLon - startLon);
            double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                    + Math.cos(Math.toRadians(startLat))
                    * Math.cos(Math.toRadians(destLat)) * Math.sin(dLon / 2)
                    * Math.sin(dLon / 2);
            double c = 2 * Math.asin(Math.sqrt(a));
            double valueResult = Radius * c;

            DecimalFormat natural = new DecimalFormat("0.00");


            return Double.parseDouble(natural.format(valueResult));
        }

        public List<Double> convertStringToCoordinates(String location) {
            if (location != null) {
                Geocoder geocoder = new Geocoder(itemView.getContext(), Locale.getDefault());

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
}
