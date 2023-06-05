package com.example.busreservationapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PaymentVirtualAccountActivity extends AppCompatActivity {

    private TextView totalPaymentDisplay, select_mandiri, select_bca;

    private int selectedId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_virtual_account);

        totalPaymentDisplay = findViewById(R.id.totalPaymentDisplay);
        select_mandiri = findViewById(R.id.select_mandiri);
        select_bca = findViewById(R.id.select_bca);

        Intent intent = getIntent();
        String price = intent.getStringExtra("price");
        totalPaymentDisplay.setText(price);

        select_mandiri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedId = R.id.select_mandiri;
                startVirtualAccountVerificationActivity(price);
            }
        });

        select_bca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedId = R.id.select_bca;
                startVirtualAccountVerificationActivity(price);
            }
        });
    }

    private void startVirtualAccountVerificationActivity(String price) {
        Intent intent = new Intent(PaymentVirtualAccountActivity.this, VirtualAccountVerificationActivity.class);
        intent.putExtra("price", price);
        intent.putExtra("selectedId", selectedId);
        startActivity(intent);
    }
}
