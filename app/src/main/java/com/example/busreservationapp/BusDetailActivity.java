package com.example.busreservationapp;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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
    private Button btnBookNow;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_detail);

        nameBusDetail =  findViewById(R.id.nameBusDetail);
        departure_detail = findViewById(R.id.departure_detail);
        arrival_detail = findViewById(R.id.arrival_detail);
        departure_time_detail = findViewById(R.id.departure_time_detail);
        arrival_time_detail = findViewById(R.id.arrival_time_detail);
        bus_station_detail_dep = findViewById(R.id.bus_station_detail_dep);
        bus_station_detail_arr = findViewById(R.id.bus_station_detail_arr);
        ticketPrice = findViewById(R.id.ticketPrice);
        trip_time_detail = findViewById(R.id.trip_time_detail);
        busPhoto = findViewById(R.id.imgBus);
        btnBookNow = findViewById(R.id.btnBookNow);

        // Get the data from the intent
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



        // Set the data in the views
        departure_detail.setText(tvDepartureCity);
        arrival_detail.setText(tvArriveCity);
        departure_time_detail.setText(tvDepartureHour);
        arrival_time_detail.setText(tvArriveHour);
        bus_station_detail_dep.setText(tvDepartureStation);
        bus_station_detail_arr.setText(tvArriveStation);
        nameBusDetail.setText(tvBusName);
        ticketPrice.setText(tvPrice);
        trip_time_detail.setText(tvTripTime);


    }
}
