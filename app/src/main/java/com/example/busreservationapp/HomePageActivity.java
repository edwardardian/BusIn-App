package com.example.busreservationapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.telecom.Call;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class HomePageActivity extends AppCompatActivity {
    private String TAG = HomePageActivity.class.getSimpleName();

    private FrameLayout container;
    private BottomNavigationView nav;

    private EditText etPassengers, date;

    private final FragmentHomePage fragmentHomePage = new FragmentHomePage();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        initView();

        loadFragment(fragmentHomePage);

        nav.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.ticket_navigation:
                                startActivity(new Intent(HomePageActivity.this, HomePageActivity.class));
                                finish();
                                return true;
                            /*case R.id.history_navigation :
                                startActivity(new Intent(UserActivity.this, UserProfileActivity.class));
                                finish();
                                return true;*/
                            case R.id.profile_navigation :
                                startActivity(new Intent(HomePageActivity.this, UserActivity.class));
                                finish();
                                return true;
                        }
                        return false;
                    }
                }
        );
    }

    private void initView(){
        container = findViewById(R.id.container);
        nav = findViewById(R.id.nav);
    }

    private void loadFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }

}
