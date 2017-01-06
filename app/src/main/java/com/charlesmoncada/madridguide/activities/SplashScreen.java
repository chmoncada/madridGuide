package com.charlesmoncada.madridguide.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.charlesmoncada.madridguide.R;
import com.charlesmoncada.madridguide.interactors.GetAllResourcesInteractor;
import com.charlesmoncada.madridguide.navigator.Navigator;
import com.charlesmoncada.madridguide.util.NetworkUtils;
import com.charlesmoncada.madridguide.util.ProgressDialogUtils;

public class SplashScreen extends AppCompatActivity {

    public void setShopFinish(boolean shopFinish) {
        this.shopFinish = shopFinish;
    }

    boolean shopFinish = false;
    boolean activityFinish = false;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        if (!NetworkUtils.isInternetAvailable(getApplicationContext())) {
            showAlertDialog();
        } else {
            setupProgressDialog();
            getAllResources();
        }
    }

    private void showAlertDialog() {
        AlertDialog.Builder dialog = NetworkUtils.showNoInternetConnectionAlert(SplashScreen.this);
        dialog.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo, int id) {
                Navigator.navigateFromSplashScreenToMainActivity(SplashScreen.this);
            }
        });
        dialog.show();
    }

    private void getAllResources() {

        new GetAllResourcesInteractor().execute(getApplicationContext(), dialog,new GetAllResourcesInteractor.GetAllResourcesInteractorResponse() {
            @Override
            public void shopResponse(boolean success) {
                Log.v("SPLASH", "termine de cargar los shops");
                shopFinish = true;
                if (activityFinish) {
                    Log.v("SPLASH", "termine de cargar todo");
                    dialog.dismiss();
                    Navigator.navigateFromSplashScreenToMainActivity(SplashScreen.this);
                }
            }

            @Override
            public void activityResponse(boolean success) {
                Log.v("SPLASH", "termine de cargar los activities");
                ProgressDialogUtils.updateProgressDialog(dialog,50);
                activityFinish = true;
                if (shopFinish) {
                    Log.v("SPLASH", "termine de cargar todo");
                    dialog.dismiss();
                    Navigator.navigateFromSplashScreenToMainActivity(SplashScreen.this);
                }
            }
        });
    }

    private void setupProgressDialog() {
        dialog = new ProgressDialog(this);
        dialog.setMessage("Downloading:");
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setProgress(0);
        dialog.setMax(100);
        dialog.show();
    }
}
