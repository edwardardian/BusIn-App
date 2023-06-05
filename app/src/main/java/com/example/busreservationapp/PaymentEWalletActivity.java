package com.example.busreservationapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PaymentEWalletActivity extends AppCompatActivity {

    private TextView totalPaymentDisplay, select_ovo, select_gopay;
    private int selectedId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_e_wallet);

        totalPaymentDisplay = findViewById(R.id.totalPaymentDisplay);
        select_ovo = findViewById(R.id.select_ovo);
        select_gopay = findViewById(R.id.select_gopay);

        Intent intent = getIntent();
        String price = intent.getStringExtra("price");
        totalPaymentDisplay.setText(price);

        select_gopay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedId = R.id.select_gopay;
                startEWalletVerificationActivity(price);
            }
        });

        select_ovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedId = R.id.select_ovo;
                startEWalletVerificationActivity(price);
            }
        });
    }

    private void startEWalletVerificationActivity(String price) {
        Intent intent = new Intent(PaymentEWalletActivity.this, EWalletVerificationActivity.class);
        intent.putExtra("price", price);
        intent.putExtra("selectedId", selectedId);
        startActivity(intent);
    }
}
