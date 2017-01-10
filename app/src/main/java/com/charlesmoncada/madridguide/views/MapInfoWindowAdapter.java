package com.charlesmoncada.madridguide.views;


import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.charlesmoncada.madridguide.R;
import com.charlesmoncada.madridguide.model.MadridActivity;
import com.charlesmoncada.madridguide.model.Shop;
import com.charlesmoncada.madridguide.util.MarkerCallback;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class MapInfoWindowAdapter implements GoogleMap.InfoWindowAdapter{

    private final View infoView;
    private LayoutInflater mapInflater;

    public MapInfoWindowAdapter(LayoutInflater inflater) {
        mapInflater = inflater;
        infoView = mapInflater.inflate(R.layout.map_info_window, null);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {

        final TextView name = (TextView) infoView.findViewById(R.id.map_info_window_name);
        final ImageView logo = (ImageView) infoView.findViewById(R.id.map_info_window_logo);

        Shop shop;
        MadridActivity activity;

        if (marker.getTag() instanceof Shop) {
            shop = (Shop) marker.getTag();
            name.setText(shop.getName());
            Picasso.with(mapInflater.getContext())
                    .load(shop.getLogoImgUrl())
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(logo, new MarkerCallback(marker));

        } else if (marker.getTag() instanceof MadridActivity) {
            activity = (MadridActivity) marker.getTag();
            name.setText(activity.getName());
            Picasso.with(mapInflater.getContext())
                    .load(activity.getLogoImgUrl())
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(logo, new MarkerCallback(marker));
        }

        return infoView;
    }
}
