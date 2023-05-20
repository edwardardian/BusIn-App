package com.example.busreservationapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserRegisterActivity extends AppCompatActivity {
    private ProgressBar progressBarRegister;

    private EditText etPhoneNumber;

    private Button btnRegister;

    private FirebaseFirestore db;
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


    private void register(){
        String phoneNumber = etPhoneNumber.getText().toString().trim();
        if(phoneNumber.isEmpty()){
            etPhoneNumber.setError("Phone Number is required!");
            etPhoneNumber.requestFocus();
            return;
        }

        User user = new User(null, null, phoneNumber);

        db.collection("users")
                .document()
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        progressBarRegister.setVisibility(View.GONE);
                        Toast.makeText(UserRegisterActivity.this, "Phone number added successfully!", Toast.LENGTH_SHORT).show();
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
