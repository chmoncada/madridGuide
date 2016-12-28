package com.charlesmoncada.madridguide.manager.net;


import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.charlesmoncada.madridguide.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Reader;
import java.io.StringReader;
import java.lang.ref.WeakReference;
import java.util.List;

public class NetworkManager {

    public interface GetShopsListener {
        public void getShopEntitiesSuccess(List<ShopEntity> result);
        public void getShopEntitiesDidFail();
    }

    public interface GetActivitiesListener {
        public void getActivityEntitiesSuccess(List<ActivityEntity> result);
        public void getActivityEntitiesDidFail();
    }

    WeakReference<Context> context;

    public NetworkManager(Context context) {
        this.context = new WeakReference<Context>(context);
    }

    public void getShopsFromServer(final GetShopsListener listener) {
        RequestQueue queue = Volley.newRequestQueue(context.get());
        String url = context.get().getString(R.string.shops_url);

        StringRequest request = new StringRequest(
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("JSON", response);
                        List<ShopEntity> shopResponse = parseResponse(response);
                        if (listener != null) {
                            listener.getShopEntitiesSuccess(shopResponse);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (listener != null) {
                            listener.getShopEntitiesDidFail();
                        }
                    }
                }
        );
        queue.add(request);
    }

    private List<ShopEntity> parseResponse(String response) {
        List<ShopEntity> result = null;
        try {
            Reader reader = new StringReader(response);
            Gson gson = new GsonBuilder().create();

            ShopResponse shopResponse = gson.fromJson(reader, ShopResponse.class);
            result = shopResponse.result;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public void getActivitiesFromServer(final GetActivitiesListener listener) {
        RequestQueue queue = Volley.newRequestQueue(context.get());
        String url = context.get().getString(R.string.activities_url);

        StringRequest request = new StringRequest(
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("JSON", response);
                        List<ActivityEntity> activityResponse = parseActivityResponse(response);
                        if (listener != null) {
                            listener.getActivityEntitiesSuccess(activityResponse);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (listener != null) {
                            listener.getActivityEntitiesDidFail();
                        }
                    }
                }
        );
        queue.add(request);
    }

    private List<ActivityEntity> parseActivityResponse(String response) {
        List<ActivityEntity> result = null;
        try {
            Reader reader = new StringReader(response);
            Gson gson = new GsonBuilder().create();

            ActivityResponse activityResponse = gson.fromJson(reader, ActivityResponse.class);
            result = activityResponse.result;
            //Log.v("result", String.valueOf(result));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
