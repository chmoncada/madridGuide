package com.charlesmoncada.madridguide.adapters;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.charlesmoncada.madridguide.R;
import com.charlesmoncada.madridguide.model.MadridActivities;
import com.charlesmoncada.madridguide.model.MadridActivity;
import com.charlesmoncada.madridguide.util.OnElementClick;
import com.charlesmoncada.madridguide.views.ActivityRowViewHolder;

import java.util.LinkedList;
import java.util.List;

public class ActivitiesAdapter extends RecyclerView.Adapter<ActivityRowViewHolder>{

    private final LayoutInflater layoutInflater;
    private final MadridActivities activities;

    private List<OnElementClick<MadridActivity>> listeners;

    public ActivitiesAdapter(MadridActivities activities, Context context) {
        this.activities = activities;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ActivityRowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.row_activity, parent, false);

        return new ActivityRowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ActivityRowViewHolder row, int position) {

        final MadridActivity activity = activities.get(position);

        row.setActivity(activity);

        final int positionForListener = row.getAdapterPosition();

        row.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (OnElementClick<MadridActivity> listener: getListeners()) {
                    listener.elementClicked(activity, positionForListener);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return (int) activities.size();
    }

    public List<OnElementClick<MadridActivity>> getListeners() {
        if (listeners == null) {
            listeners = new LinkedList<>();
        }
        return listeners;
    }

    public void  setOnElementClickListener(@NonNull final OnElementClick<MadridActivity> listener) {
        getListeners().add(listener);
    }

}
