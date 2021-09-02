package com.hfad.neet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.lang.String;

public class McqActivity extends AppCompatActivity {

    String chapterRound;
    String qArrString,chapterNum,uid;
    int roundValue;
    String[] chapterRoundArr;
//    String[] qArr;

    TextView selectedQ,selectedC;
    Button goBtn;

    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user;




    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcq);

        Intent intent = getIntent();
        chapterRound = intent.getStringExtra("chapterRound");
        assert chapterRound != null;
        chapterRoundArr = chapterRound.split("_");
        chapterNum = chapterRoundArr[0];
        roundValue = Integer.parseInt(chapterRoundArr[1]);

        goBtn = findViewById(R.id.go_button);
        selectedQ = findViewById(R.id.selectedQTv);
        selectedC = findViewById(R.id.selectChapterTv);

        selectedQ.setText(selectedQ.getText() + String.valueOf(roundValue) );
        selectedC.setText(selectedC.getText() + String.valueOf(chapterNum));

        user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        uid = user.getUid();

        readQArr();
        goBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToFetchQ();
            }
        });

//        fetchQuestions();


    }

    private void readQArr(){
        myRef = myRef.child("users").child(uid).child(chapterNum);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                qArrString = snapshot.getValue(String.class);
                Toast.makeText(McqActivity.this, "Start Now!", Toast.LENGTH_SHORT).show();
//                qArr = qArrString.split("_");
//                Toast.makeText(McqActivity.this, "QArr " + qArr[0] , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(McqActivity.this, "Please Try Again Later!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void goToFetchQ() {

        if (qArrString!=null) {
            Intent intent1 = new Intent(McqActivity.this, FetchQActivity.class);
            intent1.putExtra("qArrString", qArrString);
            intent1.putExtra("chapter",chapterNum);
            intent1.putExtra("rounds",roundValue);
            startActivity(intent1);
        }
        else{
            Toast.makeText(this, "Please Wait...!", Toast.LENGTH_SHORT).show();
        }
    }



}