package com.example.busreservationapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

public class RatingHistoryActivity extends AppCompatActivity {
    private String TAG = BusScheduleActivity.class.getSimpleName();
    private RecyclerView.LayoutManager layoutManager;
    private RatingHistoryAdapter ratingHistoryAdapter;
    private FirebaseFirestore db;
    private RecyclerView recyclerView;

    private String busName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_history);

        Intent intent = getIntent();
        if (intent.hasExtra("busName")) {
            busName = intent.getStringExtra("busName");
        }

        recyclerView = findViewById(R.id.rvHistoryRate);
        layoutManager = new LinearLayoutManager(RatingHistoryActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        db = FirebaseFirestore.getInstance();

        RatingHistoryAdapter ratingHistoryAdapter = new RatingHistoryAdapter(RatingHistoryActivity.this, busName);
        recyclerView.setAdapter(ratingHistoryAdapter);
    }
}

