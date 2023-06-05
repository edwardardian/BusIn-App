package com.example.busreservationapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class VirtualAccountVerificationActivity extends AppCompatActivity {

    private ImageView virtual_account_logo;

    private TextView virtual_account_name, totalPaymentDisplay, tvPaymentNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_virtual_account_verification);

        virtual_account_logo = findViewById(R.id.virtual_account_logo);
        virtual_account_name = findViewById(R.id.virtual_account_name);
        totalPaymentDisplay = findViewById(R.id.totalPaymentDisplay);
        tvPaymentNumber = findViewById(R.id.tvPaymentNumber);

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
                    break;
            }
        }
    }
}
