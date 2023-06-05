package com.example.busreservationapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PaymentMethodActivity extends AppCompatActivity {
    private TextView tvSelectEwallet, tvSelectTransferBankVirtualAccount, tvSelectRetailPayment, tvTotalPaymentDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);

        tvSelectEwallet = findViewById(R.id.tvSelectEWallet);
        tvSelectTransferBankVirtualAccount = findViewById(R.id.tvSelectTransferBankVirtualAccount);
        tvSelectRetailPayment = findViewById(R.id.tvSelectRetailPayment);
        tvTotalPaymentDetail = findViewById(R.id.tvTotalPaymentDetail);

        Intent intent = getIntent();
        String price = intent.getStringExtra("price");
        tvTotalPaymentDetail.setText(price);

        tvSelectEwallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PaymentMethodActivity.this, PaymentEWalletActivity.class);
                intent.putExtra("price", price);
                startActivity(intent);
            }
        });

        tvSelectTransferBankVirtualAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PaymentMethodActivity.this, PaymentVirtualAccountActivity.class);
                intent.putExtra("price", price);
                startActivity(intent);
            }
        });

        tvSelectRetailPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PaymentMethodActivity.this, PaymentRetailActivity.class);
                intent.putExtra("price", price);
                startActivity(intent);
            }
        });




    }
}
