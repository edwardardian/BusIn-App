package com.example.busreservationapp;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserRegisterActivity extends AppCompatActivity {
    private ProgressBar progressBarRegister;

    private EditText etPhoneNumber;

    private Button btnRegister;

    private FirebaseFirestore db;

    private ImageView logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        progressBarRegister = findViewById(R.id.progressBarRegister);

        etPhoneNumber = findViewById(R.id.etPhoneNumber);

        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });

        db = FirebaseFirestore.getInstance();
    }


    private void register() {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        String userName = account.getDisplayName();
        String userEmail = account.getEmail();
        String phoneNumber = etPhoneNumber.getText().toString().trim();
        String photo = account.getPhotoUrl().toString();

        if (phoneNumber.isEmpty() || phoneNumber.length() < 10) {
            etPhoneNumber.setError("Please fill the Phone Number blank field with at least 10 digits!");
            etPhoneNumber.requestFocus();
            return;
        }

        User user = new User(userName, userEmail, phoneNumber, photo);

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            String userId = firebaseUser.getUid();

            db.collection("users")
                    .document(userId)
                    .set(user)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            progressBarRegister.setVisibility(View.GONE);
                            Toast.makeText(UserRegisterActivity.this, "Phone number added successfully!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(UserRegisterActivity.this, HomePageActivity.class));
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressBarRegister.setVisibility(View.GONE);
                            Toast.makeText(UserRegisterActivity.this, "Phone number failed to add!", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}

