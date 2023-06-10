package com.example.busreservationapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class FragmentHistory extends Fragment {
    private String TAG = FragmentHistory.class.getSimpleName();

    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    private ArrayList<Trip> listTrip;
    private HistoryAdapter historyAdapter;
    private FirebaseFirestore db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.rvTicket);
        progressBar = view.findViewById(R.id.progressBar);
        db = FirebaseFirestore.getInstance();

        listTrip = new ArrayList<>();

        historyAdapter = new HistoryAdapter(listTrip, requireContext());

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(historyAdapter);

        db.collection("trip")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    listTrip.clear();

                    List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();

                    for (DocumentSnapshot document : documents) {
                        String busName = document.getString("busName");
                        String asal = document.getString("asal");
                        String tujuan = document.getString("tujuan");
                        String date = document.getString("date");
                        String harga = document.getString("harga");
                        String passengers = document.getString("passengers");

                        Trip trip = new Trip();
                        trip.setBusName(busName);
                        trip.setAsal(asal);
                        trip.setTujuan(tujuan);
                        trip.setDate(date);
                        trip.setHarga(harga);
                        trip.setPassengers(passengers);

                        listTrip.add(trip);
                    }
                    historyAdapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                })
                .addOnFailureListener(e -> {

                    progressBar.setVisibility(View.GONE);
                });
    }


}
