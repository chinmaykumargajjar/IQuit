package com.spicycoder.iquit.controllers;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.spicycoder.iquit.R;


public class EmergencyFragment extends Fragment {
    TextView addictionName, addictionPurpose, num_of_days;
    MainActivity mainActivity;
    ImageButton ib_edit_purpose;

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
        ib_edit_purpose = view.findViewById(R.id.edit_purpose_ib);
        ib_edit_purpose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupDialog();
            }
        });
        addictionName.setText(mainActivity.addictionManager
                .getItemAtPosition(mainActivity.position)
                .getAddiction()
                .getName());

        num_of_days.setText(mainActivity.addictionManager
                .getItemAtPosition(mainActivity.position)
                .getCurrentStreak()+" Days");

        updatePurpose();
    }

    public void setEditTextMaxLength(int length, EditText et) {
        InputFilter[] filterArray = new InputFilter[1];
        filterArray[0] = new InputFilter.LengthFilter(length);
        et.setFilters(filterArray);
    }

    public void updatePurpose(){
        String purpose = mainActivity.addictionManager
                .getItemAtPosition(mainActivity
                        .position)
                .getPurpose();
        if (purpose.isEmpty()) {
            addictionPurpose.setText("Please add reason here, that may help you remind " +
                    "purpose of why you want to quit this habit.");
        } else {
            addictionPurpose.setText(purpose);
        }
    }

    private void setupDialog() {
        final EditText taskEditText = new EditText(getActivity());
        setEditTextMaxLength(120,taskEditText);
        taskEditText.setText(mainActivity.addictionManager
                .getItemAtPosition(mainActivity.position)
                .getPurpose());
        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle("Update Purpose")
                .setMessage("Please set motivating purpose here...")
                .setView(taskEditText)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String purposeUpdated = String.valueOf(taskEditText.getText());
                        mainActivity.addictionManager
                                .getItemAtPosition(mainActivity
                                        .position)
                                .setPurpose(purposeUpdated);

                        updatePurpose();

                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }
}
