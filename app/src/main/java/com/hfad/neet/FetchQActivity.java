package com.hfad.neet;

//This activity is responsible for the actual question presentation.

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.Vector;

public class FetchQActivity extends AppCompatActivity {


    String qArrString;
    String chapter;
    String[] qArr;

    String correctAnsArr = "", incorrectAnsArr = "";
    int rounds;
    public int questionIndex = 0;

    DatabaseReference myRef;


    Button aBtn, bBtn, cBtn, dBtn;
    Button nextBtn;
    TextView qField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_q);


        Intent intent = getIntent();
        qArrString = intent.getStringExtra("qArrString");
        chapter = intent.getStringExtra("chapter");
        rounds = intent.getIntExtra("rounds",0);

        aBtn = findViewById(R.id.optionABtn);
        bBtn = findViewById(R.id.optionBBtn);
        cBtn = findViewById(R.id.optionCBtn);
        dBtn = findViewById(R.id.optionDBtn);
        nextBtn = findViewById(R.id.nextQBtn);

        qField = findViewById(R.id.question_field);


        qArr = qArrString.split("_");

        readCurrentQuestion(qArr[questionIndex]);
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    public void displayQuestions(String statement, String ans, final String explanation, String optionA, String optionB, String optionC, String optionD, final String qNo){
        //This in future will be more complex function that looks for the current index of question and then prints the corresponding question.

            qField.setText(statement);
            aBtn.setText(optionA);
            bBtn.setText(optionB);
            cBtn.setText(optionC);
            dBtn.setText(optionD);


            final String answer = ans;

            aBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handleClick(aBtn, answer, qNo);
                }
            });

            bBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleClick(bBtn, answer, qNo);
            }
        });

            cBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleClick(cBtn, answer, qNo);
            }
        });

            dBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleClick(dBtn, answer, qNo);
            }
        });


            nextBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (questionIndex < rounds - 1)
                    {
                        aBtn.setBackground(getDrawable(R.drawable.option_button));
                        bBtn.setBackground(getDrawable(R.drawable.option_button));
                        cBtn.setBackground(getDrawable(R.drawable.option_button));
                        dBtn.setBackground(getDrawable(R.drawable.option_button));

                        aBtn.setClickable(true);
                        bBtn.setClickable(true);
                        cBtn.setClickable(true);
                        dBtn.setClickable(true);

                        ScrollView cardView = findViewById(R.id.card_view);
                        cardView.setVisibility(View.VISIBLE);
                        readCurrentQuestion(qArr[++questionIndex]);
                    }
                    else {
                        Toast.makeText(FetchQActivity.this, "Play another round!", Toast.LENGTH_SHORT).show();
                        submitQArr();
                    }
                }
            });

    }

    private void submitQArr(){

        Vector<String> restOfQs = new Vector<>(Arrays.asList(qArr));
        if (rounds > 0) {
            restOfQs.subList(0, rounds).clear();
        }
        String[] cArray = correctAnsArr.split("_");
        String[] icArray = incorrectAnsArr.split("_");

        Vector<String> cVector = new Vector<>(Arrays.asList(cArray));
        Vector<String> icVector = new Vector<>(Arrays.asList(icArray));

        StringBuilder newQArrString = new StringBuilder();
        for (int j=0; j<rounds ; j++){
            String element = restOfQs.get(j);
            newQArrString.append(element).append("_");
        }
        for (String element : icVector){
            newQArrString.append(element).append("_");
        }
        if (rounds > 0) {
            restOfQs.subList(0, rounds).clear();
        }
        for (String element : restOfQs){
            newQArrString.append(element).append("_");
        }
        for (String element : cVector){
            newQArrString.append(element).append("_");
        }



        qArrString = newQArrString.toString();
        int noOfCorrectAns = cArray.length;
        Intent intent = new Intent(FetchQActivity.this, ShowScoreActivity.class);
        intent.putExtra("qString",qArrString);
        intent.putExtra("noOfCorrectAns",noOfCorrectAns);
        intent.putExtra("Rounds",rounds);
        intent.putExtra("Chapter",chapter);
        startActivity(intent);

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void handleClick(Button btn, String answer, String qNo){

        aBtn.setClickable(false);
        bBtn.setClickable(false);
        cBtn.setClickable(false);
        dBtn.setClickable(false);

        String text = btn.getText().toString();
        if (text.equals(answer)){
            btn.setBackground(getDrawable(R.drawable.correct_ans_btn));
            updateQSeq(true, qNo);
        }
        else{
            btn.setBackground(getDrawable(R.drawable.wrong_ans_btn));
            updateQSeq(false,qNo);
            findCorrectBtn(answer);
        }

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void findCorrectBtn(String answer){
        if(aBtn.getText().equals(answer)){
            aBtn.setBackground(getDrawable(R.drawable.correct_ans_btn));
        }
        else if(bBtn.getText().equals(answer)){
            bBtn.setBackground(getDrawable(R.drawable.correct_ans_btn));
        }
        else if(cBtn.getText().equals(answer)){
            cBtn.setBackground(getDrawable(R.drawable.correct_ans_btn));
        }
        else{
            dBtn.setBackground(getDrawable(R.drawable.correct_ans_btn));
        }
    }

    private void updateQSeq(boolean isCorrect, String qNo){
        if(isCorrect){
            correctAnsArr += qNo + "_";
        }
        else{
            incorrectAnsArr += qNo + "_";
        }
    }

    private void readCurrentQuestion(final String qNo){

        myRef = FirebaseDatabase.getInstance().getReference().child("chapters").child(chapter).child(qNo);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String statement = snapshot.child("qstatement").getValue(String.class);
                String ans = snapshot.child("qans").getValue(String.class);
                String explanation = snapshot.child("qexplanation").getValue(String.class);
                String oA = snapshot.child("optionA").getValue(String.class);
                String oB = snapshot.child("optionB").getValue(String.class);
                String oC = snapshot.child("optionC").getValue(String.class);
                String oD = snapshot.child("optionD").getValue(String.class);

                displayQuestions(statement,ans,explanation,oA,oB,oC,oD,qNo);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(FetchQActivity.this, "Database Error!", Toast.LENGTH_SHORT).show();
            }

        });


    }




}