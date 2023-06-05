package com.example.busreservationapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class EWalletVerificationActivity extends AppCompatActivity {

    private ImageView e_wallet_logo;
    private TextView e_wallet_name, totalPaymentDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e_wallet_verification);

        e_wallet_logo = findViewById(R.id.e_wallet_logo);
        e_wallet_name = findViewById(R.id.e_wallet_name);
        totalPaymentDisplay = findViewById(R.id.totalPaymentDisplay);

        Intent intent = getIntent();
        int selectedId = intent.getIntExtra("selectedId", 0);
        String price = intent.getStringExtra("price");

        if (selectedId != 0) {
            switch (selectedId) {
                case R.id.select_ovo:
                    e_wallet_logo.setImageResource(R.drawable.ovo_logo);
                    e_wallet_name.setText("OVO");
                    totalPaymentDisplay.setText(price);
                    break;
                case R.id.select_gopay:
                    e_wallet_logo.setImageResource(R.drawable.gopay_logo);
                    e_wallet_name.setText("GOPAY");
                    totalPaymentDisplay.setText(price);
                    break;
            }
        }
    }
}
