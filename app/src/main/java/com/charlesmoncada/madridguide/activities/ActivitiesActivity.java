package com.charlesmoncada.madridguide.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.charlesmoncada.madridguide.R;
import com.charlesmoncada.madridguide.fragments.ActivitiesFragment;
import com.charlesmoncada.madridguide.interactors.GetAllActivitiesFromLocalCacheInteractor;
import com.charlesmoncada.madridguide.model.MadridActivities;
import com.charlesmoncada.madridguide.model.MadridActivity;
import com.charlesmoncada.madridguide.navigator.Navigator;
import com.charlesmoncada.madridguide.util.MapsUtils;
import com.charlesmoncada.madridguide.util.OnElementClick;
import com.charlesmoncada.madridguide.views.MapInfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;

import java.util.List;

public class ActivitiesActivity extends AppCompatActivity implements OnMapReadyCallback {

    private ActivitiesFragment activitiesFragment;
    private GoogleMap googleMap;
    private MadridActivities activities;

    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities);

        activitiesFragment = (ActivitiesFragment) getSupportFragmentManager().findFragmentById(R.id.activity_activities_fragment_activities);

        GetAllActivitiesFromLocalCacheInteractor interactor = new GetAllActivitiesFromLocalCacheInteractor();
        interactor.execute(this, new GetAllActivitiesFromLocalCacheInteractor.GetAllActivitiesFromLocalCacheInteractorResponse() {
            @Override
            public void response(MadridActivities results) {

                activities = results;
                // setup the map
                if (googleMap == null) {
                    setupMap();
                }

                setupActivitiesFragment(activities);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_shop_detail_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.activity_shop_detail_menu_search);

        final SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                updateViews(newText);
                return false;
            }
        });

        return true;
    }

    private void setupActivitiesFragment(MadridActivities activities) {
        activitiesFragment.setOnElementClickListener(new OnElementClick<MadridActivity>() {
            @Override
            public void elementClicked(MadridActivity activity, int position) {
                Navigator.navigateFromActivitiesActivityToMadridActivityDetailActivity(ActivitiesActivity.this, activity);
            }
        });
        activitiesFragment.setActivities(activities);
    }

    private void updateViews(String text) {
        List<MadridActivity> results = activities.query(text);
        MadridActivities resultsActivity = MadridActivities.build(results);
        MapsUtils.updateMarkers(resultsActivity, googleMap);
        setupActivitiesFragment(resultsActivity);
    }

    private void setupMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.activities_map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
        showUserPosition();
        MapsUtils.moveCameraToMadridCenter(googleMap);
        MapsUtils.showMarkers(activities.all(), googleMap);

        googleMap.setInfoWindowAdapter(new MapInfoWindowAdapter(getLayoutInflater()));

        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                MadridActivity activity = (MadridActivity) marker.getTag();
                marker.hideInfoWindow();
                Navigator.navigateFromActivitiesActivityToMadridActivityDetailActivity(ActivitiesActivity.this, activity);
            }
        });
    }

    private void showUserPosition() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        } else {
            googleMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showUserPosition();
                }
            }
        }
    }

}
