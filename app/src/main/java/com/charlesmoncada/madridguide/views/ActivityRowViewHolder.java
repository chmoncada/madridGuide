package com.charlesmoncada.madridguide.views;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.charlesmoncada.madridguide.R;
import com.charlesmoncada.madridguide.model.MadridActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;

public class ActivityRowViewHolder extends RecyclerView.ViewHolder{

    private TextView nameTextView;
    private ImageView logoImageView;
    private ImageView activityImageView;
    private WeakReference<Context> context;

    public ActivityRowViewHolder(View rowActivity) {
        super(rowActivity);

        context = new WeakReference<Context>(rowActivity.getContext());
        nameTextView = (TextView) rowActivity.findViewById(R.id.row_activity_name);
        logoImageView = (ImageView) rowActivity.findViewById(R.id.row_activity_logo);
        activityImageView = (ImageView) rowActivity.findViewById(R.id.row_activity_img);
    }

    public void setActivity(final @NonNull MadridActivity activity) {
        if (activity == null) {
            return;
        }

        String textToShow = activity.getName().toUpperCase();
        nameTextView.setText(textToShow);

        Picasso.with(context.get())
                .load(activity.getLogoImgUrl())
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(logoImageView, new Callback() {
                    @Override
                    public void onSuccess() { }

                    @Override
                    public void onError() {
                        //Try again online if cache failed
                        Picasso.with(context.get())
                                .load(activity.getLogoImgUrl())
                                .into(logoImageView, new Callback() {
                                    @Override
                                    public void onSuccess() {}

                                    @Override
                                    public void onError() { Log.v("Picasso","Could not fetch image"); }
                                });
                    }
                });

        Picasso.with(context.get())
                .load(activity.getImageUrl())
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(activityImageView, new Callback() {
                    @Override
                    public void onSuccess() { }

                    @Override
                    public void onError() {
                        //Try again online if cache failed
                        Picasso.with(context.get())
                                .load(activity.getImageUrl())
                                .into(activityImageView, new Callback() {
                                    @Override
                                    public void onSuccess() { }

                                    @Override
                                    public void onError() { Log.v("Picasso","Could not fetch image"); }
                                });
                    }
                });

    }
}
