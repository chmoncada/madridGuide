package com.charlesmoncada.madridguide.util;


import android.app.ProgressDialog;

public class ProgressDialogUtils {

    public static void updateProgressDialog(final ProgressDialog dialog, int percentage) {

        int newProgress = dialog.getProgress() + percentage;
        dialog.setProgress(newProgress);

    }
}
