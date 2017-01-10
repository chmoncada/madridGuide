package com.charlesmoncada.madridguide.interactors;


import android.content.Context;

import com.charlesmoncada.madridguide.manager.db.ActivityDAO;
import com.charlesmoncada.madridguide.model.MadridActivities;
import com.charlesmoncada.madridguide.model.MadridActivity;
import com.charlesmoncada.madridguide.util.MainThread;

import java.util.List;

public class GetAllActivitiesFromLocalCacheInteractor {

    public interface GetAllActivitiesFromLocalCacheInteractorResponse {
        public void response(MadridActivities activities);
    }

    public void execute(final Context context, final GetAllActivitiesFromLocalCacheInteractorResponse response) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ActivityDAO dao = new ActivityDAO(context);

                List<MadridActivity> activityList = dao.query();
                final MadridActivities activities = MadridActivities.build(activityList);

                MainThread.run(new Runnable() {
                    @Override
                    public void run() {
                        response.response(activities);
                    }
                });
            }
        }).start();
    }
}
