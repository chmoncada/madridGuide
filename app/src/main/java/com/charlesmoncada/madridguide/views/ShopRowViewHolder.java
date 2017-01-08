package com.charlesmoncada.madridguide.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.charlesmoncada.madridguide.R;
import com.charlesmoncada.madridguide.model.Shop;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;


public class ShopRowViewHolder extends RecyclerView.ViewHolder {
    private TextView nameTextView;
    private ImageView logoImageView;
    private ImageView shopImageView;
    private WeakReference<Context> context;

    public ShopRowViewHolder(View rowShop) {
        super(rowShop);

        context = new WeakReference<Context>(rowShop.getContext());
        nameTextView = (TextView) rowShop.findViewById(R.id.row_shop_name);
        logoImageView = (ImageView) rowShop.findViewById(R.id.row_shop_logo);
        shopImageView = (ImageView) rowShop.findViewById(R.id.row_shop_img);
    }

    public void setShop(final @NonNull Shop shop) {
        if (shop == null) {
            return;
        }

        String textToShow = shop.getName().toUpperCase();
        nameTextView.setText(textToShow);

        Picasso.with(context.get())
                .load(shop.getLogoImgUrl())
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(logoImageView, new Callback() {
                    @Override
                    public void onSuccess() { }

                    @Override
                    public void onError() {
                        //Try again online if cache failed
                        Picasso.with(context.get())
                                .load(shop.getLogoImgUrl())
                                .into(logoImageView, new Callback() {
                                    @Override
                                    public void onSuccess() {}

                                    @Override
                                    public void onError() { Log.v("Picasso","Could not fetch image"); }
                                });
                    }
                });

        Picasso.with(context.get())
                .load(shop.getImageUrl())
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(shopImageView, new Callback() {
                    @Override
                    public void onSuccess() { }

                    @Override
                    public void onError() {
                        //Try again online if cache failed
                        Picasso.with(context.get())
                                .load(shop.getImageUrl())
                                .into(shopImageView, new Callback() {
                                    @Override
                                    public void onSuccess() { }

                                    @Override
                                    public void onError() { Log.v("Picasso","Could not fetch image"); }
                                });
                    }
                });

    }

}
