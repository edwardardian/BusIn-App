package com.example.busreservationapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class BusScheduleAdapter extends RecyclerView.Adapter<BusScheduleAdapter.ViewHolder> {
    private ArrayList<Trip> listTrip;
    private Context context;

    public BusScheduleAdapter(ArrayList<Trip> listTrip, Context context){
        this.listTrip = listTrip;
        this.context = context;
    }

    @NonNull
    @Override
    public BusScheduleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ticket_layout, parent, false);
        BusScheduleAdapter.ViewHolder viewHolder = new BusScheduleAdapter.ViewHolder(v);
        return viewHolder;
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Trip trip = listTrip.get(position);
        holder.tvBusName.setText(trip.getBusName());
        holder.tvTripTime.setText(trip.getWaktu());
        holder.tvDepartureHour.setText(trip.getTimeDeparture());
        holder.tvDepartureStation.setText(trip.getDepartTerminal());
        holder.tvDepartureCity.setText(trip.getAsal());
        holder.tvArriveHour.setText(trip.getTimeArrival());
        holder.tvArriveStation.setText(trip.getArrivalTerminal());
        holder.tvArriveCity.setText(trip.getTujuan());
        holder.tvPrice.setText(trip.getHarga());
    }

    @Override
    public int getItemCount() {
        return listTrip.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvBusName;
        private TextView tvTripTime;
        private TextView tvDepartureHour;
        private TextView tvDepartureStation;
        private TextView tvDepartureCity;
        private TextView tvArriveHour;
        private TextView tvArriveStation;
        private TextView tvArriveCity;
        private TextView tvPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBusName = itemView.findViewById(R.id.tvBusName);
            tvTripTime = itemView.findViewById(R.id.tvTripTime);
            tvDepartureHour = itemView.findViewById(R.id.tvDepartureHour);
            tvDepartureStation = itemView.findViewById(R.id.tvDepartureStation);
            tvDepartureCity = itemView.findViewById(R.id.tvDepartureCity);
            tvArriveHour = itemView.findViewById(R.id.tvArriveHour);
            tvArriveStation = itemView.findViewById(R.id.tvArriveStation);
            tvArriveCity = itemView.findViewById(R.id.tvArriveCity);
            tvPrice = itemView.findViewById(R.id.tvPrice);
        }
    }
}
