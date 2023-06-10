package com.example.busreservationapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class TicketDetailActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    private FirebaseAuth auth;

    private Trip trip;
    private TextView name, phoneNumber, seats, busName, seatsNumber, depatureTime, tripTime, depature, arrival,
            depaturTerminal, arrivalTerminal, date, price, bookingNumber;
    private String n, pN, s, bN, sN, dTime, tT, dC, aC, dT, aT, d, p, tripId;
    private int bookingNumberInt;
    private String[] selectedSeats;

    private Button btnBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_detail);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        name = findViewById(R.id.userInfo_name_display);
        phoneNumber = findViewById(R.id.userInfo_phone_display);
        seats = findViewById(R.id.userInfo_seat_display);
        busName = findViewById(R.id.busTicket_name_display);
        seatsNumber = findViewById(R.id.busTicket_seatNo_display);
        depatureTime = findViewById(R.id.busTicket_departTime);
        tripTime = findViewById(R.id.busTicket_estimateTime_display);
        depature = findViewById(R.id.busTicket_departure);
        arrival = findViewById(R.id.busTicket_arrival);
        depaturTerminal = findViewById(R.id.busTicket_departTerminal);
        arrivalTerminal = findViewById(R.id.busTicket_arrivalTerminal);
        date = findViewById(R.id.busTicket_departureDate);
        price = findViewById(R.id.busTicket_totalPrice_display);
        bookingNumber = findViewById(R.id.booking_number);
        btnBack = findViewById(R.id.btnBack);

        Intent intent = getIntent();
        tripId = intent.getStringExtra("tripId");

        selectedSeats = intent.getStringArrayExtra(FragmentSeatChooserMenu.EXTRA_SELECTED_SEATS);
        if (selectedSeats != null && selectedSeats.length > 0) {
            StringBuilder seatNumbers = new StringBuilder();
            for (String seat : selectedSeats) {
                seatNumbers.append(seat).append(", ");
            }
            seatNumbers.deleteCharAt(seatNumbers.length() - 2);
            seatsNumber.setText(seatNumbers.toString());
        }

        getUserData();
        getTripData();
    }

    private void getUserData() {
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            db.collection("users")
                    .document(userId)
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            String userName = documentSnapshot.getString("name");
                            String userPhoneNumber = documentSnapshot.getString("phoneNumber");

                            name.setText(userName);
                            phoneNumber.setText(userPhoneNumber);
                        }
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(TicketDetailActivity.this, "Gagal mengambil data pengguna", Toast.LENGTH_SHORT).show();
                    });
        }
    }

    private void getTripData() {
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            db.collection("trip")
                    .whereEqualTo("user.email", user.getEmail())
                    .orderBy("bookingNumber")
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                Trip trip = documentSnapshot.toObject(Trip.class);
                                if (trip != null) {
                                    dC = trip.getAsal();
                                    aC = trip.getTujuan();
                                    dTime = trip.getTimeDeparture();
                                    dT = trip.getDepartureTerminal();
                                    aT = trip.getArrivalTerminal();
                                    bN = trip.getBusName();
                                    p = trip.getHarga();
                                    tT = trip.getWaktu();
                                    d = trip.getDate();
                                    s = trip.getPassengers();
                                    bookingNumberInt = trip.getBookingNumber();
                                    Log.d("TicketDetailActivity", "Bus Name: " + trip.getBusName());
                                    Log.d("TicketDetailActivity", "Passengers: " + trip.getPassengers());

                                    seats.setText(s);
                                    busName.setText(bN);
                                    depatureTime.setText(dTime);
                                    depature.setText(dC);
                                    tripTime.setText(tT);
                                    depaturTerminal.setText(dT);
                                    date.setText(d);
                                    arrival.setText(aC);
                                    arrivalTerminal.setText(aT);
                                    price.setText(p);
                                    bookingNumber.setText("Booking Number: " + Integer.toString(bookingNumberInt));

                                    getSelectedSeatsFromFirestore(documentSnapshot);

                                    btnBack.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent(TicketDetailActivity.this, RatingActivity.class);
                                            intent.putExtra("busName", trip.getBusName());
                                            intent.putExtra("passengers", trip.getPassengers());
                                            intent.putExtra("date", trip.getDate());
                                            startActivity(intent);
                                            finish();
                                        }
                                    });
                                }
                            }
                        } else {
                            Toast.makeText(TicketDetailActivity.this, "No trip data found for the current user", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(TicketDetailActivity.this, "Failed to get trip data!", Toast.LENGTH_SHORT).show();
                    });
        }
    }

    private void getSelectedSeatsFromFirestore(DocumentSnapshot tripSnapshot) {
        if (tripSnapshot.contains("selectedSeats")) {
            List<String> selectedSeatsList = (List<String>) tripSnapshot.get("selectedSeats");
            if (selectedSeatsList != null && !selectedSeatsList.isEmpty()) {
                StringBuilder seatNumbers = new StringBuilder();
                for (String seat : selectedSeatsList) {
                    seatNumbers.append(seat).append(", ");
                }
                seatNumbers.deleteCharAt(seatNumbers.length() - 2);
                seatsNumber.setText(seatNumbers.toString());
            }
        }
    }
}
