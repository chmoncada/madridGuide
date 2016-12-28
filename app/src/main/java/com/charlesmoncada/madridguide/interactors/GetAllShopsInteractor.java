package com.charlesmoncada.madridguide.interactors;


import android.content.Context;

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
                    response.response(null);
                }
            }
        });
    }
}


