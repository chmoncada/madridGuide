package com.charlesmoncada.madridguide.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.charlesmoncada.madridguide.MadridGuideApp;
import com.charlesmoncada.madridguide.R;
import com.charlesmoncada.madridguide.model.Shop;
import com.charlesmoncada.madridguide.util.Constants;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShopDetailActivity extends AppCompatActivity {

    private Shop shop;

    @BindView(R.id.activity_shop_detail_shop_logo_image)
    ImageView shopLogoImage;

    @BindView(R.id.activity_shop_detail_shop_image)
    ImageView shopImage;

    @BindView(R.id.activity_shop_detail_shop_name_text)
    TextView shopNameText;

    @BindView(R.id.activity_shop_detail_shop_description)
    TextView shopDescriptionText;

    @BindView(R.id.activity_shop_detail_shop_address)
    TextView shopAddressText;

    @BindView(R.id.activity_shop_detail_shop_map)
    ImageView shopMapImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail);

        ButterKnife.bind(this);

        getDetailShopFromCallingIntent();

        updateUI();

    }

    private void getDetailShopFromCallingIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            shop = (Shop) intent.getSerializableExtra(Constants.INTENT_KEY_DETAILS_SHOP);
        }
    }

    private void updateUI() {

        shopNameText.setText(shop.getName());
        shopAddressText.setText(shop.getAddress());

        if (MadridGuideApp.defSystemLanguage.equals("es")) {
            shopDescriptionText.setText(shop.getDescriptionEs());
        } else {
            shopDescriptionText.setText(shop.getDescriptionEn());
        }

        showImagesOfDetailView();

    }

    private void showImagesOfDetailView() {

        Picasso.with(this)
                .load(shop.getLogoImgUrl())
                .into(shopLogoImage);
        Picasso.with(this)
                .load(shop.getImageUrl())
                .into(shopImage);

        // Map image
        float shopLatitude = shop.getLatitude();
        float shopLongitude = shop.getLongitude();
        String mapURL = "https://maps.googleapis.com/maps/api/staticmap?center="+shopLatitude+","+shopLongitude+"&zoom=17&size=320x220&scale=2&markers=%7Ccolor:0x9C7B14%7C"+shopLatitude+","+shopLongitude+"&key=AIzaSyAO7srQaleldg1ZDuViY-IHmuY_QHaUp_A";
        Picasso.with(this)
                .load(mapURL)
                .into(shopMapImage);
    }
}
