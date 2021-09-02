package com.hfad.neet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class SelectChapter extends AppCompatActivity {

    String roundValue;
    Button c1,c2,c3,c4,c5,c6,c7,c8,c9,c10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_chapter);

        Intent intent = getIntent();
        roundValue = intent.getStringExtra("RoundVal");

        c1 = findViewById(R.id.chapter_btn_1);
        c2 = findViewById(R.id.chapter_btn_2);
        c3 = findViewById(R.id.chapter_btn_3);
        c4 = findViewById(R.id.chapter_btn_4);
        c5 = findViewById(R.id.chapter_btn_5);
        c6 = findViewById(R.id.chapter_btn_6);
        c7 = findViewById(R.id.chapter_btn_7);
        c8 = findViewById(R.id.chapter_btn_8);
        c9 = findViewById(R.id.chapter_btn_9);
        c10 = findViewById(R.id.chapter_btn_10);

        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMCQActivity("c1");
            }
        });
        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMCQActivity("c2");
            }
        });
        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMCQActivity("c3");
            }
        });
        c4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMCQActivity("c4");
            }
        });
        c5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMCQActivity("c5");
            }
        });
        c6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMCQActivity("c6");
            }
        });
        c7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMCQActivity("c7");
            }
        });
        c8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMCQActivity("c8");
            }
        });
        c9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMCQActivity("c9");
            }
        });
        c10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMCQActivity("c10");
            }
        });


    }

    private void goToMCQActivity(String chapterNum){
        String chapter_round = chapterNum + '_' + roundValue;
        Intent newIntent = new Intent(SelectChapter.this, McqActivity.class);
        newIntent.putExtra("chapterRound",chapter_round);
        startActivity(newIntent);
    }

}