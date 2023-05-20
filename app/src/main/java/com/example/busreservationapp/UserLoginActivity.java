package com.example.busreservationapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class UserLoginActivity extends AppCompatActivity {
    private GoogleSignInClient mGoogleSignInClient;

    private FirebaseAuth mAuth;

    private Button btnGoogleLogin;

    private ProgressBar progressBar;

    private FirebaseFirestore db;

    private int RC_SIGN_IN = 5000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        btnGoogleLogin = findViewById(R.id.btnGoogleLogin);

        progressBar = findViewById(R.id.progressBarLogin);

        db = FirebaseFirestore.getInstance();

        btnGoogleLogin.setOnClickListener(v -> {
            googleSignIn();
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_client_id))
                .requestEmail()
                .build();

        mAuth = FirebaseAuth.getInstance();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void googleSignIn() {
        Intent signInClient = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInClient, RC_SIGN_IN);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                account.getId();
                progressBar.setVisibility(View.GONE);
                Toast.makeText(UserLoginActivity.this, "Google sign in success!", Toast.LENGTH_SHORT).show();
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(UserLoginActivity.this, "Google sign in failed!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential authCredential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(authCredential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(UserLoginActivity.this, "Login success!", Toast.LENGTH_SHORT).show();
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            if (firebaseUser != null) {
                                updateUI(firebaseUser);
                            } else {
                                startActivity(new Intent(UserLoginActivity.this, UserRegisterActivity.class));
                                finish();
                            }
                        } else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(UserLoginActivity.this, "Google sign in failed!", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser firebaseUser) {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if (firebaseUser != null) {
            String userName = account.getDisplayName();
            String userEmail = account.getEmail();

            User user = new User(userName, userEmail, null);

            db.collection("users").document(firebaseUser.getUid())
                    .set(user)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            startActivity(new Intent(UserLoginActivity.this, HomePageActivity.class));
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            startActivity(new Intent(UserLoginActivity.this, UserRegisterActivity.class));
                            finish();
                        }
                    });

        }
    }
}

