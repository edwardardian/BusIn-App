package com.example.busreservationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import com.example.busreservationapp.User;

import java.util.List;

public class PaymentDetailActivity extends AppCompatActivity {
    private FirebaseFirestore db;

    private FirebaseAuth auth;

    private Trip trip;

    private String[] selectedSeats;

    private String departureCity, arrivalCity, departureHour, arrivalHour, departureTerminal, arrivalTerminal, busName, price, tripTime, date, passengers, tripId;

    private TextView tvUserName, tvUserPhoneNumber, tvUserSeats, tvUserBusName, tvUserDepartureTime, tvDepartureCity, tvUserEstimatedTime, tvDepartureTerminal,
                     tvDepartureDate, tvArrivalTime, tvArrivalCity, seatNo, tvArrivalTerminal, tvArrivalDate, tvUserTicket, tvUserPriceTotal;

    private Button btnPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_detail);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        tvUserName = findViewById(R.id.tvUserName);
        tvUserPhoneNumber = findViewById(R.id.tvUserPhoneNumber);
        tvUserSeats = findViewById(R.id.tvUserSeats);
        tvUserBusName = findViewById(R.id.tvUserBusName);
        tvUserDepartureTime = findViewById(R.id.tvUserDepartureTime);
        tvDepartureCity = findViewById(R.id.tvDepartureCity);
        tvUserEstimatedTime = findViewById(R.id.tvUserEstimatedTime);
        tvDepartureTerminal = findViewById(R.id.tvDepartureTerminal);
        tvDepartureDate = findViewById(R.id.tvDepartureDate);
        tvArrivalTime = findViewById(R.id.tvArrivalTime);
        tvArrivalCity = findViewById(R.id.tvArrivalCity);
        seatNo = findViewById(R.id.seatNo);
        tvArrivalTerminal = findViewById(R.id.tvArrivalTerminal);
        tvArrivalDate = findViewById(R.id.tvArrivalDate);
        tvUserTicket = findViewById(R.id.tvUserTicket);
        tvUserPriceTotal = findViewById(R.id.tvUserPriceTotal);
        btnPayment = findViewById(R.id.btnPayment);

        Intent intent = getIntent();
        tripId = intent.getStringExtra("tripId");

        selectedSeats = intent.getStringArrayExtra(FragmentSeatChooserMenu.EXTRA_SELECTED_SEATS);
        if (selectedSeats != null && selectedSeats.length > 0) {
            StringBuilder seatNumbers = new StringBuilder();
            for (String seat : selectedSeats) {
                seatNumbers.append(seat).append(", ");
            }
            seatNumbers.deleteCharAt(seatNumbers.length() - 2);
            seatNo.setText(seatNumbers.toString());
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
                            String userPhoto = documentSnapshot.getString("photo");

                            tvUserName.setText(userName);
                            tvUserPhoneNumber.setText(userPhoneNumber);

                            db.collection("trip")
                                    .document(tripId)
                                    .update("user", new User(userName, userId, user.getEmail(), userPhoneNumber, userPhoto))
                                    .addOnSuccessListener(aVoid -> {

                                    })
                                    .addOnFailureListener(e -> {

                                    });
                        }
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(PaymentDetailActivity.this, "Gagal mengambil data pengguna", Toast.LENGTH_SHORT).show();
                    });
        }
    }

    private void getTripData() {
        db.collection("trip")
                .document(tripId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        departureCity = documentSnapshot.getString("asal");
                        arrivalCity = documentSnapshot.getString("tujuan");
                        departureHour = documentSnapshot.getString("timeDeparture");
                        arrivalHour = documentSnapshot.getString("timeArrival");
                        departureTerminal = documentSnapshot.getString("departureTerminal");
                        arrivalTerminal = documentSnapshot.getString("arrivalTerminal");
                        busName = documentSnapshot.getString("busName");
                        price = documentSnapshot.getString("harga");
                        tripTime = documentSnapshot.getString("waktu");
                        date = documentSnapshot.getString("date");
                        passengers = documentSnapshot.getString("passengers");

                        tvUserSeats.setText(passengers);
                        tvUserBusName.setText(busName);
                        tvUserDepartureTime.setText(departureHour);
                        tvDepartureCity.setText(departureCity);
                        tvUserEstimatedTime.setText(tripTime);
                        tvDepartureTerminal.setText(departureTerminal);
                        tvDepartureDate.setText(date);
                        tvArrivalTime.setText(arrivalHour);
                        tvArrivalCity.setText(arrivalCity);
                        tvArrivalTerminal.setText(arrivalTerminal);
                        tvArrivalDate.setText(date);
                        tvUserPriceTotal.setText(price);

                        btnPayment.setOnClickListener(v -> {
                            Intent intent = new Intent(PaymentDetailActivity.this, PaymentMethodActivity.class);
                            intent.putExtra("price", price);
                            startActivity(intent);
                        });

                        String passengersCount = passengers;
                        String ticketText = passengersCount;
                        tvUserTicket.setText(ticketText);

                        getSelectedSeatsFromFirestore(documentSnapshot);
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(PaymentDetailActivity.this, "Failed to get trip data!", Toast.LENGTH_SHORT).show();
                });
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
                seatNo.setText(seatNumbers.toString());
            }
        }
    }
}