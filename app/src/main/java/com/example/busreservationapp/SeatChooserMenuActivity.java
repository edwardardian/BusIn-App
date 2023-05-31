package com.example.busreservationapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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

    private void loadFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }

    private void bookNow() {
        FragmentSeatChooserMenu fragment = (FragmentSeatChooserMenu) getSupportFragmentManager().findFragmentById(R.id.container);
        if (fragment != null) {
            String[] selectedSeats = fragment.getSelectedSeats();

            if (selectedSeats.length > 0) {
                Intent intent = new Intent(SeatChooserMenuActivity.this, BusDetailActivity.class);
                intent.putExtra(FragmentSeatChooserMenu.EXTRA_SELECTED_SEATS, selectedSeats);
                startActivity(intent);
            } else {
                Toast.makeText(SeatChooserMenuActivity.this, "Please select a seat first!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
