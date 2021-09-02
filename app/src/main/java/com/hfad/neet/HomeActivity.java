package com.hfad.neet;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import  android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {

    FirebaseUser user;
    FirebaseAuth mAuth;
    RelativeLayout sRound, mRound, lRound, xlRound;
    String sRoundVal, mRoundVal, lRoundVal, xlRoundVal;

    private BroadcastReceiver MyReceiver = null;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        MyReceiver = new MyReceiver();
        broadcastIntent();

        sRound = findViewById(R.id.s_round);
        mRound = findViewById(R.id.m_round);
        lRound = findViewById(R.id.l_round);
        xlRound = findViewById(R.id.xl_round);

        sRoundVal = "5";
        mRoundVal = "10";
        lRoundVal = "15";
        xlRoundVal = "25";


        sRound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSelectChapter(sRoundVal);
            }
        });
        xlRound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSelectChapter(xlRoundVal);
            }
        });
        lRound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSelectChapter(lRoundVal);
            }
        });
        mRound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSelectChapter(mRoundVal);
            }
        });


        mAuth = FirebaseAuth.getInstance();
        checkUserID();
    }

    private void goToSelectChapter(String roundVal){
        Intent intent = new Intent(HomeActivity.this, SelectChapter.class);
        intent.putExtra("RoundVal",roundVal);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        builder.setMessage("Do you want to exit?");
        builder.setTitle("Alert !");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent exitIntent = new Intent(Intent.ACTION_MAIN);
                exitIntent.addCategory(Intent.CATEGORY_HOME);
                exitIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(exitIntent);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void checkUserID(){
        user = mAuth.getCurrentUser();
        if (user!=null){
            Toast.makeText(this, "User Found!!"+ user.getUid() , Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(this, "User not Found!", Toast.LENGTH_SHORT).show();
    }

    public void broadcastIntent(){
        registerReceiver(MyReceiver,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onPause(){
        super.onPause();
        unregisterReceiver(MyReceiver);
    }

    public void goToManageAccount(View view){
        startActivity(new Intent(HomeActivity.this, ManageAccountActivity.class));
    }


    public void goToSubscribe(View view){
        startActivity(new Intent(HomeActivity.this, SubscribeActivity.class));
    }
}