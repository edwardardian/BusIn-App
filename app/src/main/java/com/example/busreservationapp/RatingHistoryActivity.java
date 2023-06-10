package com.example.busreservationapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

public class RatingHistoryActivity extends AppCompatActivity {
    private String TAG = BusScheduleActivity.class.getSimpleName();
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private RatingHistoryAdapter ratingHistoryAdapter;
    private FirebaseFirestore db;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_history);

        layoutManager = new LinearLayoutManager(RatingHistoryActivity.this);

        db = FirebaseFirestore.getInstance();

        recyclerView.setLayoutManager(layoutManager);
    }
}
