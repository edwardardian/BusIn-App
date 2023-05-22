package com.example.busreservationapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
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
                                    } else {
                                        Intent loginIntent = new Intent(SplashScreen.this, UserLoginActivity.class);
                                        startActivity(loginIntent);
                                    }
                                } else {
                                    Toast.makeText(SplashScreen.this, "Failed to receive user data!", Toast.LENGTH_SHORT).show();
                                }
                                finish();
                            });
                } else {
                    Intent loginIntent = new Intent(SplashScreen.this, UserLoginActivity.class);
                    startActivity(loginIntent);
                    finish();
                }
            }
        }, TIMEOUT_MILLIS);
    }
}
