package com.charlesmoncada.madridguide.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.charlesmoncada.madridguide.R;
import com.charlesmoncada.madridguide.ShopsFragment;

public class ShopsActivity extends AppCompatActivity {

    private ShopsFragment shopsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shops);

        shopsFragment = (ShopsFragment) getSupportFragmentManager().findFragmentById(R.id.activity_shops_fragment_shops);

    }
}
