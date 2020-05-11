package com.debugcoder.iquit.controllers;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.debugcoder.iquit.R;


public class EmergencyFragment extends Fragment {
    TextView addictionName, addictionPurpose, num_of_days;
    MainActivity mainActivity;

    public EmergencyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_emergency, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainActivity = (MainActivity) getActivity();
        mainActivity.fab.hide();
        addictionName = view.findViewById(R.id.add_name_emer_tv);
        addictionPurpose = view.findViewById(R.id.purpose_emergency_tv);
        num_of_days = view.findViewById(R.id.num_days_emr_tv);

        addictionName.setText(mainActivity.addictionManager
                .getItemAtPosition(mainActivity.position)
                .getAddiction()
                .getName());

        addictionPurpose.setText(mainActivity.addictionManager
                .getItemAtPosition(mainActivity
                        .position)
                .getPurpose());

        num_of_days.setText(mainActivity.addictionManager
                .getItemAtPosition(mainActivity.position)
                .getNumberOfDays(null)+" Days");

    }
}
