package com.hfad.neet;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddQuestions extends AppCompatActivity {

    DatabaseReference myRef;
    String chapterNum;
    long count;
    EditText questionF, aF, bF, cF, dF, answerF ,explanationF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_questions);

        Intent intent = getIntent();
        chapterNum = intent.getStringExtra("ChapterNum");
        count = intent.getLongExtra("Count",0);
        Toast.makeText(this, "Count passed into AddQuestions is" + count, Toast.LENGTH_SHORT).show();
        count-- ;

        questionF = findViewById(R.id.editTextQuestion);
        aF = findViewById(R.id.editTextA);
        bF = findViewById(R.id.editTextB);
        cF = findViewById(R.id.editTextC);
        dF = findViewById(R.id.editTextD);
        answerF = findViewById(R.id.editTextAns);
        explanationF = findViewById(R.id.editTextExplanation);

    }

    public void submitQuestion(View view){

        String question = questionF.getText().toString();
        String a = aF.getText().toString();
        String b = bF.getText().toString();
        String c = cF.getText().toString();
        String d = dF.getText().toString();
        String answer = answerF.getText().toString();
        String explanation = explanationF.getText().toString();


        if (!(question.isEmpty() || a.isEmpty() || b.isEmpty() || c.isEmpty() || d.isEmpty() || answer.isEmpty() || explanation.isEmpty() ) ){

            myRef = FirebaseDatabase.getInstance().getReference().child("chapters").child(chapterNum);
            count++;
            String qNo = "q" + count;
            Toast.makeText(this, "New question number added :" + qNo, Toast.LENGTH_SHORT).show();

            myRef.child(qNo).child("optionA").setValue(a);
            myRef.child(qNo).child("optionB").setValue(b);
            myRef.child(qNo).child("optionC").setValue(c);
            myRef.child(qNo).child("optionD").setValue(d);
            myRef.child(qNo).child("qstatement").setValue(question);
            myRef.child(qNo).child("qans").setValue(answer);
            myRef.child(qNo).child("qexplanation").setValue(explanation);

            clearAllFields();

        }
        else{
            Toast.makeText(this, "Fill all the fields!", Toast.LENGTH_SHORT).show();
        }

    }

    private void clearAllFields(){
        questionF.setText("");
        aF.setText("");
        bF.setText("");
        cF.setText("");
        dF.setText("");
        answerF.setText("");
        explanationF.setText("");
    }
}