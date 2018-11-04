package com.example.popularmovies;

import android.content.Context;
import android.net.ConnectivityManager;

public class Util {
    public static boolean isConnectedToInternet(Context mContext) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null &&
                connectivityManager.getActiveNetworkInfo().isConnected();
    }
}
