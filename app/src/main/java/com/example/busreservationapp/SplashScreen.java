package com.example.busreservationapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Time;

public class SplashScreen extends AppCompatActivity {
    static int TIMEOUT_MILLIS = 1500;

    private FirebaseAuth firebaseAuth;

    private FirebaseFirestore firebaseFirestore;

    private SharedPreferences sharedPreferences;

    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                editor.putBoolean("isLoggedIn", false);
                editor.apply();
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null) {
                    String userId = firebaseUser.getUid();
                    firebaseFirestore.collection("users").document(userId)
                            .get()
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document != null && document.exists()) {
                                        Intent homePageIntent = new Intent(SplashScreen.this, HomePageActivity.class);
                                        startActivity(homePageIntent);
                                        finish();
                                    } else {
                                        isLoggedIn();
                                    }
                                } else {
                                    Toast.makeText(SplashScreen.this, "Failed to receive user data!", Toast.LENGTH_SHORT).show();
                                    isLoggedIn();
                                }
                                finish();
                            });
                } else {
                   isLoggedIn();
                }
            }
        }, TIMEOUT_MILLIS);
    }

    private void isLoggedIn() {
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        if (isLoggedIn) {
            Intent homePageIntent = new Intent(SplashScreen.this, HomePageActivity.class);
            startActivity(homePageIntent);
        } else {
            Intent loginIntent = new Intent(SplashScreen.this, UserLoginActivity.class);
            startActivity(loginIntent);
        }
        finish();
    }
}
