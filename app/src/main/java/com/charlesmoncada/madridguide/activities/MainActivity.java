package com.charlesmoncada.madridguide.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.charlesmoncada.madridguide.R;
import com.charlesmoncada.madridguide.navigator.Navigator;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.activity_main_shops_button)
    Button shopsButton;

    @BindView(R.id.activity_main_activities_button)
    Button activitiesButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        setupButtons();

    }

    private void setupButtons() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (view.getId()) {
                    case R.id.activity_main_shops_button:
                        Navigator.navigateFromMainActivityToShopsActivity(MainActivity.this);
                        break;
                    case R.id.activity_main_activities_button:
                        Navigator.navigateFromMainActivityToActivitiesActivity(MainActivity.this);
                        break;
                }
            }
        };

        shopsButton.setOnClickListener(listener);
        activitiesButton.setOnClickListener(listener);
    }
}
