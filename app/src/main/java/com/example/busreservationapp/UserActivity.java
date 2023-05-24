package com.example.busreservationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UserActivity extends AppCompatActivity {
    private String TAG = UserActivity.class.getSimpleName();

    private FrameLayout container;
    private BottomNavigationView nav;

    private final FragmentUser fragmentUser = new FragmentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        initView();

        loadFragment(fragmentUser);

        nav.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()){
                            /*case R.id.search_navigation :
                                *//*startActivity(new Intent(UserActivity.this, UserActivity.class));*//*
                                finish();
                                return true;
                            case R.id.ticket_navigation :
                                *//*startActivity(new Intent(UserActivity.this, UserProfileActivity.class));*//*
                                finish();
                                return true;*/
                            case R.id.profile_navigation :
                                startActivity(new Intent(UserActivity.this, FragmentUser.class));
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