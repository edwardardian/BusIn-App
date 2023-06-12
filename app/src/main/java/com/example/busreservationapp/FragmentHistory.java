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

import com.google.firebase.auth.FirebaseAuth;
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

        fetchUserTrips();
    }

    private void fetchUserTrips() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        db.collection("trip")
                .whereEqualTo("userId", userId)
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    if (e != null) {
                        Log.w(TAG, "Listen failed.", e);
                        return;
                    }

                    historyAdapter.getTripData(userId);
                });
    }
}

