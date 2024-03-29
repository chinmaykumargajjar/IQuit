package com.spicycoder.iquit.controllers.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.spicycoder.iquit.R;
import com.spicycoder.iquit.controllers.Interfaces.AdapterToFragmentInterface;
import com.spicycoder.iquit.controllers.ViewFragment;
import com.spicycoder.iquit.models.AddictionUserModel;
import com.spicycoder.iquit.models.Utilities;
import com.google.android.material.snackbar.Snackbar;

import org.joda.time.DateTime;

public class RelapseListAdapter extends RecyclerView.Adapter<RelapseListAdapter.MyViewHolder> {
    private final AddictionUserModel positionModel;
    AdapterToFragmentInterface myAdapter;
    String TAG = RelapseListAdapter.class.getName();
    Context context;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView relapseDate, streakCountTV;
        public ImageButton deleteImageBtn;
        public MyViewHolder(ConstraintLayout v) {
            super(v);
            relapseDate = v.findViewById(R.id.relapseDate);
            streakCountTV = v.findViewById(R.id.streakCountTV);
            deleteImageBtn = v.findViewById(R.id.deleteImageBtn);
        }

    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RelapseListAdapter(AddictionUserModel positionModel, ViewFragment viewFragment,
                              Context context) {
        this.positionModel = positionModel;
        this.myAdapter = viewFragment;
        this.context = context;
        Log.i(TAG, "Adapter Initialized dataset size ="+positionModel.getRelapseHistory().size());
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RelapseListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        // create a new view
        ConstraintLayout v = (ConstraintLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.relapse_main_rv_raw, parent, false);
        RelapseListAdapter.MyViewHolder vh = new RelapseListAdapter.MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RelapseListAdapter.MyViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.relapseDate.setText(Utilities
                .getStringFromDate(positionModel
                        .getRelapseHistory()
                        .get(position)));
        DateTime previous = new DateTime();

        long numberOfDays = positionModel
                .getNumberOfDays(position);
        if(position == positionModel.getRelapseHistory().size()-1){
            holder.streakCountTV.setText("");
        }else if(numberOfDays != -1) {
            holder.streakCountTV.setText("Streak Length: "+numberOfDays);
        }
        holder.deleteImageBtn.setTag(position);
        holder.deleteImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(positionModel.getRelapseHistory().size() > 1) {
                    confirmDeleteAction(v);
                } else {
                    Snackbar.make(v, R.string.relapse_del, Snackbar.LENGTH_LONG)
                            .show();
                }

            }
        });
    }

    public void confirmDeleteAction(final View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle("Confirm");
        builder.setMessage("Are you sure you want to delete this relapse?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog
                Log.i(TAG, "Remove Item at =" + v.getTag());
                positionModel.removeRelapse(Integer.parseInt(v.getTag().toString()));
                notifyDataSetChanged();
                myAdapter.actNow();
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Do nothing
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return positionModel.getRelapseHistory().size();
    }
}