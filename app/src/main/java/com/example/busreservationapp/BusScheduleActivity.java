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
import android.widget.ListView;
import android.widget.ProgressBar;
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
    private String TAG = BusScheduleActivity.class.getSimpleName();
    private TextView departureSpinner, arrivalSpinner, datePicker, seats;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private ArrayList<Trip> listTrip;
    private BusScheduleAdapter busScheduleAdapter;

    private FirebaseFirestore db;

    private RecyclerView recyclerView;

    private ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_schedule);

        departureSpinner = findViewById(R.id.spinnerDeparture);
        arrivalSpinner = findViewById(R.id.spinnerArrival);
        datePicker = findViewById(R.id.date);
        seats = findViewById(R.id.seats);
        recyclerView = findViewById(R.id.rvTrip);
        progressBar = findViewById(R.id.progressBar);

        layoutManager = new LinearLayoutManager(BusScheduleActivity.this);

        db = FirebaseFirestore.getInstance();

        Intent intent = getIntent();
        String departure = intent.getStringExtra("departure");
        String arrival = intent.getStringExtra("arrival");
        String date = intent.getStringExtra("date");
        String passengers = intent.getStringExtra("passengers");

        recyclerView.setLayoutManager(layoutManager);

        departureSpinner.setText(departure);
        arrivalSpinner.setText(arrival);
        datePicker.setText(date);
        seats.setText(passengers);

        getData();
    }

    private void getData() {
        progressBar(true);
        if (departureSpinner.getText().equals("Surabaya") && arrivalSpinner.getText().equals("Malang")) {
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

                            Trip trip = new Trip(busName, departureCity, arrivalCity, price, departureTerminal, arrivalTerminal, departureHour, arrivalHour, time);

                            busSchedules.add(trip);
                            listTrip = new ArrayList<>();
                            listTrip.addAll(busSchedules);

                            adapter = new BusScheduleAdapter(listTrip, getBaseContext());
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.GONE);
                        }
                    } else {
                        Log.d(TAG, "Error getting bus schedules: ", task.getException());
                        progressBar.setVisibility(View.GONE);
                    }
                }
            });
        } else if (departureSpinner.getText().equals("Malang") && arrivalSpinner.getText().equals("Surabaya")) {
            CollectionReference busScheduleCollection = db.collection("malang-surabaya");
            Query query = busScheduleCollection.whereEqualTo("departureCity", "Malang").whereEqualTo("arrivalCity", "Surabaya");

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

                            Trip trip = new Trip(busName, departureCity, arrivalCity, price, departureTerminal, arrivalTerminal, departureHour, arrivalHour, time);

                            busSchedules.add(trip);
                            listTrip = new ArrayList<>();
                            listTrip.addAll(busSchedules);

                            adapter = new BusScheduleAdapter(listTrip, getBaseContext());
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.GONE);
                        }
                    } else {
                        Log.d(TAG, "Error getting bus schedules: ", task.getException());
                        progressBar.setVisibility(View.GONE);
                    }
                }
            });
        } else if (departureSpinner.getText().equals("Surabaya") && arrivalSpinner.getText().equals("Jakarta")) {
            CollectionReference busScheduleCollection = db.collection("surabaya-jakarta");
            Query query = busScheduleCollection.whereEqualTo("departureCity", "Surabaya").whereEqualTo("arrivalCity", "Jakarta");

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

                            Trip trip = new Trip(busName, departureCity, arrivalCity, price, departureTerminal, arrivalTerminal, departureHour, arrivalHour, time);

                            busSchedules.add(trip);
                            listTrip = new ArrayList<>();
                            listTrip.addAll(busSchedules);

                            adapter = new BusScheduleAdapter(listTrip, getBaseContext());
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.GONE);
                        }
                    } else {
                        Log.d(TAG, "Error getting bus schedules: ", task.getException());
                        progressBar.setVisibility(View.GONE);
                    }
                }
            });
        } else if (departureSpinner.getText().equals("Jakarta") && arrivalSpinner.getText().equals("Surabaya")) {
                CollectionReference busScheduleCollection = db.collection("jakarta-surabaya");
                Query query = busScheduleCollection.whereEqualTo("departureCity", "Jakarta").whereEqualTo("arrivalCity", "Surabaya");

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

                                Trip trip = new Trip(busName, departureCity, arrivalCity, price, departureTerminal, arrivalTerminal, departureHour, arrivalHour, time);

                                busSchedules.add(trip);
                                listTrip = new ArrayList<>();
                                listTrip.addAll(busSchedules);

                                adapter = new BusScheduleAdapter(listTrip, getBaseContext());
                                recyclerView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);
                            }
                        } else {
                            Log.d(TAG, "Error getting bus schedules: ", task.getException());
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
        } else if (departureSpinner.getText().equals("Malang") && arrivalSpinner.getText().equals("Jakarta")) {
            CollectionReference busScheduleCollection = db.collection("malang-jakarta");
            Query query = busScheduleCollection.whereEqualTo("departureCity", "Malang").whereEqualTo("arrivalCity", "Jakarta");

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

                            Trip trip = new Trip(busName, departureCity, arrivalCity, price, departureTerminal, arrivalTerminal, departureHour, arrivalHour, time);

                            busSchedules.add(trip);
                            listTrip = new ArrayList<>();
                            listTrip.addAll(busSchedules);

                            adapter = new BusScheduleAdapter(listTrip, getBaseContext());
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.GONE);
                        }
                    } else {
                        Log.d(TAG, "Error getting bus schedules: ", task.getException());
                        progressBar.setVisibility(View.GONE);
                    }
                }
            });
        } else if (departureSpinner.getText().equals("Jakarta") && arrivalSpinner.getText().equals("Malang")) {
            CollectionReference busScheduleCollection = db.collection("jakarta-malang");
            Query query = busScheduleCollection.whereEqualTo("departureCity", "Jakarta").whereEqualTo("arrivalCity", "Malang");

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

                            Trip trip = new Trip(busName, departureCity, arrivalCity, price, departureTerminal, arrivalTerminal, departureHour, arrivalHour, time);

                            busSchedules.add(trip);
                            listTrip = new ArrayList<>();
                            listTrip.addAll(busSchedules);

                            adapter = new BusScheduleAdapter(listTrip, getBaseContext());
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.GONE);
                        }
                    } else {
                        Log.d(TAG, "Error getting bus schedules: ", task.getException());
                        progressBar.setVisibility(View.GONE);
                    }
                }
            });
        }
    }

    private void progressBar(boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

}
