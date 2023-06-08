package com.example.busreservationapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class RatingActivity extends AppCompatActivity {
    private TextView rating_busName_display;
    private TextView rating_passenger_display;
    private TextView rating_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        // Mendapatkan data yang dikirim dari TicketDetailActivity
        Intent intent = getIntent();
        String busName = intent.getStringExtra("busName");
        String passengers = intent.getStringExtra("passengers");
        String date = intent.getStringExtra("date");

        // Menginisialisasi TextView pada layout RatingActivity
        rating_busName_display = findViewById(R.id.rating_busName_display);
        rating_passenger_display = findViewById(R.id.rating_passenger_display);
        rating_date = findViewById(R.id.rating_date);

        // Menampilkan data yang diterima dari TicketDetailActivity
        rating_busName_display.setText(busName);
        rating_passenger_display.setText(passengers);
        rating_date.setText(date);
    }
}