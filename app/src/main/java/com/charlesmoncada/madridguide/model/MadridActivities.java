package com.charlesmoncada.madridguide.model;


import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MadridActivities implements Iterable, Updatable {

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
    public List<MadridActivity> all() {
        return activities;
    }

    @Override
    public void add(Object element) {
        MadridActivity activity = (MadridActivity) element;
        activities.add(activity);
    }

    @Override
    public void delete(Object element) {
        MadridActivity activity = (MadridActivity) element;
        activities.remove(activity);
    }

    @Override
    public void edit(Object newElement, long index) {
        MadridActivity newActivity = (MadridActivity) newElement;
        activities.set((int) index, newActivity);
    }

    public List<MadridActivity> query(String query) {

        List<MadridActivity> result = new LinkedList<>();

        for (MadridActivity activity: activities) {
            if (containsIgnoreCase(activity.getName(), query)) {
                result.add(activity);
            }
        }

        return  result;

    }

    private static boolean containsIgnoreCase(String src, String what) {
        final int length = what.length();
        if (length == 0)
            return true; // Empty string is contained

        final char firstLo = Character.toLowerCase(what.charAt(0));
        final char firstUp = Character.toUpperCase(what.charAt(0));

        for (int i = src.length() - length; i >= 0; i--) {
            // Quick check before calling the more expensive regionMatches() method:
            final char ch = src.charAt(i);
            if (ch != firstLo && ch != firstUp)
                continue;

            if (src.regionMatches(true, i, what, 0, length))
                return true;
        }

        return false;
    }

}
