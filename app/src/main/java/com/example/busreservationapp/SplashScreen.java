package com.example.busreservationapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.sql.Time;

public class SplashScreen extends AppCompatActivity {
    static int TIMEOUT_MILLIS = 1500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                if(firebaseUser != null && !firebaseUser.isAnonymous()) {
                    Intent homePageIntent = new Intent(SplashScreen.this, HomePageActivity.class);
                    startActivity(homePageIntent);
                } else {
                    Intent loginIntent = new Intent(SplashScreen.this, UserLoginActivity.class);
                    startActivity(loginIntent);
                }
                finish();
            }
        }, TIMEOUT_MILLIS);
    }
}
