package com.hfad.neet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    EditText emailF, passF, confirmF;
    Button signUpBtn;
    String emailS,passS,cPass;

    private FirebaseAuth mAuth;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        emailF = findViewById(R.id.email_field);
        passF = findViewById(R.id.pass_field);
        confirmF = findViewById(R.id.c_pass_field);
        signUpBtn = findViewById(R.id.sign_up_btn);

        mAuth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference();


        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewUser();
            }
        });

    }

    private void createNewUser(){
        if (checkPasswords()){
            performSignUp();
        }
        else{
            Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkPasswords(){
        passS = passF.getText().toString();
        cPass = confirmF.getText().toString();
        return cPass.equals(passS);
    }

    private void performSignUp(){
        emailS = emailF.getText().toString();

        mAuth.createUserWithEmailAndPassword(emailS, passS)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = mAuth.getCurrentUser();
                            assert user != null;
                            initUser(user);
                            Toast.makeText(SignUpActivity.this, "Sign Up Successful, Please Login!", Toast.LENGTH_SHORT).show();

                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }

    private void initUser(FirebaseUser firebaseUser){
        String uid = firebaseUser.getUid();
        String qString = "q1_q2_q3_q4_q5_q6_q7_q8_q9_q10_11_q12_q13_q14_q15_q16_q17_q18_q19_q20";
        myRef.child("users").child(uid).child("c1").setValue(qString);
        myRef.child("users").child(uid).child("c2").setValue(qString);
        myRef.child("users").child(uid).child("c3").setValue(qString);
        myRef.child("users").child(uid).child("c4").setValue(qString);
        myRef.child("users").child(uid).child("c5").setValue(qString);
        myRef.child("users").child(uid).child("c6").setValue(qString);
        myRef.child("users").child(uid).child("c7").setValue(qString);
        myRef.child("users").child(uid).child("c8").setValue(qString);
        myRef.child("users").child(uid).child("c9").setValue(qString);
        myRef.child("users").child(uid).child("c10").setValue(qString);
    }
}