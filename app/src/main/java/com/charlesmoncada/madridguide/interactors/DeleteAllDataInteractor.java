package com.charlesmoncada.madridguide.interactors;


import android.content.Context;

import com.charlesmoncada.madridguide.manager.db.ActivityDAO;
import com.charlesmoncada.madridguide.manager.db.ShopDAO;
import com.charlesmoncada.madridguide.util.MainThread;

public class DeleteAllDataInteractor {

    public interface DeleteAllDataInteractorResponse {
        public void response(boolean success);
    }

    public void execute(final Context context, final DeleteAllDataInteractor.DeleteAllDataInteractorResponse response) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                boolean success = true;
                ShopDAO dao = new ShopDAO(context);
                ActivityDAO activityDAO = new ActivityDAO(context);
                success = activityDAO.deleteAll() > 0 && dao.deleteAll() > 0;

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
