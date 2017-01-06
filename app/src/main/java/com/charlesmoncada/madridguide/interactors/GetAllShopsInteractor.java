package com.charlesmoncada.madridguide.interactors;


import android.content.Context;
import android.util.Log;

import com.charlesmoncada.madridguide.manager.db.ShopDAO;
import com.charlesmoncada.madridguide.manager.net.NetworkManager;
import com.charlesmoncada.madridguide.manager.net.ShopEntity;
import com.charlesmoncada.madridguide.model.Shop;
import com.charlesmoncada.madridguide.model.Shops;
import com.charlesmoncada.madridguide.model.mappers.ShopEntityShopMapper;

import java.util.List;

public class GetAllShopsInteractor {

    public interface GetAllShopsInteractorResponse {
        public void response(Shops shops);
    }

    public void execute(final Context context, final GetAllShopsInteractorResponse response) {

        ShopDAO dao = new ShopDAO(context);

        if (dao.query() != null ) {
            Log.v("SHOPSDAO", "YA TENEMOS DATOS, NO DEBERIAMOS BAJARLOS DE NUEVO");
            if (response != null) {
                response.response(null);
            }

        } else {
            Log.v("SHOPSACTIVITY", "NO TENEMOS DATOS, BAJANDO...");
            NetworkManager networkManager = new NetworkManager(context);
            networkManager.getShopsFromServer(new NetworkManager.GetShopsListener() {
                @Override
                public void getShopEntitiesSuccess(List<ShopEntity> result) {

                    List<Shop> shops = new ShopEntityShopMapper().map(result);
                    if (response != null) {
                        response.response(Shops.build(shops));
                    }
                }

                @Override
                public void getShopEntitiesDidFail() {
                    if (response != null) {
                        Log.v("SHOPSACTIVITY", "ERROR BAJANDO LOS SHOPS");
                        response.response(null);
                    }
                }
            });
        }


    }
}


