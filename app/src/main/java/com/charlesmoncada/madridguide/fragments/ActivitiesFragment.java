package com.charlesmoncada.madridguide.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.charlesmoncada.madridguide.R;
import com.charlesmoncada.madridguide.adapters.ActivitiesAdapter;
import com.charlesmoncada.madridguide.model.MadridActivities;
import com.charlesmoncada.madridguide.model.MadridActivity;
import com.charlesmoncada.madridguide.util.OnElementClick;

public class ActivitiesFragment extends Fragment {
    private RecyclerView activitiesRecyclerView;
    private ActivitiesAdapter adapter;
    private MadridActivities activities;

    private OnElementClick<MadridActivity> listener;

    public ActivitiesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activities, container, false);
        activitiesRecyclerView = (RecyclerView) view.findViewById(R.id.activitiess_recycler_view);
        activitiesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setTitle(R.string.activities_activity_title);
        //activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //updateUI();

        return view;
    }

    private void updateUI() {

        adapter = new ActivitiesAdapter(getActivities(), getActivity());
        activitiesRecyclerView.setAdapter(adapter);

        adapter.setOnElementClickListener(new OnElementClick<MadridActivity>() {

            @Override
            public void elementClicked(MadridActivity activity, int position) {
                if (listener != null) {
                    listener.elementClicked(activity, position);
                }
            }

        });

    }

    private MadridActivities getActivities() {
        return activities;
    }

    public void setActivities(MadridActivities activities) {
        this.activities = activities;

        updateUI();
    }

    public OnElementClick<MadridActivity> getListener() {
        return listener;
    }

    public  void setOnElementClickListener(@NonNull final OnElementClick<MadridActivity> listener) {
        this.listener = listener;
    }

}
