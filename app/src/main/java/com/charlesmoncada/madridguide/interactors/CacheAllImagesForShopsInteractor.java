package com.charlesmoncada.madridguide.interactors;


import android.content.Context;

import com.charlesmoncada.madridguide.manager.db.ShopDAO;
import com.charlesmoncada.madridguide.model.Shop;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CacheAllImagesForShopsInteractor {

    public interface CacheAllImagesInteractorResponse {
        public void response(boolean sucess);
        public void totalResponse(int sucessResponse, int totalImages);
    }

    int totalResponses = 0;
    int totalImages= 0;

    public void execute(final Context context, final CacheAllImagesInteractorResponse response) {

        ShopDAO dao = new ShopDAO(context);

        List<Shop> list = dao.query();

        totalImages = dao.query().size() * 3;

        //CacheImage(context, list, list.size()-1, response);

        for (final Shop shop: dao.query()) {
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

            Picasso.with(context).load("http://maps.googleapis.com/maps/api/staticmap?center="+shop.getLatitude()+","+shop.getLongitude()+"&zoom=17&size=320x220&scale=2&markers=%7Ccolor:0x9C7B14%7C"+shop.getLatitude()+","+shop.getLongitude())
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

//        for (final Shop shop: dao.query()) {
//            Picasso.with(context).load(shop.getImageUrl()).fetch(new Callback() {
//                @Override
//                public void onSuccess() {
//                    Picasso.with(context).load(shop.getLogoImgUrl()).fetch(new Callback() {
//                        @Override
//                        public void onSuccess() {
//                            Picasso.with(context)
//                                    .load("http://maps.googleapis.com/maps/api/staticmap?center="+shop.getLatitude()+","+shop.getLongitude()+"&zoom=17&size=320x220&scale=2&markers=%7Ccolor:0x9C7B14%7C"+shop.getLatitude()+","+shop.getLongitude())
//                                    .fetch(new Callback() {
//                                        @Override
//                                        public void onSuccess() {
//                                            Log.v("Picasso", "termine de cargar las imagenes de: " + shop.getName());
//                                        }
//                                        @Override
//                                        public void onError() {}
//                                    });
//                        }
//
//                        @Override
//                        public void onError() {}
//                    });
//                }
//
//                @Override
//                public void onError() {}
//            });
//
//
//        }

        response.response(true);
    }

    private void CacheImage(final Context context, final List<Shop> list, final int i, final CacheAllImagesInteractorResponse response) {

        Picasso.with(context).load(list.get(i).getImageUrl()).fetch(new Callback() {
            @Override
            public void onSuccess() {
                Picasso.with(context).load(list.get(i).getLogoImgUrl()).fetch(new Callback() {
                    @Override
                    public void onSuccess() {
                        Picasso.with(context)
                                .load("http://maps.googleapis.com/maps/api/staticmap?center="+list.get(i).getLatitude()+","+list.get(i).getLongitude()+"&zoom=17&size=320x220&scale=2&markers=%7Ccolor:0x9C7B14%7C"+list.get(i).getLatitude()+","+list.get(i).getLongitude())
                                .fetch(new Callback() {
                                    @Override
                                    public void onSuccess() {
                                        int j = i - 1 ;
                                        if (j > -1) {
                                            CacheImage(context, list, j, response);
                                        } else {response.response(true);}
                                    }
                                    @Override
                                    public void onError() {
                                        int j = i - 1 ;
                                        if (j > -1) {
                                            CacheImage(context, list, j, response);
                                        } else {response.response(true);}
                                    }
                                });
                    }

                    @Override
                    public void onError() {
                        Picasso.with(context)
                                .load("http://maps.googleapis.com/maps/api/staticmap?center="+list.get(i).getLatitude()+","+list.get(i).getLongitude()+"&zoom=17&size=320x220&scale=2&markers=%7Ccolor:0x9C7B14%7C"+list.get(i).getLatitude()+","+list.get(i).getLongitude())
                                .fetch(new Callback() {
                                    @Override
                                    public void onSuccess() {
                                        int j = i - 1 ;
                                        if (j > -1) {
                                            CacheImage(context, list, j, response);
                                        } else {response.response(true);}
                                    }
                                    @Override
                                    public void onError() {
                                        int j = i - 1 ;
                                        if (j > -1) {
                                            CacheImage(context, list, j, response);
                                        } else {response.response(true);}
                                    }
                                });
                    }
                });
            }

            @Override
            public void onError() {
                Picasso.with(context).load(list.get(i).getLogoImgUrl()).fetch(new Callback() {
                    @Override
                    public void onSuccess() {
                        Picasso.with(context)
                                .load("http://maps.googleapis.com/maps/api/staticmap?center="+list.get(i).getLatitude()+","+list.get(i).getLongitude()+"&zoom=17&size=320x220&scale=2&markers=%7Ccolor:0x9C7B14%7C"+list.get(i).getLatitude()+","+list.get(i).getLongitude())
                                .fetch(new Callback() {
                                    @Override
                                    public void onSuccess() {
                                        int j = i - 1 ;
                                        if (j > -1) {
                                            CacheImage(context, list, j, response);
                                        } else {response.response(true);}
                                    }
                                    @Override
                                    public void onError() {
                                        int j = i - 1 ;
                                        if (j > -1) {
                                            CacheImage(context, list, j, response);
                                        } else {response.response(true);}
                                    }
                                });
                    }

                    @Override
                    public void onError() {
                        Picasso.with(context)
                                .load("http://maps.googleapis.com/maps/api/staticmap?center="+list.get(i).getLatitude()+","+list.get(i).getLongitude()+"&zoom=17&size=320x220&scale=2&markers=%7Ccolor:0x9C7B14%7C"+list.get(i).getLatitude()+","+list.get(i).getLongitude())
                                .fetch(new Callback() {
                                    @Override
                                    public void onSuccess() {
                                        int j = i - 1 ;
                                        if (j > -1) {
                                            CacheImage(context, list, j, response);
                                        } else {response.response(true);}
                                    }
                                    @Override
                                    public void onError() {
                                        int j = i - 1 ;
                                        if (j > -1) {
                                            CacheImage(context, list, j, response);
                                        } else {response.response(true);}
                                    }
                                });
                    }
                });
            }
        });
    }
}
