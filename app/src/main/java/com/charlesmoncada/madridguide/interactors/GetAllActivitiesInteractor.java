package com.charlesmoncada.madridguide.interactors;


import android.content.Context;

import com.charlesmoncada.madridguide.manager.net.ActivityEntity;
import com.charlesmoncada.madridguide.manager.net.NetworkManager;
import com.charlesmoncada.madridguide.model.MadridActivities;
import com.charlesmoncada.madridguide.model.MadridActivity;
import com.charlesmoncada.madridguide.model.mappers.ActivityEntityMadridActivityMapper;

import java.util.List;

public class GetAllActivitiesInteractor {

    public interface GetAllActivitiesInteractorResponse {
        public void response(MadridActivities activities);
    }

    public void execute(final Context context, final GetAllActivitiesInteractorResponse response) {
        NetworkManager networkManager = new NetworkManager(context);
        networkManager.getActivitiesFromServer(new NetworkManager.GetActivitiesListener() {


            @Override
            public void getActivityEntitiesSuccess(List<ActivityEntity> result) {
                List<MadridActivity> activities = new ActivityEntityMadridActivityMapper().map(result);
                if (response != null) {
                    response.response(MadridActivities.build(activities));
                }
            }

            @Override
            public void getActivityEntitiesDidFail() {
                if (response != null) {
                    response.response(null);
                }
            }

        });
    }

}
