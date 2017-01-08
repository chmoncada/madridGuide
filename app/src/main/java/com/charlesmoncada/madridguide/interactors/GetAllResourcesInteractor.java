package com.charlesmoncada.madridguide.interactors;


import android.app.ProgressDialog;
import android.content.Context;

import com.charlesmoncada.madridguide.model.MadridActivities;
import com.charlesmoncada.madridguide.model.Shops;
import com.charlesmoncada.madridguide.util.ProgressDialogUtils;

public class GetAllResourcesInteractor {

    public interface GetAllResourcesInteractorResponse {
        public void shopResponse(boolean success);
        public void activityResponse(boolean success);
    }

    public void execute(final Context context, final ProgressDialog dialog, final GetAllResourcesInteractor.GetAllResourcesInteractorResponse response) {

        new GetAllShopsInteractor().execute(context, new GetAllShopsInteractor.GetAllShopsInteractorResponse() {
            @Override
            public void response(Shops shops) {
                if (shops != null) {
                    ProgressDialogUtils.updateProgressDialog(dialog,15);
                    new CacheAllShopsInteractor().execute(context, shops, new CacheAllShopsInteractor.CacheAllShopsInteractorResponse() {
                        @Override
                        public void response(boolean sucess) {
                            ProgressDialogUtils.updateProgressDialog(dialog,15);
                            new CacheAllImagesForShopsInteractor().execute(context, new CacheAllImagesForShopsInteractor.CacheAllImagesForShopsInteractorResponse() {
                                @Override
                                public void totalResponse(int sucessResponse, int totalImages) {
                                    if (sucessResponse == totalImages) {
                                        ProgressDialogUtils.updateProgressDialog(dialog,20);
                                        response.shopResponse(true);
                                    }
                                }
                            });
                        }
                    });
                } else {
                    ProgressDialogUtils.updateProgressDialog(dialog,50);
                    response.shopResponse(false);
                }

            }
        });

        new GetAllActivitiesInteractor().execute(context, new GetAllActivitiesInteractor.GetAllActivitiesInteractorResponse() {
            @Override
            public void response(MadridActivities activities) {
                if (activities != null) {
                    ProgressDialogUtils.updateProgressDialog(dialog,15);
                    new CacheAllActivitiesInteractor().execute(context, activities, new CacheAllActivitiesInteractor.CacheAllActivitiesInteractorResponse() {
                        @Override
                        public void response(boolean sucess) {
                            ProgressDialogUtils.updateProgressDialog(dialog,15);
                            new CacheAllImagesForActivitiesInteractor().execute(context, new CacheAllImagesForActivitiesInteractor.CacheAllImagesForActivitiesInteractorResponse() {
                                @Override
                                public void totalResponse(int sucessResponse, int totalImages) {
                                    if (sucessResponse == totalImages) {
                                        ProgressDialogUtils.updateProgressDialog(dialog,20);
                                        response.activityResponse(true);
                                    }
                                }
                            });
                        }
                    });
                } else {
                    ProgressDialogUtils.updateProgressDialog(dialog,50);
                    response.activityResponse(false);
                }

            }
        });


    }

}
