package com.charlesmoncada.madridguide.manager.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.charlesmoncada.madridguide.model.MadridActivities;
import com.charlesmoncada.madridguide.model.MadridActivity;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;

import static com.charlesmoncada.madridguide.manager.db.DBConstants.*;

public class ActivityDAO implements DAOPersistable<MadridActivity> {

    private WeakReference<Context> context;
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public ActivityDAO(Context context, DBHelper dbHelper) {
        this.context = new WeakReference<>(context);
        this.dbHelper = dbHelper;
        this.db = dbHelper.getDB();
    }

    public ActivityDAO(Context context) {
        this(context, DBHelper.getInstance(context));
    }


    @Override
    public long insert(@NonNull MadridActivity activity) {
        if (activity == null) {
            return 0;
        }

        db.beginTransaction();
        long id = DBHelper.INVALID_ID;
        try {
            id = db.insert(TABLE_ACTIVITY, KEY_ACTIVITY_NAME, this.getContentValues(activity));
            activity.setId(id);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        return id;
    }

    public static @NonNull ContentValues getContentValues(final @NonNull MadridActivity activity) {
        final ContentValues contentValues = new ContentValues();

        if (activity == null) {
            return contentValues;
        }

        contentValues.put(KEY_ACTIVITY_ADDRESS, activity.getAddress());
        contentValues.put(KEY_ACTIVITY_DESCRIPTION_ES, activity.getDescriptionEs());
        contentValues.put(KEY_ACTIVITY_DESCRIPTION_EN, activity.getDescriptionEn());
        contentValues.put(KEY_ACTIVITY_IMAGE_URL, activity.getImageUrl());
        contentValues.put(KEY_ACTIVITY_LOGO_IMAGE_URL, activity.getLogoImgUrl());
        contentValues.put(KEY_ACTIVITY_NAME, activity.getName());
        contentValues.put(KEY_ACTIVITY_LATITUDE, activity.getLatitude());
        contentValues.put(KEY_ACTIVITY_LONGITUDE, activity.getLongitude());
        contentValues.put(KEY_ACTIVITY_URL, activity.getUrl());

        return contentValues;
    }

    public static @NonNull MadridActivity getMadridActivityFromContentValues(final @NonNull ContentValues contentValues) {
        final MadridActivity activity = new MadridActivity(1, "");

        activity.setAddress(contentValues.getAsString(KEY_ACTIVITY_ADDRESS));
        activity.setDescriptionEs(contentValues.getAsString(KEY_ACTIVITY_DESCRIPTION_ES));
        activity.setDescriptionEn(contentValues.getAsString(KEY_ACTIVITY_DESCRIPTION_EN));
        activity.setImageUrl(contentValues.getAsString(KEY_ACTIVITY_IMAGE_URL));
        activity.setLogoImgUrl(contentValues.getAsString(KEY_ACTIVITY_LOGO_IMAGE_URL));
        activity.setName(contentValues.getAsString(KEY_ACTIVITY_NAME));
        activity.setLatitude(contentValues.getAsFloat(KEY_ACTIVITY_LATITUDE));
        activity.setLongitude(contentValues.getAsFloat(KEY_ACTIVITY_LONGITUDE));
        activity.setUrl(contentValues.getAsString(KEY_ACTIVITY_URL));

        return activity;
    }

    @Override
    public void update(long id, @NonNull MadridActivity data) {

    }

    @Override
    public int delete(long id) {
        return db.delete(TABLE_ACTIVITY, KEY_ACTIVITY_ID + " = ?", new String[]{ ""+ id});
    }

    @Override
    public int deleteAll() {
        int result = db.delete(TABLE_ACTIVITY, null,null);// 2nd way
        Log.v("DELETE ALL:", String.valueOf(result));
        //db.delete(TABLE_ACTIVITY, null, null);

        return result;
    }

    @Nullable
    @Override
    public Cursor queryCursor() {
        Cursor c = db.query(TABLE_ACTIVITY, TABLE_ACTIVITY_ALL_COLUMNS, null, null, null, null, KEY_ACTIVITY_ID);

        if (c!= null && c.getCount() > 0) {
            c.moveToFirst();
        }
        return c;
    }

    @Override
    public @Nullable MadridActivity query(final long id) {
        Cursor c = db.query(TABLE_ACTIVITY, TABLE_ACTIVITY_ALL_COLUMNS, KEY_ACTIVITY_ID + " = " + id, null, null, null, KEY_ACTIVITY_ID);
        if (c != null && c.getCount() == 1) {
            c.moveToFirst();
        } else {
            return null;
        }
        MadridActivity activity = getActivity(c);
        return activity;
    }

    @NonNull
    public static MadridActivity getActivity(Cursor c) {
        long identifier = c.getLong(c.getColumnIndex(KEY_ACTIVITY_ID));
        String name = c.getString(c.getColumnIndex(KEY_ACTIVITY_NAME));
        MadridActivity activity = new MadridActivity(identifier, name);

        activity.setAddress(c.getString(c.getColumnIndex(KEY_ACTIVITY_ADDRESS)));
        activity.setDescriptionEs(c.getString(c.getColumnIndex(KEY_ACTIVITY_DESCRIPTION_ES)));
        activity.setDescriptionEn(c.getString(c.getColumnIndex(KEY_ACTIVITY_DESCRIPTION_EN)));
        activity.setImageUrl(c.getString(c.getColumnIndex(KEY_ACTIVITY_IMAGE_URL)));
        activity.setLogoImgUrl(c.getString(c.getColumnIndex(KEY_ACTIVITY_LOGO_IMAGE_URL)));
        activity.setLatitude(c.getFloat(c.getColumnIndex(KEY_ACTIVITY_LATITUDE)));
        activity.setLongitude(c.getFloat(c.getColumnIndex(KEY_ACTIVITY_LONGITUDE)));
        activity.setUrl(c.getString(c.getColumnIndex(KEY_ACTIVITY_URL)));

        return activity;
    }

    @Nullable
    @Override
    public List<MadridActivity> query() {
        Cursor c = queryCursor();

        if (c == null || !c.moveToFirst()) {
            return null;
        }

        List<MadridActivity> activities = new LinkedList<>();

        c.moveToFirst();
        do {
            MadridActivity activity = getActivity(c);
            activities.add(activity);
        } while (c.moveToNext());

        return activities;
    }

    public Cursor queryCursor(long id) {
        Cursor c = db.query(TABLE_ACTIVITY, TABLE_ACTIVITY_ALL_COLUMNS, "ID = " + id, null, null, null, KEY_ACTIVITY_ID);
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
        }
        return c;
    }

    @NonNull
    public static MadridActivities getActivities(Cursor data) {
        List<MadridActivity> activityList = new LinkedList<>();

        while (data.moveToNext()) {
            MadridActivity activity = ActivityDAO.getActivity(data);
            activityList.add(activity);
        }

        return MadridActivities.build(activityList);
    }


}
