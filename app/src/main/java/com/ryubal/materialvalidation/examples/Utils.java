package com.ryubal.materialvalidation.examples;

import android.app.Activity;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class Utils {
    public static void showAlert(Activity activity, String title, String msg) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(activity);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setPositiveButton(android.R.string.ok, null);
        builder.show();
    }
}
