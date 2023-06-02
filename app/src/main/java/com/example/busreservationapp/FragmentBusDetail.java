package com.example.busreservationapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.HashMap;

public class FragmentBusDetail extends Fragment {
    private String TAG = FragmentBusDetail.class.getSimpleName();

    private TextView nameBusDetail;
    private TextView departure_detail;
    private TextView arrival_detail;
    private TextView departure_time_detail;
    private TextView arrival_time_detail;
    private TextView bus_station_detail_dep;
    private TextView bus_station_detail_arr;
    private TextView ticketPrice;
    private TextView trip_time_detail;
    private TextView passengers_detail;
    private TextView date_detail;
    private ImageView busPhoto;
    private Button chooseSeats;
    private Button btnBookNow;
    private FirebaseFirestore db;

    private DocumentSnapshot selectedBus;

    private double ticketPriceValue;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = layoutInflater.inflate(R.layout.fragment_bus_detail, container, false);
        initView(view);

        return view;
    }

    public void initView(View view) {
        nameBusDetail = view.findViewById(R.id.nameBusDetail);
        departure_detail = view.findViewById(R.id.departure_detail);
        arrival_detail = view.findViewById(R.id.arrival_detail);
        departure_time_detail = view.findViewById(R.id.departure_time_detail);
        arrival_time_detail = view.findViewById(R.id.arrival_time_detail);
        bus_station_detail_dep = view.findViewById(R.id.bus_station_detail_dep);
        bus_station_detail_arr = view.findViewById(R.id.bus_station_detail_arr);
        ticketPrice = view.findViewById(R.id.ticketPrice);
        trip_time_detail = view.findViewById(R.id.trip_time_detail);
        passengers_detail = view.findViewById(R.id.passengers_detail);
        busPhoto = view.findViewById(R.id.imgBus);
        chooseSeats = view.findViewById(R.id.btnSeatChooser);
        btnBookNow = view.findViewById(R.id.btnBookNow);
        date_detail = view.findViewById(R.id.date_detail);

        db = FirebaseFirestore.getInstance();

        Intent intent = getActivity().getIntent();
        String tvDepartureCity = intent.getStringExtra("departureCity");
        String tvArriveCity = intent.getStringExtra("arrivalCity");
        String tvDepartureHour = intent.getStringExtra("departureHour");
        String tvArriveHour = intent.getStringExtra("arrivalHour");
        String tvDepartureStation = intent.getStringExtra("departureTerminal");
        String tvArriveStation = intent.getStringExtra("arrivalTerminal");
        String tvBusName = intent.getStringExtra("busName");
        String tvPrice = getActivity().getIntent().getStringExtra("price");
        String tvTripTime = intent.getStringExtra("time");
        String tvPriceString = tvPrice.replaceAll("[^\\d.]", "");
        String date = intent.getStringExtra("date");
        String passengers = intent.getStringExtra("passengers");

        try {
            double price = Double.parseDouble(tvPriceString);
            ticketPriceValue = price;
            DecimalFormat decimalFormat = new DecimalFormat("###,###");
            String formattedPrice = "Rp" + decimalFormat.format(ticketPriceValue);
            ticketPrice.setText(formattedPrice);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            ticketPrice.setText("Invalid Price");
        }

        departure_detail.setText(tvDepartureCity);
        arrival_detail.setText(tvArriveCity);
        departure_time_detail.setText(tvDepartureHour);
        arrival_time_detail.setText(tvArriveHour);
        bus_station_detail_dep.setText(tvDepartureStation);
        bus_station_detail_arr.setText(tvArriveStation);
        nameBusDetail.setText(tvBusName);
        trip_time_detail.setText(tvTripTime);
        date_detail.setText(date);
        passengers_detail.setText(passengers);

        getBusPhoto(tvBusName);

        chooseSeats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SeatChooserMenuActivity.class);
                intent.putExtra("departureCity", departure_detail.getText().toString());
                intent.putExtra("arrivalCity", arrival_detail.getText().toString());
                intent.putExtra("departureHour", departure_time_detail.getText().toString());
                intent.putExtra("arrivalHour", arrival_time_detail.getText().toString());
                intent.putExtra("departureTerminal", bus_station_detail_dep.getText().toString());
                intent.putExtra("arrivalTerminal", bus_station_detail_arr.getText().toString());
                intent.putExtra("busName", nameBusDetail.getText().toString());
                intent.putExtra("price", ticketPrice.getText().toString());
                intent.putExtra("time", trip_time_detail.getText().toString());
                intent.putExtra("date", date_detail.getText().toString());
                intent.putExtra("passengers", passengers_detail.getText().toString());
                startActivity(intent);
            }
        });

        btnBookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle("Confirmation");
                alert.setMessage("Are you sure you want to book this?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String departureCity = departure_detail.getText().toString();
                        String arrivalCity = arrival_detail.getText().toString();
                        String departureHour = departure_time_detail.getText().toString();
                        String arrivalHour = arrival_time_detail.getText().toString();
                        String departureTerminal = bus_station_detail_dep.getText().toString();
                        String arrivalTerminal = bus_station_detail_arr.getText().toString();
                        String busName = nameBusDetail.getText().toString();
                        String price = ticketPrice.getText().toString();
                        String tripTime = trip_time_detail.getText().toString();
                        String date = date_detail.getText().toString();
                        String passengers = passengers_detail.getText().toString();

                        saveDataToFirestore(departureCity, arrivalCity, departureHour, arrivalHour, departureTerminal, arrivalTerminal, busName, price, tripTime, passengers, date);
                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alert.show();
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
                        selectedBus = querySnapshot.getDocuments().get(0);
                        String busPhotoUrl = selectedBus.getString("busPhoto");
                        Picasso.get().load(busPhotoUrl).into(busPhoto);
                    }
                } else {
                    Toast.makeText(getActivity(), "Failed to get bus photo!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void saveDataToFirestore(String departureCity, String arrivalCity, String departureHour, String arrivalHour,
                                     String departureTerminal, String arrivalTerminal, String busName, String price, String date, String tripTime, String passengers) {


        Trip trip = new Trip(busName, departureCity, arrivalCity, price, departureTerminal, arrivalTerminal, departureHour, arrivalHour, passengers, date, tripTime);


        CollectionReference tripCollection = db.collection("trip");

        tripCollection.add(trip)
                .addOnSuccessListener(documentReference -> {
                    String tripId = documentReference.getId();
                    Intent intent = new Intent(getActivity(), PaymentDetailActivity.class);
                    intent.putExtra("tripId", tripId);
                    startActivity(intent);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getActivity(), "Failed to save trip data to Firestore", Toast.LENGTH_SHORT).show();
                });
    }
}
