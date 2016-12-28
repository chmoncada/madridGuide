package com.charlesmoncada.madridguide.model;


import java.util.List;

public interface IActivitiesIterable {
    long size();
    MadridActivity get(long index);
    List<MadridActivity> allActivities();
}
