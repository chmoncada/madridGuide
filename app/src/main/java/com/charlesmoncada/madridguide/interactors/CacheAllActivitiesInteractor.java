package com.charlesmoncada.madridguide.interactors;


import android.content.Context;
import android.os.Looper;

import com.charlesmoncada.madridguide.manager.db.ActivityDAO;
import com.charlesmoncada.madridguide.model.MadridActivities;
import com.charlesmoncada.madridguide.model.MadridActivity;

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
