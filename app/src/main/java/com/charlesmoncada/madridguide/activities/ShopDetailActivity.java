package com.charlesmoncada.madridguide.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.charlesmoncada.madridguide.R;
import com.charlesmoncada.madridguide.model.Shop;
import com.charlesmoncada.madridguide.util.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShopDetailActivity extends AppCompatActivity {

    private Shop shop;

    @BindView(R.id.activity_shop_detail_logo_image)
    ImageView logoImage;

    @BindView(R.id.activity_shop_detail_shop_name_text)
    TextView nameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        shop = (Shop) intent.getSerializableExtra(Constants.INTENT_KEY_DETAILS_SHOP);


    }
}
