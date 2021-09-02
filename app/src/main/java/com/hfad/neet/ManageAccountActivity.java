package com.hfad.neet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class ManageAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_account);
    }

    public void goToSubscribeAct(View view){
        startActivity(new Intent(ManageAccountActivity.this, SubscribeActivity.class));
    }

    public void shareApp(View view){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,"THIS IS THE LINK TO THE WEBSITE FROM WHERE YOU CAN DOWNLOAD THE APP:");
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent,null);
        startActivity(shareIntent);
    }

    public void logOutNow(View view){
        FirebaseAuth.getInstance().signOut();
    }

    public void deleteAccount(View view){
        Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).delete();
    }
}