package com.example.busreservationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.firestore.FirebaseFirestore;

public class BusDetailActivity extends AppCompatActivity {
    private FirebaseFirestore db;

    private BusTrip bustrip;

    private Bus bus;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_detail);

        db = FirebaseFirestore.getInstance();


    }
}