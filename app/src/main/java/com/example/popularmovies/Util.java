package com.example.popularmovies;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.support.v4.app.ActivityCompat;
import android.util.DisplayMetrics;

public class Util {
    public static boolean isConnectedToInternet(Context mContext) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null &&
                connectivityManager.getActiveNetworkInfo().isConnected();
    }

    public static int getScreenWidth(Activity mContext) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        mContext.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//        int height = displayMetrics.heightPixels;
        return displayMetrics.widthPixels;
    }
}
