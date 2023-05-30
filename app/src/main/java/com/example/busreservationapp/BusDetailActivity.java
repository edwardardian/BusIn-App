package com.example.busreservationapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

public class BusDetailActivity extends AppCompatActivity {

    private TextView nameBusDetail;
    private TextView departure_detail;
    private TextView arrival_detail;
    private TextView departure_time_detail;
    private TextView arrival_time_detail;
    private TextView bus_station_detail_dep;
    private TextView bus_station_detail_arr;
    private TextView ticketPrice;
    private TextView trip_time_detail;
    private ImageView busPhoto;
    private Button chooseSeats;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_detail);

        nameBusDetail = findViewById(R.id.nameBusDetail);
        departure_detail = findViewById(R.id.departure_detail);
        arrival_detail = findViewById(R.id.arrival_detail);
        departure_time_detail = findViewById(R.id.departure_time_detail);
        arrival_time_detail = findViewById(R.id.arrival_time_detail);
        bus_station_detail_dep = findViewById(R.id.bus_station_detail_dep);
        bus_station_detail_arr = findViewById(R.id.bus_station_detail_arr);
        ticketPrice = findViewById(R.id.ticketPrice);
        trip_time_detail = findViewById(R.id.trip_time_detail);
        busPhoto = findViewById(R.id.imgBus);
        chooseSeats = findViewById(R.id.btnSeatChooser);

        db = FirebaseFirestore.getInstance();

        Intent intent = getIntent();
        String tvDepartureCity = intent.getStringExtra("departureCity");
        String tvArriveCity = intent.getStringExtra("arrivalCity");
        String tvDepartureHour = intent.getStringExtra("departureHour");
        String tvArriveHour = intent.getStringExtra("arrivalHour");
        String tvDepartureStation = intent.getStringExtra("departureTerminal");
        String tvArriveStation = intent.getStringExtra("arrivalTerminal");
        String tvBusName = intent.getStringExtra("busName");
        String tvPrice = intent.getStringExtra("price");
        String tvTripTime = intent.getStringExtra("time");

        departure_detail.setText(tvDepartureCity);
        arrival_detail.setText(tvArriveCity);
        departure_time_detail.setText(tvDepartureHour);
        arrival_time_detail.setText(tvArriveHour);
        bus_station_detail_dep.setText(tvDepartureStation);
        bus_station_detail_arr.setText(tvArriveStation);
        nameBusDetail.setText(tvBusName);
        ticketPrice.setText(tvPrice);
        trip_time_detail.setText(tvTripTime);

        getBusPhoto(tvBusName);

        chooseSeats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BusDetailActivity.this, SeatChooserMenuActivity.class));
                return;
            }
        });
    }

    private void getBusPhoto(String busName) {
        CollectionReference busCollection = db.collection("bus");
        Query query = busCollection.whereEqualTo("busName", busName);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot querySnapshot = task.getResult();
                    if (querySnapshot != null && !querySnapshot.isEmpty()) {
                        DocumentSnapshot document = querySnapshot.getDocuments().get(0);
                        String busPhotoUrl = document.getString("busPhoto");
                        Picasso.get().load(busPhotoUrl).into(busPhoto);
                    }
                } else {
                    Toast.makeText(BusDetailActivity.this, "Failed to get bus photo!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

