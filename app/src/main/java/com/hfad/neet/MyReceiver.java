package com.hfad.neet;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    Dialog dialog;


    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(final Context context, Intent intent) {

        String status =  NetworkUtil.getConnectivityStatusString(context);
        dialog = new Dialog(context, android.R.style.Theme_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.no_internet_layout);
        Button restartApp = dialog.findViewById(R.id.restartApp);

        restartApp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                ((Activity) context).finish();
                Intent i = new Intent(context, HomeActivity.class);
                context.startActivity(i);
            }
        });

        if(status.isEmpty() || status.equals("No Internet is available")){
            status = "No Internet is available";
            dialog.show();
        }
        Toast.makeText(context, status, Toast.LENGTH_SHORT).show();
    }
}
