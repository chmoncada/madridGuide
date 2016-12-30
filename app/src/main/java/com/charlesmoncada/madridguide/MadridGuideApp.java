package com.charlesmoncada.madridguide;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

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
        Log.v("MadridGuide", defSystemLanguage);

        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttpDownloader(this,Integer.MAX_VALUE));
        Picasso built = builder.build();
        built.setIndicatorsEnabled(true);
        built.setLoggingEnabled(false);
        Picasso.setSingletonInstance(built);


    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    public static Context getAppContext() {
        return appContext.get();
    }
}
