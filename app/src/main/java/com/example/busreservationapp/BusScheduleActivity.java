package com.example.busreservationapp;

import static android.content.ContentValues.TAG;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.slider.Slider;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class BusScheduleActivity extends AppCompatActivity {
    private Spinner departureSpinner;
    private Spinner arrivalSpinner;

    private EditText datePicker;

    private TextView seats;

    private FirebaseFirestore db;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_schedule);

        departureSpinner = findViewById(R.id.spinnerDeparture);
        arrivalSpinner = findViewById(R.id.spinnerArrival);
        datePicker = findViewById(R.id.date);
        seats = findViewById(R.id.seats);
        recyclerView = findViewById(R.id.rvTrip);

        db = FirebaseFirestore.getInstance();

        Intent intent = getIntent();
        String departure = intent.getStringExtra("departure");
        String arrival = intent.getStringExtra("arrival");
        String date = intent.getStringExtra("date");
        String passengers = getIntent().getStringExtra("passengers");

        seats.setText("Seat " + passengers);

        Spinner spinnerDeparture = findViewById(R.id.spinnerDeparture);
        ArrayAdapter<CharSequence> departureAdapter = ArrayAdapter.createFromResource(this, R.array.Asal, android.R.layout.simple_spinner_item);
        departureAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDeparture.setAdapter(departureAdapter);

        if (departure != null) {
            int departurePosition = departureAdapter.getPosition(departure);
            spinnerDeparture.setSelection(departurePosition);
        }

        Spinner spinnerArrival = findViewById(R.id.spinnerArrival);
        ArrayAdapter<CharSequence> arrivalAdapter = ArrayAdapter.createFromResource(this, R.array.Tujuan, android.R.layout.simple_spinner_item);
        arrivalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerArrival.setAdapter(arrivalAdapter);

        if (arrival != null) {
            int arrivalPosition = arrivalAdapter.getPosition(arrival);
            spinnerArrival.setSelection(arrivalPosition);
        }

        if (date != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.UK);
            try {
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.setTime(dateFormat.parse(date));
                datePicker.setText(dateFormat.format(selectedDate.getTime()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        getData();
    }

    private void getData() {
        CollectionReference busScheduleCollection = db.collection("surabaya-malang");

        Query query = busScheduleCollection.whereEqualTo("departureCity", "Surabaya").whereEqualTo("arrivalCity", "Malang");

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<Trip> busSchedules = new ArrayList<>();

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String departureCity = document.getString("departureCity");
                        String arrivalCity = document.getString("arrivalCity");
                        String departureHour = document.getString("departureHour");
                        String arrivalHour = document.getString("arrivalHour");
                        String departureTerminal = document.getString("departureTerminal");
                        String arrivalTerminal = document.getString("arrivalTerminal");
                        String busName = document.getString("busName");
                        String price = document.getString("price");
                        String time = document.getString("time");

                        Trip trip = new Trip(departureCity, arrivalCity, departureHour, arrivalHour, departureTerminal, arrivalTerminal, busName, price, time);

                        busSchedules.add(trip);
                    }

                    BusScheduleAdapter adapter = new BusScheduleAdapter(busSchedules);
                    recyclerView.setAdapter(adapter);
                } else {
                    Log.d(TAG, "Error getting bus schedules: ", task.getException());
                }
            }
        });
    }

}