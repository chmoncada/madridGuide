package com.charlesmoncada.madridguide.util;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.Calendar;

public class DateUtils {

    public static void saveDateInSharedPreferences(Activity activity) {

        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        long millis = Calendar.getInstance().getTime().getTime();
        editor.putLong("SavedDate", millis);
        editor.apply();
    }

    private static long readDateFromSharedPreferences(Activity activity) {

        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        long savedDate = sharedPref.getLong("SavedDate", 0);
        return savedDate;
    }

    public static boolean isCacheNotLongerValid(Activity activity) {

        // get actual date
        Calendar calendar = Calendar.getInstance();

        // get date from Shared Preferences
        long fecha = DateUtils.readDateFromSharedPreferences(activity);

        // compare date if actual date is more than sharedPreferences date + 7 days
        Calendar limitDate = Calendar.getInstance();
        limitDate.setTimeInMillis(fecha);
        limitDate.add(Calendar.DAY_OF_MONTH, 7);

        return calendar.compareTo(limitDate) > 0;
    }


}
