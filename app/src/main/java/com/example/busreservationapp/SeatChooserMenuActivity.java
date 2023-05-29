package com.example.busreservationapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

public class SeatChooserMenuActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseFirestore db;

    private CheckedTextView[] seats = new CheckedTextView[40];
    private int[] seatAvailability = new int[40];
    private String[] seatCodes = new String[40];

    private Button btnBookNow;

    public static final String EXTRA_SELECTED_SEATS = "extra_selected_seats";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_chooser_menu);

        db = FirebaseFirestore.getInstance();
        btnBookNow = findViewById(R.id.btnBookNow);

        btnBookNow.setOnClickListener(this);

        initializeSeats();

        for (CheckedTextView seat : seats) {
            seat.setOnClickListener(this);
        }

        updateSeatChooserView();
    }

    private void initializeSeats() {
        seats[0] = findViewById(R.id.ivSeatA1);
        seats[1] = findViewById(R.id.ivSeatB1);
        seats[2] = findViewById(R.id.ivSeatA2);
        seats[3] = findViewById(R.id.ivSeatB2);
        seats[4] = findViewById(R.id.ivSeatA3);
        seats[5] = findViewById(R.id.ivSeatB3);
        seats[6] = findViewById(R.id.ivSeatA4);
        seats[7] = findViewById(R.id.ivSeatB4);
        seats[8] = findViewById(R.id.ivSeatA5);
        seats[9] = findViewById(R.id.ivSeatB5);
        seats[10] = findViewById(R.id.ivSeatA6);
        seats[11] = findViewById(R.id.ivSeatB6);
        seats[12] = findViewById(R.id.ivSeatA7);
        seats[13] = findViewById(R.id.ivSeatB7);
        seats[14] = findViewById(R.id.ivSeatA8);
        seats[15] = findViewById(R.id.ivSeatB8);
        seats[16] = findViewById(R.id.ivSeatA9);
        seats[17] = findViewById(R.id.ivSeatB9);
        seats[18] = findViewById(R.id.ivSeatA10);
        seats[19] = findViewById(R.id.ivSeatB10);
        seats[20] = findViewById(R.id.ivSeatC1);
        seats[21] = findViewById(R.id.ivSeatD1);
        seats[22] = findViewById(R.id.ivSeatC2);
        seats[23] = findViewById(R.id.ivSeatD2);
        seats[24] = findViewById(R.id.ivSeatC3);
        seats[25] = findViewById(R.id.ivSeatD3);
        seats[26] = findViewById(R.id.ivSeatC4);
        seats[27] = findViewById(R.id.ivSeatD4);
        seats[28] = findViewById(R.id.ivSeatC5);
        seats[29] = findViewById(R.id.ivSeatD5);
        seats[30] = findViewById(R.id.ivSeatC6);
        seats[31] = findViewById(R.id.ivSeatD6);
        seats[32] = findViewById(R.id.ivSeatC7);
        seats[33] = findViewById(R.id.ivSeatD7);
        seats[34] = findViewById(R.id.ivSeatC8);
        seats[35] = findViewById(R.id.ivSeatD8);
        seats[36] = findViewById(R.id.ivSeatC9);
        seats[37] = findViewById(R.id.ivSeatD9);
        seats[38] = findViewById(R.id.ivSeatC10);
        seats[39] = findViewById(R.id.ivSeatD10);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnBookNow) {
            bookNow();
        } else {
            int seatIndex = findSeatIndex(view.getId());

            if (seatAvailability[seatIndex] == 0) {
                seats[seatIndex].setBackgroundResource(R.drawable.seat_booked);
                seatAvailability[seatIndex] = 1;
                seatCodes[seatIndex] = generateSeatCode(seatIndex);
            } else {
                seats[seatIndex].setBackgroundResource(R.drawable.seat_available);
                seatAvailability[seatIndex] = 0;
                seatCodes[seatIndex] = "";
            }

            updateSeatChooserView();
        }
    }

    private void bookNow() {
        boolean isSeatSelected = false;
        for (int a = 0; a < seatAvailability.length; a++) {
            if (seatAvailability[a] == 1) {
                isSeatSelected = true;
                break;
            }
        }
        if (isSeatSelected) {
            String[] selectedSeats = new String[seatCodes.length];
            for (int i = 0; i < seatCodes.length; i++) {
                selectedSeats[i] = seatCodes[i] != null ? seatCodes[i] : "";
            }

            Intent intent = new Intent(SeatChooserMenuActivity.this, BusDetailActivity.class);
            intent.putExtra(EXTRA_SELECTED_SEATS, selectedSeats);

            startActivity(intent);
        } else {
            Toast.makeText(this, "Please select a seat first!", Toast.LENGTH_SHORT).show();
        }
    }



    private void updateSeatChooserView() {
        for (int i = 0; i < seats.length; i++) {
            if (seatAvailability[i] == 0) {
                seats[i].setBackgroundResource(R.drawable.seat_chooser);
            } else {
                seats[i].setBackgroundResource(R.drawable.seat_booked);
            }
        }
    }

    private int findSeatIndex(int viewId) {
        for (int i = 0; i < seats.length; i++) {
            if (seats[i].getId() == viewId) {
                return i;
            }
        }
        return -1;
    }

    private String generateSeatCode(int seatIndex) {
        int row = (seatIndex / 2) + 1;
        int column = (seatIndex % 2) + 1;

        return "Row " + row + ", Seat " + column;
    }
}
