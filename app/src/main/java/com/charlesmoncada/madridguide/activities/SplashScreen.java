package com.charlesmoncada.madridguide.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.charlesmoncada.madridguide.R;
import com.charlesmoncada.madridguide.interactors.DeleteAllDataInteractor;
import com.charlesmoncada.madridguide.interactors.GetAllResourcesInteractor;
import com.charlesmoncada.madridguide.navigator.Navigator;
import com.charlesmoncada.madridguide.util.DateUtils;
import com.charlesmoncada.madridguide.util.NetworkUtils;

public class SplashScreen extends AppCompatActivity {

    private boolean shopFinish = false;
    private boolean activityFinish = false;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        if (!NetworkUtils.isInternetAvailable(getApplicationContext())) {
            showAlertDialog();
        } else {

            if (DateUtils.isCacheNotLongerValid(SplashScreen.this)) {

                new DeleteAllDataInteractor().execute(getApplicationContext(), new DeleteAllDataInteractor.DeleteAllDataInteractorResponse() {
                    @Override
                    public void response(boolean success) {
                        setupProgressDialog();
                        getAllResources();
                    }
                });
            } else {
                setupProgressDialog();
                getAllResources();
            }
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
                shopFinish = true;
                if (activityFinish) {
                    if (success) {DateUtils.saveDateInSharedPreferences(SplashScreen.this); }
                    dialog.dismiss();
                    Navigator.navigateFromSplashScreenToMainActivity(SplashScreen.this);
                }
            }

            @Override
            public void activityResponse(boolean success) {
                activityFinish = true;
                if (shopFinish) {
                    if (success) { DateUtils.saveDateInSharedPreferences(SplashScreen.this); }
                    dialog.dismiss();
                    Navigator.navigateFromSplashScreenToMainActivity(SplashScreen.this);
                }
            }
        });
    }

    private void setupProgressDialog() {
        dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.splash_screen_dialog_text));
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setProgress(0);
        dialog.setMax(100);
        dialog.show();
    }
}
