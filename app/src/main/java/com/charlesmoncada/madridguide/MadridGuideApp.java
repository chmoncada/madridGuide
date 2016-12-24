package com.charlesmoncada.madridguide;

import android.app.Application;
import android.content.Context;

import com.charlesmoncada.madridguide.manager.db.ShopDAO;
import com.charlesmoncada.madridguide.manager.net.NetworkManager;
import com.charlesmoncada.madridguide.manager.net.ShopEntity;
import com.charlesmoncada.madridguide.model.Shop;
import com.charlesmoncada.madridguide.model.mappers.ShopEntityShopMapper;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.util.List;


public class MadridGuideApp extends Application {

    private static WeakReference<Context> appContext;

    @Override
    public void onCreate() {
        super.onCreate();

        // init your app

        appContext = new WeakReference<Context>(getApplicationContext());

        // insert test data in DB
        //insertTestDataInDB();

        Picasso.with(getApplicationContext()).setLoggingEnabled(true);
        Picasso.with(getApplicationContext()).setIndicatorsEnabled(true);

        // testing
        NetworkManager networkManager = new NetworkManager(getApplicationContext());
        networkManager.getShopsFromServer(new NetworkManager.GetShopsListener() {
            @Override
            public void getShopEntitiesSuccess(List<ShopEntity> result) {
                List<Shop> shops = new ShopEntityShopMapper().map(result);
                ShopDAO dao = new ShopDAO(getApplicationContext());

                for (Shop shop: shops) {
                    dao.insert(shop);
                }
            }

            @Override
            public void getShopEntitiesDidFail() {

            }
        });


    }

//    private void insertTestDataInDB() {
//        ShopDAO dao = new ShopDAO(getApplicationContext());
//        for (int i = 0; i < 20; i++) {
//            Shop shop = new Shop(1, "Shop " + i).setLogoImgUrl("http://platea.pntic.mec.es/~mmotta/web11ab/elfary.jpg");
//            dao.insert(shop);
//        }
//    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    public static Context getAppContext() {
        return appContext.get();
    }
}
