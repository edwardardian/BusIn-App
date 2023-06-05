package com.example.busreservationapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PaymentRetailActivity extends AppCompatActivity {

    private TextView totalPaymentDisplay, select_indomaret, select_alfamart;

    private int selectedId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_retail);

        totalPaymentDisplay = findViewById(R.id.totalPaymentDisplay);
        select_alfamart = findViewById(R.id.select_alfamart);
        select_indomaret = findViewById(R.id.select_indomaret);

        Intent intent = getIntent();
        String price = intent.getStringExtra("price");
        totalPaymentDisplay.setText(price);

        select_indomaret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedId = R.id.select_indomaret;
                startRetailVerificationActivity(price);
            }
        });

        select_alfamart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedId = R.id.select_alfamart;
                startRetailVerificationActivity(price);
            }
        });
    }

    private void startRetailVerificationActivity(String price) {
        Intent intent = new Intent(PaymentRetailActivity.this, RetailVerificationActivity.class);
        intent.putExtra("price", price);
        intent.putExtra("selectedId", selectedId);
        startActivity(intent);
    }
}
