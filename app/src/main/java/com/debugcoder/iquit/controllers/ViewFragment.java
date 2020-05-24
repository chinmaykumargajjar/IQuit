package com.debugcoder.iquit.controllers;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.debugcoder.iquit.R;
import com.debugcoder.iquit.controllers.Adapters.RelapseListAdapter;
import com.debugcoder.iquit.controllers.Interfaces.AdapterToFragmentInterface;
import com.debugcoder.iquit.models.AddictionUserModel;
import com.google.android.material.snackbar.Snackbar;

import org.joda.time.DateTime;

import java.util.Calendar;

public class ViewFragment extends Fragment implements AdapterToFragmentInterface {
    AddictionUserModel positionModel;
    TextView daysViewTv;
    RecyclerView relapseHistory_rv;
    RelapseListAdapter adapter;
    DateTime updatedRelapseDate;
    public ViewFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        positionModel = ((MainActivity) getActivity()).addictionManager
                .getItemAtPosition(((MainActivity) getActivity()).position);
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.fab.hide();

        TextView addictionNameViewtv = view.findViewById(R.id.addictionNameViewtv);
        daysViewTv = view.findViewById(R.id.days_view_tv);
        addictionNameViewtv.setText(positionModel.getAddiction().getName());
        setNumberOfDays();
        setupDatePicker(view);

        relapseHistory_rv = view.findViewById(R.id.relapseHistory_lv);
        adapter = new RelapseListAdapter(positionModel,this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        relapseHistory_rv.setHasFixedSize(true);
        relapseHistory_rv.setLayoutManager(layoutManager);
        relapseHistory_rv.setAdapter(adapter);
    }

    public void setupDatePicker(final View setView) {

        setView.findViewById(R.id.logRelapse_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                DatePickerDialog picker = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                monthOfYear++;
                                updatedRelapseDate = new DateTime(year + "-" + monthOfYear + "-"
                                        + dayOfMonth);

                                if (positionModel.getStreakFromDate(updatedRelapseDate) > -1) {
                                    positionModel.addRelapse(updatedRelapseDate);
                                    adapter.notifyDataSetChanged();
                                    setNumberOfDays();
                                } else {
                                    Snackbar.make(setView, R.string.date_selection, Snackbar.LENGTH_LONG)
                                            .show();
                                }
                            }
                        }, year, month, day);
                picker.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface
                        .OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == DialogInterface.BUTTON_NEGATIVE) {
                            // Do Stuff
                            Log.i("dialog click", "dialog negative button clicked");
                            dialog.dismiss();
                        }
                    }
                });
                picker.show();
            }
        });
    }

    public boolean setNumberOfDays() {
        long numberOfDays = positionModel.getCurrentStreak();
        if (numberOfDays != -2) {
            daysViewTv.setText(numberOfDays + " Days");
            return true;
        }
        return false;
    }

    @Override
    public void actNow() {
        setNumberOfDays();
    }
}
