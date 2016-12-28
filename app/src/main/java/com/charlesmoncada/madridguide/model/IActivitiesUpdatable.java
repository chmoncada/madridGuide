package com.charlesmoncada.madridguide.model;


public interface IActivitiesUpdatable {
    void add(MadridActivity activity);
    void delete(MadridActivity activity);
    void edit(MadridActivity newActivity, long index);
}
