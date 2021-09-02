package com.hfad.neet;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

class NetworkUtil {
    public static String getConnectivityStatusString(Context context){
        String status = "No Internet is available";
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if(activeNetwork != null){
            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI){
                status = "WiFi enabled";
                return status;
            }
            else if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE){
                status = "Mobile data enabled";
                return status;
            }
            else{
                status = "No Internet is available";
                return status;
            }

        }
        return status;
    }
}
