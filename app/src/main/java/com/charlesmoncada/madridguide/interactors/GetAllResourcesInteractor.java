package com.charlesmoncada.madridguide.interactors;


import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

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
                            Log.v("GUIDE", "GUARDE EN DISCO LOS SHOPS: "+ sucess);
                            ProgressDialogUtils.updateProgressDialog(dialog,15);
                            new CacheAllImagesForShopsInteractor().execute(context, new CacheAllImagesForShopsInteractor.CacheAllImagesInteractorResponse() {
                                @Override
                                public void response(boolean sucess) {

                                }

                                @Override
                                public void totalResponse(int sucessResponse, int totalImages) {
                                    if (sucessResponse == totalImages) {
                                        ProgressDialogUtils.updateProgressDialog(dialog,20);
                                        Log.v("CACHE", "TERMINE");
                                        response.shopResponse(true);
                                    } else {
                                        Log.v("CACHE", "voy descargando "+ sucessResponse + " de "+ totalImages);
                                    }
                                }


                            });
                            //response.shopResponse(true);
                        }
                    });
                } else {
                    Log.v("GUIDE", "Ya lo habia guardado, no lo guardo de nuevo");
                    ProgressDialogUtils.updateProgressDialog(dialog,50);
                    response.shopResponse(true);
                }

            }
        });

        new GetAllActivitiesInteractor().execute(context, new GetAllActivitiesInteractor.GetAllActivitiesInteractorResponse() {
            @Override
            public void response(MadridActivities activities) {
                if (activities != null) {
                    new CacheAllActivitiesInteractor().execute(context, activities, new CacheAllActivitiesInteractor.CacheAllActivitiesInteractorResponse() {
                                @Override
                                public void response(boolean sucess) {
                                    Log.v("GUIDE", "GUARDE EN DISCO LAS ACTIVITIES: "+ sucess);
                                    response.activityResponse(true);
                                }
                            }
                    );
                } else {
                    Log.v("GUIDE", "Ya lo habia guardado, no lo guardo de nuevo");
                    response.activityResponse(true);
                }

            }
        });


    }

}
