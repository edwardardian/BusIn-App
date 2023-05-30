package com.example.busreservationapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.firestore.FirebaseFirestore;

public class SeatChooserMenuActivity extends AppCompatActivity {
    private String TAG = SeatChooserMenuActivity.class.getSimpleName();

    private FrameLayout container;
    private LinearLayout linearLayout;

    private final FragmentSeatChooserMenu fragmentSeatChooserMenu = new FragmentSeatChooserMenu();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_chooser_menu);

        initView();

        loadFragment(fragmentSeatChooserMenu);
    }

    private void initView(){
        container = findViewById(R.id.container);
    }

    private void loadFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }

}
