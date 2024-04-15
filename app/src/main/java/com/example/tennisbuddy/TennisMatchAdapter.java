//package com.example.tennisbuddy;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.tennisbuddy.R;
//
//import java.util.List;
//
//public class TennisMatchAdapter extends RecyclerView.Adapter<TennisMatchAdapter.TennisMatchViewHolder> {
//
//    private List<TennisMatch> tennisMatches;
//    private OnItemClickListener listener;
//
//    public TennisMatchAdapter(List<TennisMatch> tennisMatches) {
//        this.tennisMatches = tennisMatches;
//    }
//
//    public interface OnItemClickListener {
//        void onItemClick(TennisMatch item);
//    }
//
//    public void setOnItemClickListener(OnItemClickListener listener) {
//        this.listener = listener;
//    }
//
//    @NonNull
//    @Override
//    public TennisMatchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.matchlistview, parent, false);
//        return new TennisMatchViewHolder(view, listener);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull TennisMatchViewHolder holder, int position) {
//        TennisMatch tennisMatch = tennisMatches.get(position);
//        holder.dateTextView.setText(tennisMatch.getDate());
//        holder.timeTextView.setText(tennisMatch.getTime());
//        holder.locationTextView.setText(tennisMatch.getLocation());
//        holder.experienceLevelTextView.setText(tennisMatch.getExperienceLevel());
//    }
//
//    @Override
//    public int getItemCount() {
//        return tennisMatches.size();
//    }
//
//    class TennisMatchViewHolder extends RecyclerView.ViewHolder {
//        TextView dateTextView;
//        TextView timeTextView;
//        TextView locationTextView;
//        TextView experienceLevelTextView;
//
//        public TennisMatchViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
//            super(itemView);
//            dateTextView = itemView.findViewById(R.id.date);
//            timeTextView = itemView.findViewById(R.id.time);
//            locationTextView = itemView.findViewById(R.id.location);
//            experienceLevelTextView = itemView.findViewById(R.id.experience_level);
//
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (listener != null) {
//                        int position = getAdapterPosition();
//                        if (position != RecyclerView.NO_POSITION) {
//                            listener.onItemClick(tennisMatches.get(position));
//                        }
//                    }
//                }
//            });
//        }
//    }
//}