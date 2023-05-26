package com.example.busreservationapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class FragmentHomePage extends Fragment {

    final Calendar myCalendar = Calendar.getInstance();
    EditText datePicker;

    EditText etPassengers;

    Button btnSearchBus;

    private String selectedDeparture;
    private String selectedArrival;

    private String passengers;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment_home_page, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        Spinner spinnerDeparture = view.findViewById(R.id.tvDeparture);
        Spinner spinnerArrival = view.findViewById(R.id.tvArrival);
        datePicker = view.findViewById(R.id.date);
        btnSearchBus = view.findViewById(R.id.btnSearchBus);
        etPassengers = view.findViewById(R.id.etPassengers);

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);
                updateLabel();
            }
        };

        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getActivity(), date,
                        myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH))
                        .show();
            }
        });

        btnSearchBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectedDeparture = spinnerDeparture.getSelectedItem().toString();
                String selectedArrival = spinnerArrival.getSelectedItem().toString();
                String passengers = etPassengers.getText().toString();


                Intent intent = new Intent(getActivity(), BusScheduleActivity.class);
                intent.putExtra("departure", selectedDeparture);
                intent.putExtra("arrival", selectedArrival);
                intent.putExtra("date", datePicker.getText().toString());
                intent.putExtra("passengers", passengers);
                startActivity(intent);
            }
        });

        spinnerDeparture.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDeparture = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinnerArrival.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedArrival = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.UK);
        datePicker.setText(dateFormat.format(myCalendar.getTime()));
    }

}
