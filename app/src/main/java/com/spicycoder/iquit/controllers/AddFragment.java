package com.spicycoder.iquit.controllers;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.spicycoder.iquit.R;
import com.spicycoder.iquit.controllers.Interfaces.AddDataPassInterface;
import com.spicycoder.iquit.models.AddictionType;
import com.spicycoder.iquit.models.AddictionUserModel;
import com.spicycoder.iquit.models.DeviceCheckResult;
import com.spicycoder.iquit.models.Utilities;
import com.google.android.material.snackbar.Snackbar;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class AddFragment extends Fragment {
    AddDataPassInterface addDataPassInterface;
    AddictionUserModel addictionUserModel;
    Spinner chooseAddSpinner;
    EditText purposeEt;
    DateTime updatedRelapseDate;
    TextView lastRelapseDate_tv;
    String TAG = AddFragment.class.toString();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH)+1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        updatedRelapseDate = new DateTime(year+"-"+month+"-"
                +day);
        try
        {
            addDataPassInterface = (MainActivity) this.getActivity();
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(context.toString()+ " must implement OnImageClickListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupSpinner(view);
        Log.i(TAG, "Add fragment onViewCreated!");
        purposeEt =  view.findViewById(R.id.purpose_edittext);
        lastRelapseDate_tv = view.findViewById(R.id.lastRelapseDate_tv);
        lastRelapseDate_tv.setText(Utilities
                .getStringFromDate(updatedRelapseDate));
        setupDatePicker(view);

        view.findViewById(R.id.cancel__add_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(AddFragment.this)
                        .navigate(R.id.from_Add_to_Home_Fragment);
            }
        });

        view.findViewById(R.id.add_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Add Button Clicked!");
                String purpose = purposeEt.getText().toString();
                String addictionName = chooseAddSpinner.getSelectedItem().toString();
                addictionUserModel = new AddictionUserModel(purpose,
                        updatedRelapseDate,
                        new AddictionType(addictionName));

                if (canAddAddiction(addictionName).isResult()) {
                    addDataPassInterface.passData(addictionUserModel);
                    NavHostFragment.findNavController(AddFragment.this)
                                .navigate(R.id.from_Add_to_Home_Fragment);
                }  else {
                    Snackbar.make(view,canAddAddiction(addictionName).getResultString(),
                            Snackbar.LENGTH_SHORT)
                            .show();
                }
            }
        });
    }

    public DeviceCheckResult canAddAddiction(String addictionName){
    boolean result = true;
    String resultString = null;
    
    if(((MainActivity)getActivity())
            .addictionManager
            .doesAddictionExist(addictionName)){
        result = false;
        resultString =addictionName+" already exists!";
    } else if(chooseAddSpinner.getSelectedItemPosition() == 0){
        result = false;
        resultString ="Please select addiction type!";
    } else if (addictionUserModel.getCurrentStreak() <= -2){
        result = false;
        resultString = getResources().getString(R.string.date_selection);
    }
    
    return new DeviceCheckResult(result,resultString);
    }
    
    public void setupSpinner(View view){
        List<String> spinnerArray =  new ArrayList<String>();
        spinnerArray.add("Please select a Item");
        spinnerArray.add("NailBiting");
        spinnerArray.add("Alcohol");
        spinnerArray.add("PornFree");
        spinnerArray.add("SocialMedia");
        spinnerArray.add("Netflix");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chooseAddSpinner = view.findViewById(R.id.chooseAddSpinner);
        chooseAddSpinner.setAdapter(adapter);
    }

    public void setupDatePicker(View view){
        Calendar cldr = Calendar.getInstance();
        final int day = cldr.get(Calendar.DAY_OF_MONTH);
        final int month = cldr.get(Calendar.MONTH);
        final int year = cldr.get(Calendar.YEAR);

        view.findViewById(R.id.update_date_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog picker = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                monthOfYear++;

                                updatedRelapseDate = Utilities.getDateFromNumbers(dayOfMonth,monthOfYear,year);

                                lastRelapseDate_tv.setText(Utilities
                                        .getStringFromDate(updatedRelapseDate));
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
}
