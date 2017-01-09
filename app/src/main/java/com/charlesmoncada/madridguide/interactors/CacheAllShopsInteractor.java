package com.charlesmoncada.madridguide.interactors;


import android.content.Context;

import com.charlesmoncada.madridguide.manager.db.ShopDAO;
import com.charlesmoncada.madridguide.model.Shop;
import com.charlesmoncada.madridguide.model.Shops;
import com.charlesmoncada.madridguide.util.MainThread;

public class CacheAllShopsInteractor {

    public interface CacheAllShopsInteractorResponse {
        public void response(boolean sucess);
    }

    public void execute(final Context context, final Shops shops, final CacheAllShopsInteractorResponse response) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ShopDAO dao = new ShopDAO(context);

                boolean success = true;
                for (Shop shop: shops.all()) {
                    success = dao.insert(shop) > 0;
                    if (!success) {
                        break;
                    }
                }

                final boolean successResponse = success;

                MainThread.run(new Runnable() {
                    @Override
                    public void run() {
                        if (response != null) {
                            response.response(successResponse);
                        }
                    }
                });

            }
        }).start();


    }

}
