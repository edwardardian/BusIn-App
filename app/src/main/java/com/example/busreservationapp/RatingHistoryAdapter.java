package com.example.busreservationapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class RatingHistoryAdapter extends RecyclerView.Adapter<RatingHistoryAdapter.ViewHolder> {
    private ArrayList<BusRating> listRating;
    private Context context;
    private FirebaseFirestore db;

    public RatingHistoryAdapter(ArrayList<BusRating> listRating, Context context) {
        this.listRating = listRating;
        this.context = context;
    }

    @NonNull
    @Override
    public RatingHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rating_layout, parent, false);
        return new RatingHistoryAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RatingHistoryAdapter.ViewHolder holder, int position) {
        BusRating busRating = listRating.get(position);
        holder.userName.setText(busRating.getUserName());
        holder.comment.setText(busRating.getRatingText());
    }


    @Override
    public int getItemCount() {
        return listRating.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView userName;
        private TextView busName;
        private TextView rating;
        private TextView comment;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.userName_rateHistory);
            busName = itemView.findViewById(R.id.busName_rateHistory);
            rating = itemView.findViewById(R.id.ratingBar_rateHistory);
            comment = itemView.findViewById(R.id.comment_rateHistory);
        }
    }

}
