package com.example.busreservationapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.CheckedTextView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentSeatChooserMenu extends Fragment implements View.OnClickListener {
    private String TAG = FragmentSeatChooserMenu.class.getSimpleName();

    private FirebaseFirestore db;

    private CheckedTextView[] seats = new CheckedTextView[40];
    private int[] seatAvailability = new int[40];
    private String[] seatCodes = new String[40];

    private Button btnBookNow;
    private List<String> selectedSeatsList = new ArrayList<>();


    public static final String EXTRA_SELECTED_SEATS = "extra_selected_seats";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = layoutInflater.inflate(R.layout.fragment_seat_chooser_menu, container, false);
        initView (view);

        return view;
    }

    public void initView(View view){
        db = FirebaseFirestore.getInstance();

        initializeSeats(view);

        for (CheckedTextView seat : seats) {
            seat.setOnClickListener(this);
        }
        updateSeatChooserView();
    }

    private void initializeSeats(View view) {
        seats[0] = view.findViewById(R.id.ivSeatA1);
        seats[1] = view.findViewById(R.id.ivSeatB1);
        seats[2] = view.findViewById(R.id.ivSeatA2);
        seats[3] = view.findViewById(R.id.ivSeatB2);
        seats[4] = view.findViewById(R.id.ivSeatA3);
        seats[5] = view.findViewById(R.id.ivSeatB3);
        seats[6] = view.findViewById(R.id.ivSeatA4);
        seats[7] = view.findViewById(R.id.ivSeatB4);
        seats[8] = view.findViewById(R.id.ivSeatA5);
        seats[9] = view.findViewById(R.id.ivSeatB5);
        seats[10] = view.findViewById(R.id.ivSeatA6);
        seats[11] = view.findViewById(R.id.ivSeatB6);
        seats[12] = view.findViewById(R.id.ivSeatA7);
        seats[13] = view.findViewById(R.id.ivSeatB7);
        seats[14] = view.findViewById(R.id.ivSeatA8);
        seats[15] = view.findViewById(R.id.ivSeatB8);
        seats[16] = view.findViewById(R.id.ivSeatA9);
        seats[17] = view.findViewById(R.id.ivSeatB9);
        seats[18] = view.findViewById(R.id.ivSeatA10);
        seats[19] = view.findViewById(R.id.ivSeatB10);
        seats[20] = view.findViewById(R.id.ivSeatC1);
        seats[21] = view.findViewById(R.id.ivSeatD1);
        seats[22] = view.findViewById(R.id.ivSeatC2);
        seats[23] = view.findViewById(R.id.ivSeatD2);
        seats[24] = view.findViewById(R.id.ivSeatC3);
        seats[25] = view.findViewById(R.id.ivSeatD3);
        seats[26] = view.findViewById(R.id.ivSeatC4);
        seats[27] = view.findViewById(R.id.ivSeatD4);
        seats[28] = view.findViewById(R.id.ivSeatC5);
        seats[29] = view.findViewById(R.id.ivSeatD5);
        seats[30] = view.findViewById(R.id.ivSeatC6);
        seats[31] = view.findViewById(R.id.ivSeatD6);
        seats[32] = view.findViewById(R.id.ivSeatC7);
        seats[33] = view.findViewById(R.id.ivSeatD7);
        seats[34] = view.findViewById(R.id.ivSeatC8);
        seats[35] = view.findViewById(R.id.ivSeatD8);
        seats[36] = view.findViewById(R.id.ivSeatC9);
        seats[37] = view.findViewById(R.id.ivSeatD9);
        seats[38] = view.findViewById(R.id.ivSeatC10);
        seats[39] = view.findViewById(R.id.ivSeatD10);
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
                selectedSeatsList.add(seatCodes[seatIndex]);
            } else {
                seats[seatIndex].setBackgroundResource(R.drawable.seat_available);
                seatAvailability[seatIndex] = 0;
                seatCodes[seatIndex] = "";
                selectedSeatsList.remove(seatCodes[seatIndex]);
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

            Intent intent = new Intent(getActivity(), BusDetailActivity.class);
            intent.putExtra(EXTRA_SELECTED_SEATS, selectedSeats);
            startActivity(intent);
        } else {
            Toast.makeText(getActivity(), "Please select a seat first!", Toast.LENGTH_SHORT).show();
        }
    }
    public String[] getSelectedSeats() {
        List<String> selectedSeatsList = new ArrayList<>();

        for (int i = 0; i < seatAvailability.length; i++) {
            if (seatAvailability[i] == 1) {
                selectedSeatsList.add(seatCodes[i]);
            }
        }

        return selectedSeatsList.toArray(new String[selectedSeatsList.size()]);
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