package com.example.busreservationapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

public class TicketDetailActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    private String departureCity, arrivalCity, departureHour, arrivalHour, departureTerminal, arrivalTerminal, busName, price,
            tripTime, date, passengers, tripId;
    private Trip trip;
    private TextView name, phoneNumber, seats;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_detail);


    }
}
