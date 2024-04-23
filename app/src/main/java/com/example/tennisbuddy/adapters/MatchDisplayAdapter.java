package com.example.tennisbuddy.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tennisbuddy.R;
import com.example.tennisbuddy.entities.Match;

import java.util.List;

public class MatchDisplayAdapter extends RecyclerView.Adapter<MatchDisplayAdapter.MyViewHolder> {

    private List<Match> matchList;

    public MatchDisplayAdapter(List<Match> matchList) {
        this.matchList = matchList;
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

        holder.matchCard.setCardBackgroundColor(Color.parseColor("#81EB6F"));
        // Place data from array into components
    }

    @Override
    public int getItemCount() {
        return matchList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        CardView matchCard;

        // Global components

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            matchCard = itemView.findViewById(R.id.layoutMatchDisplay);

            // Initialize components
        }
    }
}
