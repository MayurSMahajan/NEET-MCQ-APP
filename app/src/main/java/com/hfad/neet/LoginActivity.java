package com.hfad.neet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
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

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    String email, password;
    EditText emailField, passwordField;
    Button loginBtn,redirectBtn;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        emailField = findViewById(R.id.email_login_field);
        passwordField = findViewById(R.id.pass_login_field);

        loginBtn = findViewById(R.id.sign_in_btn);
        redirectBtn = findViewById(R.id.redirect_sign_up);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performLogin();
            }
        });
        redirectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectToSignUp();
            }
        });

    }



    private void performLogin(){
        email = emailField.getText().toString();
        password = passwordField.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    private void redirectToSignUp(){
        startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
    }

    private void updateUI(FirebaseUser user){
        if (user!=null){
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
        }
    }

    public void resetPassword(View view){
        email = emailField.getText().toString();
        if(email != null) {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            String emailAddress = email;

            auth.sendPasswordResetEmail(emailAddress).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        emailSentAlert(true);
                    }
                    else{
                        emailSentAlert(false);
                    }
                }
            });

        }
        else{
            Toast.makeText(this, "Please enter your registered email address!", Toast.LENGTH_SHORT).show();
        }
    }

    private void emailSentAlert(boolean status){
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setCancelable(true);
        String msg,title;
        if(status){
            msg = "We have sent you a Password Reset Link on your Email. Check that out!";
            title = "Password Reset Link Sent";
        }
        else{
            msg = "We couldn't find you, Please Sign Up.";
            title = "Sorry!";
        }
        builder.setMessage(msg);
        builder.setTitle(title);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}