package com.charlesmoncada.madridguide.interactors;


import android.content.Context;

import com.charlesmoncada.madridguide.manager.db.ShopDAO;
import com.charlesmoncada.madridguide.model.Shop;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class CacheAllImagesForShopsInteractor {

    public interface CacheAllImagesForShopsInteractorResponse {
        public void totalResponse(int sucessResponse, int totalImages);
    }

    int totalResponses = 0;
    int totalImages = 0;

    public void execute(final Context context, final CacheAllImagesForShopsInteractorResponse response) {

        ShopDAO dao = new ShopDAO(context);

        totalImages = dao.query().size() * 3;

        for (final Shop shop : dao.query()) {
            Picasso.with(context).load(shop.getImageUrl()).fetch(new Callback() {
                @Override
                public void onSuccess() {
                    totalResponses = totalResponses + 1;
                    response.totalResponse(totalResponses, totalImages);
                }

                @Override
                public void onError() {
                    totalResponses = totalResponses + 1;
                    response.totalResponse(totalResponses, totalImages);
                }
            });

            Picasso.with(context).load(shop.getLogoImgUrl()).fetch(new Callback() {
                @Override
                public void onSuccess() {
                    totalResponses = totalResponses + 1;
                    response.totalResponse(totalResponses, totalImages);
                }

                @Override
                public void onError() {
                    totalResponses = totalResponses + 1;
                    response.totalResponse(totalResponses, totalImages);
                }
            });

            String mapURL = "https://maps.googleapis.com/maps/api/staticmap?center=" +shop.getLatitude() + "," + shop.getLongitude() + "&zoom=17&size=320x220&scale=2&markers=%7Ccolor:0x9C7B14%7C" + shop.getLatitude() + "," + shop.getLongitude()+"&key=AIzaSyAO7srQaleldg1ZDuViY-IHmuY_QHaUp_A";

            Picasso.with(context)
                    .load(mapURL)
                    .fetch(new Callback() {
                        @Override
                        public void onSuccess() {
                            totalResponses = totalResponses + 1;
                            response.totalResponse(totalResponses, totalImages);
                        }

                        @Override
                        public void onError() {
                            totalResponses = totalResponses + 1;
                            response.totalResponse(totalResponses, totalImages);
                        }
                    });
        }

    }
}

