package com.charlesmoncada.madridguide.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.charlesmoncada.madridguide.MadridGuideApp;
import com.charlesmoncada.madridguide.R;
import com.charlesmoncada.madridguide.model.MadridActivity;
import com.charlesmoncada.madridguide.util.Constants;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MadridActivityDetailActivity extends AppCompatActivity {

    private MadridActivity activity;

    @BindView(R.id.activity_madrid_activity_detail_activity_logo_image)
    ImageView activityLogoImage;

    @BindView(R.id.activity_madrid_activity_detail_activity_image)
    ImageView activityImage;

    @BindView(R.id.activity_madrid_activity_detail_activity_name_text)
    TextView activityNameText;

    @BindView(R.id.activity_madrid_activity_detail_activity_description)
    TextView activityDescriptionText;

    @BindView(R.id.activity_madrid_activity_detail_activity_address)
    TextView activityAddressText;

    @BindView(R.id.activity_madrid_activity_detail_activity_map)
    ImageView activityMapImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_madrid_activity_detail);

        ButterKnife.bind(this);

        getDetailShopFromCallingIntent();

        updateUI();

    }


    private void getDetailShopFromCallingIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            activity = (MadridActivity) intent.getSerializableExtra(Constants.INTENT_KEY_DETAILS_ACTIVITY);
        }
    }

    private void updateUI() {

        if (activity != null) {
            activityNameText.setText(activity.getName());
            activityAddressText.setText(activity.getAddress());

            if (MadridGuideApp.defSystemLanguage.equals("es")) {
                activityDescriptionText.setText(activity.getDescriptionEs());
            } else {
                activityDescriptionText.setText(activity.getDescriptionEn());
            }

            showImagesOfDetailView();
        }

    }

    private void showImagesOfDetailView() {

        Picasso.with(this)
                .load(activity.getLogoImgUrl())
                .into(activityLogoImage);
        Picasso.with(this)
                .load(activity.getImageUrl())
                .into(activityImage);

        // Map image
        float activityLatitude = activity.getLatitude();
        float activityLongitude = activity.getLongitude();
        String mapURL = "https://maps.googleapis.com/maps/api/staticmap?center="+activityLatitude+","+activityLongitude+"&zoom=17&size=320x220&scale=2&markers=%7Ccolor:0x9C7B14%7C"+activityLatitude+","+activityLongitude+"&key=AIzaSyAO7srQaleldg1ZDuViY-IHmuY_QHaUp_A";
        Picasso.with(this)
                .load(mapURL)
                .into(activityMapImage);
    }
}
