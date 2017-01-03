package com.charlesmoncada.madridguide.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.charlesmoncada.madridguide.R;
import com.charlesmoncada.madridguide.interactors.CacheAllActivitiesInteractor;
import com.charlesmoncada.madridguide.interactors.CacheAllShopsInteractor;
import com.charlesmoncada.madridguide.interactors.GetAllActivitiesInteractor;
import com.charlesmoncada.madridguide.interactors.GetAllShopsInteractor;
import com.charlesmoncada.madridguide.model.MadridActivities;
import com.charlesmoncada.madridguide.model.Shops;
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

        //Check if there is a Internet connection
        if (!isInternetAvailable()) {
            Log.v("PRUEBA", "no tenemos internet");

            showNoInternetConnectionAlert();

        } else {
            Log.v("PRUEBA", "si tenemos internet");

            // Conseguimos todos los shops
            LoadAllResources(); // Posteriormente hacer un interactor de esto

        }
        
        ButterKnife.bind(this);

        setupButtons();

    }

    private void LoadAllResources() {

        new GetAllShopsInteractor().execute(getApplicationContext(),
                new GetAllShopsInteractor.GetAllShopsInteractorResponse() {
                    @Override
                    public void response(Shops shops) {

                        if (shops != null) {
                            new CacheAllShopsInteractor().execute(getApplicationContext(),
                                    shops, new CacheAllShopsInteractor.CacheAllShopsInteractorResponse() {
                                        @Override
                                        public void response(boolean sucess) {
                                            Log.v("GUIDE", "GUARDE EN DISCO LOS SHOPS: "+ sucess);
                                        }
                                    }
                            );
                        } else {
                            Log.v("GUIDE", "Ya lo habia guardado, no lo guardo de nuevo");
                        }
                    }
                }
        );

        // Conseguimos todas las activiades
        new GetAllActivitiesInteractor().execute(getApplicationContext(),
                new GetAllActivitiesInteractor.GetAllActivitiesInteractorResponse() {
                    @Override
                    public void response(MadridActivities activities) {

                        if (activities != null) {
                            new CacheAllActivitiesInteractor().execute(getApplicationContext(),
                                    activities, new CacheAllActivitiesInteractor.CacheAllActivitiesInteractorResponse() {
                                        @Override
                                        public void response(boolean sucess) {
                                            Log.v("GUIDE", "GUARDE EN DISCO LAS ACTIVITIES: "+ sucess);
                                        }
                                    }
                            );
                        } else {
                            Log.v("GUIDE", "Ya lo habia guardado, no lo guardo de nuevo");
                        }
                    }
                }
        );
    }

    private void showNoInternetConnectionAlert() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(R.string.main_activity_warning_title);
        dialog.setMessage(R.string.main_activity_warning_text);
        dialog.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                //aceptar();
            }
        });

        dialog.show();
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

    public boolean isInternetAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        return isConnected;
    }
}
