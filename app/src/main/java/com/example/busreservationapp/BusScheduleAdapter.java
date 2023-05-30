package com.example.busreservationapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class BusScheduleAdapter extends RecyclerView.Adapter<BusScheduleAdapter.ViewHolder> {
    private ArrayList<Trip> listTrip;
    private Context context;

    private RecyclerView rvTrip;

    public BusScheduleAdapter(ArrayList<Trip> listTrip, Context context){
        this.listTrip = listTrip;
        this.context = context;
    }

    @NonNull
    @Override
    public BusScheduleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ticket_layout, parent, false);
        return new ViewHolder(v);
    }


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
        holder.btnBookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, BusDetailActivity.class);
                intent.putExtra("busId", trip.getBusId());
                intent.putExtra("departureCity", trip.getAsal());
                intent.putExtra("busName", trip.getBusName());
                intent.putExtra("arrivalCity", trip.getTujuan());
                intent.putExtra("departureTerminal", trip.getDepartTerminal());
                intent.putExtra("arrivalTerminal", trip.getArrivalTerminal());
                intent.putExtra("departureHour", trip.getTimeDeparture());
                intent.putExtra("arrivalHour", trip.getTimeArrival());
                intent.putExtra("time", trip.getWaktu());
                intent.putExtra("price", trip.getHarga());

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
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
        private Button btnBookNow;

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
            btnBookNow = itemView.findViewById(R.id.btnBookNow);
        }


    }
}


