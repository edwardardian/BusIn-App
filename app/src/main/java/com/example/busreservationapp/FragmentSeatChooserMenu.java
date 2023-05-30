package com.example.busreservationapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentSeatChooserMenu extends Fragment {
    private String TAG = FragmentSeatChooserMenu.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = layoutInflater.inflate(R.layout.fragment_seat_chooser_menu, container, false);
        initView (view);
        return view;
    }

    public void initView(View view){

    }
}