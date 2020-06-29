package com.spicycoder.iquit.controllers.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.spicycoder.iquit.R;
import com.spicycoder.iquit.controllers.Interfaces.AddDataPassInterface;
import com.spicycoder.iquit.models.AddictionUserModel;

import java.util.ArrayList;

public class HomeListAdapter extends RecyclerView.Adapter<HomeListAdapter.MyViewHolder> {
    private ArrayList<AddictionUserModel> mDataset;
    NavController navController;
    AddDataPassInterface addDataPassInterface;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView habitName, daysCount;
        public Button emergencyBtn, viewBtn;
        public MyViewHolder(ConstraintLayout v) {
            super(v);
            habitName = v.findViewById(R.id.relapseDate);
            daysCount = v.findViewById(R.id.streakCountTV);
            emergencyBtn = v.findViewById(R.id.emergencyBtn);
            viewBtn = v.findViewById(R.id.delBtn);
        }

    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public HomeListAdapter(ArrayList<AddictionUserModel> myDataset, NavController navController,
                           AddDataPassInterface addDataPassInterface) {
        this.mDataset = myDataset;
        this.navController = navController;
        this.addDataPassInterface = addDataPassInterface;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public HomeListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        // create a new view
        ConstraintLayout v = (ConstraintLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.habits_main_rv_raw, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.habitName.setText(mDataset.get(position).getAddiction().getName());
        long numberOfDays = mDataset.get(position)
                .getCurrentStreak();
        if(numberOfDays != -1) {
            holder.daysCount.setText("Current Streak "+numberOfDays + " Days");
        }

        holder.viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDataPassInterface.goToViewFragment(position);
            }
        });

        holder.emergencyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //handle the click here.
                addDataPassInterface.goToEmergencyFragment(position);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}