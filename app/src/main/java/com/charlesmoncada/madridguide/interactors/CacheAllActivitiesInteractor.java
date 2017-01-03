package com.charlesmoncada.madridguide.interactors;


import android.content.Context;

import com.charlesmoncada.madridguide.manager.db.ActivityDAO;
import com.charlesmoncada.madridguide.model.MadridActivities;
import com.charlesmoncada.madridguide.model.MadridActivity;
import com.charlesmoncada.madridguide.util.MainThread;

public class CacheAllActivitiesInteractor {

    public interface CacheAllActivitiesInteractorResponse {
        public void response(boolean sucess);
    }

    public void execute(final Context context, final MadridActivities activities, final CacheAllActivitiesInteractor.CacheAllActivitiesInteractorResponse response) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ActivityDAO dao = new ActivityDAO(context);

                boolean success = true;
                for (MadridActivity activity: activities.allActivities()) {
                    success = dao.insert(activity) > 0;
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
