package com.charlesmoncada.madridguide.model;


import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class MadridActivities implements IActivitiesIterable, IActivitiesUpdatable {

    List<MadridActivity> activities;

    public static @NonNull MadridActivities build(@NonNull final List<MadridActivity> activityList) {
        MadridActivities activities = new MadridActivities(activityList);

        if (activityList == null) {
            activities.activities = new ArrayList<>();
        }

        return activities;
    }

    public static @NonNull MadridActivities build() {
        return build(new ArrayList<MadridActivity>());
    }

    private MadridActivities(List<MadridActivity> activities) {
        this.activities = activities;
    }

    private MadridActivities() {
    }


    @Override
    public long size() {
        return activities.size();
    }

    @Override
    public MadridActivity get(long index) {
        return activities.get((int) index);
    }

    @Override
    public List<MadridActivity> allActivities() {
        return activities;
    }


    @Override
    public void add(MadridActivity activity) {
        activities.add(activity);
    }

    @Override
    public void delete(MadridActivity activity) {
        activities.remove(activity);
    }

    @Override
    public void edit(MadridActivity newActivity, long index) {
        activities.set((int) index, newActivity);
    }
}
