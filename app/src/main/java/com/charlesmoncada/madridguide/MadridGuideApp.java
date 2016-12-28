package com.charlesmoncada.madridguide;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.charlesmoncada.madridguide.interactors.CacheAllActivitiesInteractor;
import com.charlesmoncada.madridguide.interactors.CacheAllShopsInteractor;
import com.charlesmoncada.madridguide.interactors.GetAllActivitiesInteractor;
import com.charlesmoncada.madridguide.interactors.GetAllShopsInteractor;
import com.charlesmoncada.madridguide.model.MadridActivities;
import com.charlesmoncada.madridguide.model.Shops;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;


public class MadridGuideApp extends Application {

    private static WeakReference<Context> appContext;

    @Override
    public void onCreate() {
        super.onCreate();

        // init your app
        appContext = new WeakReference<Context>(getApplicationContext());

        Picasso.with(getApplicationContext()).setLoggingEnabled(true);
        Picasso.with(getApplicationContext()).setIndicatorsEnabled(false);

        new GetAllShopsInteractor().execute(getApplicationContext(),
                new GetAllShopsInteractor.GetAllShopsInteractorResponse() {
                    @Override
                    public void response(Shops shops) {
                        new CacheAllShopsInteractor().execute(getApplicationContext(),
                                shops, new CacheAllShopsInteractor.CacheAllShopsInteractorResponse() {
                                    @Override
                                    public void response(boolean sucess) {
                                        Log.v("GUIDE", "GUARDE EN DISCO LOS SHOPS");
                                    }
                                });
                    }
                }
        );

        new GetAllActivitiesInteractor().execute(getApplicationContext(),
                new GetAllActivitiesInteractor.GetAllActivitiesInteractorResponse() {
                    @Override
                    public void response(MadridActivities activities) {

                        new CacheAllActivitiesInteractor().execute(getApplicationContext(),
                                activities, new CacheAllActivitiesInteractor.CacheAllActivitiesInteractorResponse() {
                                    @Override
                                    public void response(boolean sucess) {
                                        Log.v("GUIDE", "GUARDE EN DISCO LAS ACTIVITIES");
                                    }
                                });
                    }

                });

    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    public static Context getAppContext() {
        return appContext.get();
    }
}
