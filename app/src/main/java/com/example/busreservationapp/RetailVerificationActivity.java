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

public class RetailVerificationActivity extends AppCompatActivity {

    private ImageView retail_logo;

    private TextView retail_name, totalPaymentDisplay;

    private Button verify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retail_verification);

        retail_logo = findViewById(R.id.retail_logo);
        retail_name = findViewById(R.id.retail_name);
        totalPaymentDisplay = findViewById(R.id.totalPaymentDisplay);
        verify = findViewById(R.id.btnVerifyPayment);

        Intent intent = getIntent();
        int selectedId = intent.getIntExtra("selectedId", 0);
        String price = intent.getStringExtra("price");

        if (selectedId != 0) {
            switch (selectedId) {
                case R.id.select_indomaret:
                    retail_logo.setImageResource(R.drawable.indomaret_logo);
                    retail_name.setText("Indomaret");
                    totalPaymentDisplay.setText(price);
                    verify.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            verifyNow();
                        }
                    });
                    break;
                case R.id.select_alfamart:
                    retail_logo.setImageResource(R.drawable.alfamart_logo);
                    retail_name.setText("Alfamart");
                    totalPaymentDisplay.setText(price);
                    verify.setOnClickListener(new View.OnClickListener() {
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
        AlertDialog.Builder alert = new AlertDialog.Builder(RetailVerificationActivity.this);
        alert.setTitle("Confirmation");
        alert.setMessage("Are you sure you want to pay this?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(RetailVerificationActivity.this, "Payment not verified. Please select another payment method!", Toast.LENGTH_SHORT).show();
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
