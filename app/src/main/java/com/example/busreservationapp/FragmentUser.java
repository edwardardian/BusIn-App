package com.example.busreservationapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.squareup.picasso.Picasso;

public class FragmentUser extends Fragment {
    private String TAG = FragmentUser.class.getSimpleName();

    private Button logout;

    private TextView tvUserName, tvUserEmail, tvUserPhoneNumber;

    private ImageView logo;

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
        logo = view.findViewById(R.id.logo);

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
            userId = firebaseUser.getUid();

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
                                String photo = documentSnapshot.getString("photo");

                                tvUserName.setText(userName);
                                tvUserEmail.setText(userEmail);
                                tvUserPhoneNumber.setText(userPhoneNumber);

                                if (photo != null && !photo.isEmpty()) {
                                    Picasso.get().load(photo).into(logo);
                                }
                            }
                        }
                    });
        }
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLogout(v);
            }
        });
    }

    public void onLogout(View view){
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Logout");
        alert.setMessage("Are you sure you want to logout?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (firebaseAuth != null) {
                    firebaseAuth.signOut();
                }
                startActivity(new Intent(getActivity(), UserLoginActivity.class));
                getActivity().finish();
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (userListener != null) {
            userListener.remove();
        }
    }
}
