package com.example.busreservationapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BusScheduleAdapter extends RecyclerView.Adapter<BusScheduleAdapter.ViewHolder> {
    private List<Trip> tripList;

    public BusScheduleAdapter(List<Trip> tripList) {
        this.tripList = tripList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ticket_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Trip trip = tripList.get(position);
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
        return tripList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvBusName;
        TextView tvTripTime;
        TextView tvDepartureHour;
        TextView tvDepartureStation;
        TextView tvDepartureCity;
        TextView tvArriveHour;
        TextView tvArriveStation;
        TextView tvArriveCity;
        TextView tvPrice;

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
