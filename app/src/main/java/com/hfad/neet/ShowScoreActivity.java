package com.hfad.neet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ShowScoreActivity extends AppCompatActivity {

    String qString,chapter;
    int totalQs;
    TextView scoreTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_score);

        Intent intent = getIntent();
        qString = intent.getStringExtra("qString");
        totalQs = intent.getIntExtra("Rounds",0);
        int noOfCorrectA = intent.getIntExtra("noOfCorrectAns",0);
        chapter = intent.getStringExtra("Chapter");

        scoreTV = findViewById(R.id.textView4);
        double percentage = noOfCorrectA * 100;
        percentage = percentage / totalQs;

        String percent = percentage + " %";
        scoreTV.setText(percent);

        uploadQSequence();
    }

    private void uploadQSequence(){
        //In this method we will upload the new question sequence to the firebase database.
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        String userID = user.getUid();
        myRef.child("users").child(userID).child(chapter).setValue(qString);
    }

    public void replayRound(View view){
        Intent intent = new Intent(ShowScoreActivity.this, SelectChapter.class);
        intent.putExtra("RoundVal",totalQs);
        startActivity(intent);
    }

    public void backToHome(View view){
        startActivity(new Intent(ShowScoreActivity.this, HomeActivity.class));
    }
}