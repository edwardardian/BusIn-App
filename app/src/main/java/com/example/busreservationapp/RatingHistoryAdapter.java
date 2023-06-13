package com.example.busreservationapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class RatingHistoryAdapter extends RecyclerView.Adapter<RatingHistoryAdapter.ViewHolder> {
    private ArrayList<BusRating> listRating;
    private Context context;
    private FirebaseFirestore db;
    private String busName;

    public RatingHistoryAdapter(Context context, String busName) {
        this.context = context;
        db = FirebaseFirestore.getInstance();
        listRating = new ArrayList<>();
        this.busName = busName;
        fetchRatingsFromFirestore();
    }

    private void fetchRatingsFromFirestore() {
        db.collection("ratings")
                .whereEqualTo("busName", busName)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        listRating.clear();

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            BusRating busRating = document.toObject(BusRating.class);
                            listRating.add(busRating);
                        }

                        notifyDataSetChanged();
                    } else {
                    }
                });
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
        holder.busName.setText(busRating.getBusName());

        int rating = Math.round(busRating.getRatingStars());
        holder.rating.setRating(rating);
    }


    @Override
    public int getItemCount() {
        return listRating.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView userName;
        private TextView busName;
        private AppCompatRatingBar rating;
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
