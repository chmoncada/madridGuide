package com.charlesmoncada.madridguide.activities;


import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;

import com.charlesmoncada.madridguide.R;
import com.charlesmoncada.madridguide.fragments.ShopsFragment;
import com.charlesmoncada.madridguide.manager.db.DBConstants;
import com.charlesmoncada.madridguide.manager.db.ShopDAO;
import com.charlesmoncada.madridguide.manager.db.provider.MadridGuideProvider;
import com.charlesmoncada.madridguide.model.Shop;
import com.charlesmoncada.madridguide.model.Shops;
import com.charlesmoncada.madridguide.navigator.Navigator;
import com.charlesmoncada.madridguide.views.OnElementClick;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ShopsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, OnMapReadyCallback {

    private ShopsFragment shopsFragment;
    private SupportMapFragment mapFragment;
    private GoogleMap googleMap;
    private Shops shops;

    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shops);

        shopsFragment = (ShopsFragment) getSupportFragmentManager().findFragmentById(R.id.activity_shops_fragment_shops);



        LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.initLoader(0, null, this);
    }

//    // 1st attempt at async cursor load: works!
//    public void getShops() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                ShopDAO dao = new ShopDAO(ShopsActivity.this);
//
//                List<Shop> shopList = dao.query();
//                final Shops shops = Shops.build(shopList);
//
//                ShopsActivity.this.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        shopsFragment.setShops(shops);
//                    }
//                });
//            }
//        });
//    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader loader = new CursorLoader(this,
                MadridGuideProvider.SHOPS_URI,
                DBConstants.TABLE_SHOP_ALL_COLUMNS,            // projection
                null,                               // where
                null,                               // campos del where
                null);                              // order

        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        // Get the model
        shops = ShopDAO.getShops(data);

        // setup the map
        if (googleMap == null) {
            setupMap();
        }

        // Setup the List Recycler view Fragment
        setupShopsFragment(shops);


    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private void setupShopsFragment(Shops shops) {
        shopsFragment.setOnElementClickListener(new OnElementClick<Shop>() {
            @Override
            public void elementClicked(Shop shop, int position) {
                Navigator.navigateFromShopsActivityToShopDetailActivity(ShopsActivity.this, shop);
            }
        });

        shopsFragment.setShops(shops);
    }

    private void setupMap() {
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;

        showUserPosition();
        moveCamera();
        showShopMarkers();
    }

    private void moveCamera() {
        CameraPosition cameraPosition = new CameraPosition.Builder().target(
                new LatLng(40.417005,-3.703423)).zoom(12).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    public void showShopMarkers() {
        for (Shop shop : shops.allShops()) {
            float latitude = shop.getLatitude();
            float longitude = shop.getLongitude();
            LatLng position = new LatLng(latitude, longitude);
            String name = shop.getName();
            googleMap.addMarker(new MarkerOptions().position(position).title(name));
        }
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
                return;
            }
        }
    }
}
