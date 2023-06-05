package com.example.busreservationapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RetailVerificationActivity extends AppCompatActivity {

    private ImageView retail_logo;

    private TextView retail_name, totalPaymentDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retail_verification);

        retail_logo = findViewById(R.id.retail_logo);
        retail_name = findViewById(R.id.retail_name);
        totalPaymentDisplay = findViewById(R.id.totalPaymentDisplay);

        Intent intent = getIntent();
        int selectedId = intent.getIntExtra("selectedId", 0);
        String price = intent.getStringExtra("price");

        if (selectedId != 0) {
            switch (selectedId) {
                case R.id.select_indomaret:
                    retail_logo.setImageResource(R.drawable.indomaret_logo);
                    retail_name.setText("Indomaret");
                    totalPaymentDisplay.setText(price);
                    break;
                case R.id.select_alfamart:
                    retail_logo.setImageResource(R.drawable.alfamart_logo);
                    retail_name.setText("Alfamart");
                    totalPaymentDisplay.setText(price);
                    break;
            }
        }
    }
}
