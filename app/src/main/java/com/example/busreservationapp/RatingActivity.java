package com.example.busreservationapp;

import android.content.Intent;
import android.media.Rating;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class RatingActivity extends AppCompatActivity {
    private TextView rating_busName_display;
    private TextView rating_passenger_display;
    private TextView rating_date;
    private RatingBar ratingBar;
    private TextInputLayout textRating;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        rating_busName_display = findViewById(R.id.rating_busName_display);
        rating_passenger_display = findViewById(R.id.rating_passenger_display);
        rating_date = findViewById(R.id.rating_date);
        ratingBar = findViewById(R.id.ratingBar);
        textRating = findViewById(R.id.textRating);
        btnSubmit = findViewById(R.id.btnRate);

        Intent intent = getIntent();
        String busName = intent.getStringExtra("busName");
        String passengers = intent.getStringExtra("passengers");
        String date = intent.getStringExtra("date");

        rating_busName_display.setText(busName);
        rating_passenger_display.setText(passengers);
        rating_date.setText(date);

        btnSubmit.setOnClickListener(view -> {
            Intent intent2 = new Intent(RatingActivity.this, HistoryActivity.class);
            startActivity(intent2);
            saveRating();
        });

    }

    private void saveRating() {
        String busName = rating_busName_display.getText().toString();
        String ratingText = textRating.getEditText().getText().toString();
        float ratingStars = ratingBar.getRating();

        if (ratingText.isEmpty()) {
            textRating.setError("Please fill the feedback field!");
            return;
        }

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String userName = currentUser.getDisplayName();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("ratings")
                .whereEqualTo("busName", busName)
                .whereEqualTo("userName", userName)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null && !task.getResult().isEmpty()) {
                        Log.d("RatingActivity", "You have already rated this bus!");
                        Toast.makeText(RatingActivity.this, "You have already rated this bus!", Toast.LENGTH_SHORT).show();
                    } else {
                        BusRating rating = new BusRating(busName, ratingText, ratingStars, userName);

                        db.collection("ratings")
                                .add(rating)
                                .addOnSuccessListener(documentReference -> {
                                    Log.d("RatingActivity", "Rating saved successfully!");
                                    Toast.makeText(RatingActivity.this, "Rating saved successfully!", Toast.LENGTH_SHORT).show();
                                })
                                .addOnFailureListener(e -> {
                                    Log.e("RatingActivity", "Rating failed to save!", e);
                                    Toast.makeText(RatingActivity.this, "Rating failed to save!", Toast.LENGTH_SHORT).show();
                                });
                    }
                });
    }
}