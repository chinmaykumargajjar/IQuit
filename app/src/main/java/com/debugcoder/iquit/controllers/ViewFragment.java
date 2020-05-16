package com.debugcoder.iquit.controllers;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.debugcoder.iquit.R;
import com.debugcoder.iquit.controllers.Interfaces.AddDataPassInterface;
import com.debugcoder.iquit.models.AddictionType;
import com.debugcoder.iquit.models.AddictionUserModel;
import com.debugcoder.iquit.models.Utilities;
import com.google.android.material.snackbar.Snackbar;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ViewFragment extends Fragment {
    AddictionUserModel positionModel;
    TextView daysViewTv;
    ListView relapseHistory_lv;
    ArrayAdapter adapter;
    ArrayList<String> relapseHistory = new ArrayList<String>();;

    public ViewFragment() {
    }

    public void updateRelapseDataAdapter(){
        relapseHistory.clear();
        for(DateTime dateTime:positionModel.getRelapseHistory()){
            relapseHistory.add(Utilities.getStringFromDate(dateTime));
        }
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
        positionModel = ((MainActivity)getActivity()).addictionManager
                .getItemAtPosition(((MainActivity)getActivity()).position);
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.fab.hide();

        TextView addictionNameViewtv = view.findViewById(R.id.addictionNameViewtv);
        daysViewTv = view.findViewById(R.id.days_view_tv);
        relapseHistory_lv = view.findViewById(R.id.relapseHistory_lv);
        updateRelapseDataAdapter();
        adapter=new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1,
                relapseHistory);
        relapseHistory_lv.setAdapter(adapter);

        addictionNameViewtv.setText(positionModel.getAddiction().getName());

        setupDatePicker(view);
    }

    public void setupDatePicker(final View setView){

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
                                DateTime updatedRelapseDate = new DateTime(year+"-"+monthOfYear+"-"
                                        +dayOfMonth);
                                if(canSetNumberOfDays(updatedRelapseDate)) {
                                    //Add Relapse
                                    positionModel.addRelapse(updatedRelapseDate);
                                    //update UI
                                    updateRelapseDataAdapter();
                                    adapter.notifyDataSetChanged();
                                    relapseHistory_lv.smoothScrollToPosition(relapseHistory.size());
                                } else {
                                    Snackbar.make(setView, R.string.date_selection, Snackbar.LENGTH_LONG)
                                                .show();
                                }
                            }
                        }, year, month, day);
                picker.show();
            }
        });
    }

    public boolean canSetNumberOfDays(DateTime updatedRelapseDate){
        long numberOfDays = positionModel.getNumberOfDays(updatedRelapseDate);
        if ( numberOfDays != -1) {
            daysViewTv.setText(numberOfDays + " Days");
            return true;
        }
        return false;
    }

}
