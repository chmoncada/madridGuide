package com.charlesmoncada.madridguide;

import android.app.Application;
import android.content.Context;

import java.lang.ref.WeakReference;


public class MadridGuideApp extends Application {

    private static WeakReference<Context> appContext;

    @Override
    public void onCreate() {
        super.onCreate();

        // init your app

        appContext = new WeakReference<Context>(getApplicationContext());

    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    public static Context getAppContext() {
        return appContext.get();
    }
}
