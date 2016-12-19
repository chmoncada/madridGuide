package com.charlesmoncada.madridguide.navigator;

import android.content.Intent;

import com.charlesmoncada.madridguide.activities.MainActivity;
import com.charlesmoncada.madridguide.activities.ShopsActivity;

public class Navigator {
    public static Intent navigateFromMainActivityToShopsActivity(final MainActivity mainActivity) {

        final Intent i = new Intent(mainActivity, ShopsActivity.class);

        mainActivity.startActivity(i);

        return i;
    }
}
