package com.lifereze.tweeps.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.lifereze.tweeps.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewUserActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.emailEditText) EditText mEmailEditText;
    @BindView(R.id.passwordEditText) EditText mPasswordEditText;
    @BindView(R.id.confirmPasswordEditText) EditText mConfirmPasswordEditText;
    @BindView(R.id.newUserSubmitButton) Button mNewUserSubmitButton;
    @BindView(R.id.userLoginTextView) TextView mUserLoginTextView;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ProgressDialog mAuthProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
        mNewUserSubmitButton.setOnClickListener(this);
        mUserLoginTextView.setOnClickListener(this);
        createAuthStateListener();
        createAuthProgressDialog();
    }

    @Override
    public void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop(){
        super.onStop();
        mAuth.removeAuthStateListener(mAuthListener);
    }

    @Override
    public void onClick(View v){
        if (v==mNewUserSubmitButton){
            createNewUser();
        }
        if (v==mUserLoginTextView){
            Intent intent = new Intent(NewUserActivity.this,LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    private void createAuthProgressDialog(){
        mAuthProgressDialog = new ProgressDialog(this);
        mAuthProgressDialog.setMessage("Please wait while we authenticate you ...");
        mAuthProgressDialog.setCancelable(false);
    }

    private void createAuthStateListener(){
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user!=null){
                    Intent intent = new Intent(NewUserActivity.this,MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        };
    }

    private void createNewUser(){
        final String email = mEmailEditText.getText().toString().trim();
        final String password = mPasswordEditText.getText().toString().trim();
        String confirmPassword = mConfirmPasswordEditText.getText().toString().trim();
        boolean validEmail = isValidEmail(email);
        boolean validPassword = isValidPassword(password,confirmPassword);
        if (!validEmail||!validPassword)return;
        mAuthProgressDialog.show();
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    mAuthProgressDialog.dismiss();
                    Toast.makeText(NewUserActivity.this, "Authentication Successful", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(NewUserActivity.this, "Authentication failed! Try Again", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isValidEmail(String email){
        boolean isGoodEmail = (email!=null&& Patterns.EMAIL_ADDRESS.matcher(email).matches());

        if (!isGoodEmail){
            mEmailEditText.setError("Invalid email address");
            return false;
        }
        return isGoodEmail;
    }

    private boolean isValidPassword(String password,String confirmPassword){
        if (password.length()<8){
            mPasswordEditText.setError("The password must be at least 8 characters long");
            return false;
        }else if (!password.equals(confirmPassword)){
            mPasswordEditText.setError("Passwords do not match");
            return false;
        }
        return true;
    }
}
