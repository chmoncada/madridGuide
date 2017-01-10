package com.charlesmoncada.madridguide.navigator;

import android.content.Intent;

import com.charlesmoncada.madridguide.activities.ActivitiesActivity;
import com.charlesmoncada.madridguide.activities.MadridActivityDetailActivity;
import com.charlesmoncada.madridguide.activities.MainActivity;
import com.charlesmoncada.madridguide.activities.ShopDetailActivity;
import com.charlesmoncada.madridguide.activities.ShopsActivity;
import com.charlesmoncada.madridguide.activities.SplashScreen;
import com.charlesmoncada.madridguide.model.MadridActivity;
import com.charlesmoncada.madridguide.model.Shop;
import com.charlesmoncada.madridguide.util.Constants;

public class Navigator {
    public static Intent navigateFromSplashScreenToMainActivity(final SplashScreen splashScreen) {
        final Intent i = new Intent(splashScreen, MainActivity.class);

        splashScreen.startActivity(i);

        return i;
    }

    public static Intent navigateFromMainActivityToShopsActivity(final MainActivity mainActivity) {

        final Intent i = new Intent(mainActivity, ShopsActivity.class);

        mainActivity.startActivity(i);

        return i;
    }

    public static Intent navigateFromShopsActivityToShopDetailActivity(final ShopsActivity shopsActivity, Shop detail) {

        final Intent i = new Intent(shopsActivity, ShopDetailActivity.class);

        i.putExtra(Constants.INTENT_KEY_DETAILS_SHOP, detail);

        shopsActivity.startActivity(i);

        return i;
    }

    public static Intent navigateFromActivitiesActivityToMadridActivityDetailActivity(final ActivitiesActivity activitiesActivity, MadridActivity detail) {

        final Intent i = new Intent(activitiesActivity, MadridActivityDetailActivity.class);

        i.putExtra(Constants.INTENT_KEY_DETAILS_ACTIVITY, detail);

        activitiesActivity.startActivity(i);

        return i;
    }

    public static Intent navigateFromMainActivityToActivitiesActivity(final MainActivity mainActivity) {

        final Intent i = new Intent(mainActivity, ActivitiesActivity.class);

        mainActivity.startActivity(i);

        return i;
    }

}
