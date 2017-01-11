package com.charlesmoncada.madridguide;

import android.app.Application;
import android.content.Context;

import java.lang.ref.WeakReference;
import java.util.Locale;


public class MadridGuideApp extends Application {

    private static WeakReference<Context> appContext;
    public static String defSystemLanguage;

    @Override
    public void onCreate() {
        super.onCreate();

        // init your app
        appContext = new WeakReference<Context>(getApplicationContext());
        defSystemLanguage = Locale.getDefault().getLanguage();

    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    public static Context getAppContext() {
        return appContext.get();
    }
}
