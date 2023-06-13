package com.example.busreservationapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class EWalletVerificationActivity extends AppCompatActivity {

    private ImageView e_wallet_logo;

    private TextView e_wallet_name, totalPaymentDisplay;

    private Button btnVerifyPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e_wallet_verification);

        e_wallet_logo = findViewById(R.id.e_wallet_logo);
        e_wallet_name = findViewById(R.id.e_wallet_name);
        totalPaymentDisplay = findViewById(R.id.totalPaymentDisplay);
        btnVerifyPayment = findViewById(R.id.btnVerifyPayment);

        Intent intent = getIntent();
        int selectedId = intent.getIntExtra("selectedId", 0);
        String price = intent.getStringExtra("price");

        if (selectedId != 0) {
            switch (selectedId) {
                case R.id.select_ovo:
                    e_wallet_logo.setImageResource(R.drawable.ovo_logo);
                    e_wallet_name.setText("OVO");
                    totalPaymentDisplay.setText(price);
                    btnVerifyPayment.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            verifyNow();
                        }
                    });
                    break;
                case R.id.select_gopay:
                    e_wallet_logo.setImageResource(R.drawable.gopay_logo);
                    e_wallet_name.setText("GOPAY");
                    totalPaymentDisplay.setText(price);
                    btnVerifyPayment.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            verifyNow();
                        }
                    });
                    break;
            }
        }
    }

    public void verifyNow(){
        AlertDialog.Builder alert = new AlertDialog.Builder(EWalletVerificationActivity.this);
        alert.setTitle(R.string.app_name);
        alert.setMessage("Are you sure you want to pay this?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(EWalletVerificationActivity.this, TicketDetailActivity.class);
                Toast.makeText(EWalletVerificationActivity.this, "Payment Verified!", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.show();
    }
}
