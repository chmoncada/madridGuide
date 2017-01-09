package com.charlesmoncada.madridguide.util;


import com.charlesmoncada.madridguide.model.Iterable;
import com.charlesmoncada.madridguide.model.Shop;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapsUtils {

    public static void moveCameraToMadridCenter(GoogleMap googleMap) {
        CameraPosition cameraPosition = new CameraPosition.Builder().target(
                new LatLng(40.417005,-3.703423)).zoom(12).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    public static <T> void showMarkers(List<T> list, GoogleMap googleMap) {
        for (T each : list) {

            Shop shopElement;
            float latitude = 0;
            float longitude = 0;
            String name = "";

            if (each instanceof Shop) {
                shopElement = (Shop) each;
                latitude = shopElement.getLatitude();
                longitude = shopElement.getLongitude();
                name = shopElement.getName();
            }

            LatLng position = new LatLng(latitude, longitude);
            Marker marker = googleMap.addMarker(new MarkerOptions().position(position).title(name));
            marker.setTag(each);
        }
    }

    public static <T> void updateMarkers(Iterable<T> list, GoogleMap googleMap) {
        googleMap.clear();
        MapsUtils.showMarkers(list.all(),googleMap);
    }

}
