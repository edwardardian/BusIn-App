package com.example.busreservationapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;

public class FragmentUser extends Fragment {
    private String TAG = FragmentUser.class.getSimpleName();

    private Button logout;

    private TextView tvUserName, tvUserEmail, tvUserPhoneNumber;

    private FirebaseUser firebaseUser;

    private FirebaseAuth firebaseAuth;

    private FirebaseFirestore firebaseFirestore;

    private String userId;

    private ListenerRegistration userListener;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = layoutInflater.inflate(R.layout.fragment_user, container, false);
        initView (view);
        return view;
    }

    private void initView(View view) {
        tvUserName = view.findViewById(R.id.tvUserName);
        tvUserEmail = view.findViewById(R.id.tvUserEmail);
        tvUserPhoneNumber = view.findViewById(R.id.tvUserPhoneNumber);

        logout = view.findViewById(R.id.btnLogout);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        userId = firebaseUser.getUid();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();

        if (firebaseUser == null) {
            startActivity(new Intent(getActivity(), UserLoginActivity.class));
            getActivity().finish();
        } else {
            userListener = firebaseFirestore.collection("users")
                    .document(firebaseUser.getUid())
                    .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                            if (error != null) {
                                Toast.makeText(getActivity(), "Failed to receive user data!", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            if (documentSnapshot != null && documentSnapshot.exists()) {
                                String userName = documentSnapshot.getString("name");
                                String userEmail = documentSnapshot.getString("email");
                                String userPhoneNumber = documentSnapshot.getString("phoneNumber");

                                tvUserName.setText(userName);
                                tvUserEmail.setText(userEmail);
                                tvUserPhoneNumber.setText(userPhoneNumber);
                            }
                        }
                    });
        }
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                startActivity(new Intent(getActivity(), UserLoginActivity.class));
                getActivity().finish();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (userListener != null) {
            userListener.remove();
        }
    }
}
