package com.example.busreservationapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.text.DecimalFormat;

public class SeatChooserMenuActivity extends AppCompatActivity {
    private String TAG = SeatChooserMenuActivity.class.getSimpleName();

    private FrameLayout container;
    private LinearLayout linearLayout;
    private Button btnBookNow;

    private final FragmentSeatChooserMenu fragmentSeatChooserMenu = new FragmentSeatChooserMenu();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_chooser_menu);

        initView();

        loadFragment(fragmentSeatChooserMenu);
    }

    private void initView() {
        container = findViewById(R.id.container);
        btnBookNow = findViewById(R.id.btnBookNow);
        btnBookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookNow();
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }

    private void bookNow() {
        AlertDialog.Builder alert = new AlertDialog.Builder(SeatChooserMenuActivity.this);
        alert.setTitle(R.string.app_name);
        alert.setMessage("Are you sure you want to book this seat/s?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FragmentSeatChooserMenu fragment = (FragmentSeatChooserMenu) getSupportFragmentManager().findFragmentById(R.id.container);
                if (fragment != null) {
                    String[] selectedSeats = fragment.getSelectedSeats();
                    if (selectedSeats != null && selectedSeats.length > 0) {
                        int seatCount = selectedSeats.length;
                        String priceString = getIntent().getStringExtra("price");

                        String cleanPriceString = priceString.replaceAll("[^\\d.]", "");
                        double price = Double.parseDouble(cleanPriceString);

                        double totalPrice = price * seatCount;

                        DecimalFormat decimalFormat = new DecimalFormat("###,###.##");
                        String formattedTotalPrice = decimalFormat.format(totalPrice);

                        Intent intent = new Intent(SeatChooserMenuActivity.this, BusDetailActivity.class);
                        intent.putExtra("selectedSeats", selectedSeats);
                        intent.putExtra("busName", getIntent().getStringExtra("busName"));
                        intent.putExtra("departureHour", getIntent().getStringExtra("departureHour"));
                        intent.putExtra("departureCity", getIntent().getStringExtra("departureCity"));
                        intent.putExtra("arrivalCity", getIntent().getStringExtra("arrivalCity"));
                        intent.putExtra("arrivalHour", getIntent().getStringExtra("arrivalHour"));
                        intent.putExtra("departureTerminal", getIntent().getStringExtra("departureTerminal"));
                        intent.putExtra("arrivalTerminal", getIntent().getStringExtra("arrivalTerminal"));
                        intent.putExtra("price", formattedTotalPrice);
                        intent.putExtra("time", getIntent().getStringExtra("time"));
                        intent.putExtra("date", getIntent().getStringExtra("date"));
                        intent.putExtra("passengers", getIntent().getStringExtra("passengers"));
                        intent.putExtra(FragmentSeatChooserMenu.EXTRA_SELECTED_SEATS, selectedSeats);
                        startActivity(intent);
                    } else {
                        Toast.makeText(SeatChooserMenuActivity.this, "Please select a seat first!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.show();
    }
}
