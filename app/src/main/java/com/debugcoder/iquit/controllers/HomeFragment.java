package com.debugcoder.iquit.controllers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.debugcoder.iquit.R;
import com.debugcoder.iquit.models.AddictionManager;
import com.debugcoder.iquit.models.AddictionType;
import com.debugcoder.iquit.models.AddictionUserModel;

import org.joda.time.DateTime;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
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

        AddictionManager addictionManager = new AddictionManager();
        addictionManager.addNewAddiction(new AddictionUserModel("I really need to stop " +
                "watching porn",
                new ArrayList<DateTime>(),
                new DateTime("2020-1-13T21:39:45.618-08:00"),
                new AddictionType("Porn  Free")));

        addictionManager.addNewAddiction(new AddictionUserModel("I really need to stop " +
                "drinking alcohol",
                new ArrayList<DateTime>(),
                new DateTime("2019-12-13T21:39:45.618-08:00"),
                new AddictionType("Alcohol")));

        addictionManager.addNewAddiction(new AddictionUserModel("I really need to stop " +
                "Biting Nail",
                new ArrayList<DateTime>(),
                new DateTime("2020-2-13T21:39:45.618-08:00"),
                new AddictionType("Nail Bite")));

        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(addictionManager.getAddictionUserModels(),
                NavHostFragment.findNavController(HomeFragment.this));
        recyclerView.setAdapter(mAdapter);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.badHabitsRV);


//        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                NavHostFragment.findNavController(FirstFragment.this)
//                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
//            }
//        });
    }
}