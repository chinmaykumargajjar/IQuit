package com.spicycoder.iquit.controllers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.spicycoder.iquit.R;
import com.spicycoder.iquit.controllers.Adapters.HomeListAdapter;
import com.spicycoder.iquit.controllers.Interfaces.AddDataPassInterface;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private MainActivity mainActivity;
    AddDataPassInterface addDataPassInterface;
    TextView nothing_found_tv;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        layoutManager = new LinearLayoutManager(this.getActivity());

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        mAdapter = new HomeListAdapter(mainActivity.addictionManager.getAddictionUserModels(),
                NavHostFragment.findNavController(HomeFragment.this),
                addDataPassInterface);
        recyclerView.setAdapter(mAdapter);
        if(mainActivity.addictionManager != null &&
                mainActivity.addictionManager.getAddictionUserModels().size() != 0){
            nothing_found_tv.setVisibility(View.INVISIBLE);
        } else {
            nothing_found_tv.setVisibility(View.VISIBLE);
        }
        setupDeleteFunction();
    }

    public void setupDeleteFunction(){
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.DOWN | ItemTouchHelper.UP) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                //Remove swiped item from list and notify the RecyclerView
                int position = viewHolder.getAdapterPosition();
                String name = mainActivity.addictionManager.getItemAtPosition(position)
                        .getAddiction()
                        .getName();
                confirmDeleteAction(position,name);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    public void confirmDeleteAction(final int position, String name){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Delete "+name+"?");
        builder.setMessage("This will permanently delete the habit. All recorded data will " +
                "be erased. Are you sure you want to permanently delete this Habit?");

        builder.setPositiveButton("NO", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                mAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("YES", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
                // Do nothing but close the dialog
                mainActivity.addictionManager.removeItemAtPosition(position);
                mAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.badHabitsRV);
        nothing_found_tv = view.findViewById(R.id.nothing_found_tv);
        mainActivity = (MainActivity) getActivity();
        mainActivity.fab.show();

    }
}
