package com.example.busreservationapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PaymentMethodActivity extends AppCompatActivity {
    private TextView tvSelectEwallet, tvSelectTransferBankVirtualAccount, tvSelectRetailPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);

        tvSelectEwallet = findViewById(R.id.tvSelectEWallet);
        tvSelectTransferBankVirtualAccount = findViewById(R.id.tvSelectTransferBankVirtualAccount);
        tvSelectRetailPayment = findViewById(R.id.tvSelectRetailPayment);

    }
}
