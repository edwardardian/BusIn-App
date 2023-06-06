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

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class VirtualAccountVerificationActivity extends AppCompatActivity {

    private ImageView virtual_account_logo;

    private TextView virtual_account_name, totalPaymentDisplay, tvPaymentNumber;

    private Button btnVerifyPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_virtual_account_verification);

        virtual_account_logo = findViewById(R.id.virtual_account_logo);
        virtual_account_name = findViewById(R.id.virtual_account_name);
        totalPaymentDisplay = findViewById(R.id.totalPaymentDisplay);
        tvPaymentNumber = findViewById(R.id.tvPaymentNumber);
        btnVerifyPayment = findViewById(R.id.btnVerifyPayment);

        Intent intent = getIntent();
        int selectedId = intent.getIntExtra("selectedId", 0);
        String price = intent.getStringExtra("price");

        if (selectedId != 0) {
            switch (selectedId) {
                case R.id.select_mandiri:
                    virtual_account_logo.setImageResource(R.drawable.mandiri_logo);
                    virtual_account_name.setText("Bank Mandiri");
                    totalPaymentDisplay.setText(price);

                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
                    String formattedDate = dateFormat.format(calendar.getTime());
                    String orderNumber = "M" + formattedDate;
                    tvPaymentNumber.setText(orderNumber);

                    btnVerifyPayment.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            verifyNow();
                        }
                    });
                    break;
                case R.id.select_bca:
                    virtual_account_logo.setImageResource(R.drawable.bca_logo);
                    virtual_account_name.setText("Bank BCA");
                    totalPaymentDisplay.setText(price);

                    Calendar calendar2 = Calendar.getInstance();
                    SimpleDateFormat dateFormat2 = new SimpleDateFormat("ddMMyyyy");
                    String formattedDate2 = dateFormat2.format(calendar2.getTime());
                    String orderNumber2 = "B" + formattedDate2;
                    tvPaymentNumber.setText(orderNumber2);

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
        AlertDialog.Builder alert = new AlertDialog.Builder(VirtualAccountVerificationActivity.this);
        alert.setTitle("Confirmation");
        alert.setMessage("Are you sure you want to pay this?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(VirtualAccountVerificationActivity.this, TicketDetailActivity.class);
                Toast.makeText(VirtualAccountVerificationActivity.this, "Payment Verified!", Toast.LENGTH_SHORT).show();
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
