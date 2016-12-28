package com.charlesmoncada.madridguide.interactors;


import android.content.Context;
import android.os.Looper;

import com.charlesmoncada.madridguide.manager.db.ShopDAO;
import com.charlesmoncada.madridguide.model.Shop;
import com.charlesmoncada.madridguide.model.Shops;

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
                for (Shop shop: shops.allShops()) {
                    success = dao.insert(shop) > 0;
                    if (!success) {
                        break;
                    }
                }

                Looper main = Looper.getMainLooper();

                // TODO: TIENE QUE IR AL HILO PRINCIPAL!!!!
                //main.getThread()
                if (response != null) {
                    response.response(success);
                }
            }
        }).start();


    }

}
