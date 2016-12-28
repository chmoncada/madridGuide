package com.charlesmoncada.madridguide.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

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

    @BindView(R.id.activity_shop_detail_shop_name_text)
    TextView shopNameText;

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
        Picasso.with(this)
                .load(shop.getLogoImgUrl())
                .into(shopLogoImage);

    }
}
