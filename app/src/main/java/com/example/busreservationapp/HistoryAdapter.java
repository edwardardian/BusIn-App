package com.example.busreservationapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private ArrayList<Trip> listTrip;
    private Context context;
    private FirebaseFirestore db;
    private FirebaseAuth auth;

    public HistoryAdapter(ArrayList<Trip> listTrip, Context context) {
        this.listTrip = listTrip;
        this.context = context;
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ticket_history, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.ViewHolder holder, int position) {
        Trip trip = listTrip.get(position);
        holder.historyBusName.setText(trip.getBusName());
        holder.historyDeparture.setText(trip.getAsal());
        holder.historyArrival.setText(trip.getTujuan());
        holder.historyPriceDisplay.setText(trip.getHarga());
        holder.historyPassengersDisplay.setText(trip.getPassengers());
        holder.historyDate.setText(trip.getDate());
    }

    @Override
    public int getItemCount() {
        return listTrip.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView historyBusName, historyDeparture, historyArrival, historyPassengersDisplay, historyPriceDisplay, historyDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            historyBusName = itemView.findViewById(R.id.history_busName);
            historyDeparture = itemView.findViewById(R.id.history_departure);
            historyArrival = itemView.findViewById(R.id.history_arrival);
            historyPassengersDisplay = itemView.findViewById(R.id.history_passenger_display);
            historyPriceDisplay = itemView.findViewById(R.id.history_price_display);
            historyDate = itemView.findViewById(R.id.history_date);
        }
    }

    public void getTripData(String userId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("trip")
                .whereEqualTo("user.userId", userId)
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
                    notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    Log.d(TAG, "Error fetching trips: " + e.getMessage());
                });
    }

}
