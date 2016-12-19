package com.charlesmoncada.madridguide.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.charlesmoncada.madridguide.R;
import com.charlesmoncada.madridguide.navigator.Navigator;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.activity_main_shops_button)
    Button shopsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setupShopsButton();
    }

    private void setupShopsButton() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigator.navigateFromMainActivityToShopsActivity(MainActivity.this);
            }
        };
        shopsButton.setOnClickListener(listener);
    }
}
