package com.mplu.julifit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {


    EditText userId,userName,password,passwordAgain;
    private Button loginButton;
    private Button backButton;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        userId=(EditText) findViewById(R.id.userIdLogin);
        password=(EditText) findViewById(R.id.userIdPassword);
        passwordAgain = (EditText) findViewById(R.id.userIdPassword2);

        backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),getResources().getString(R.string.back),Toast.LENGTH_SHORT).show();
                openLogin();
            }
        });

        loginButton=(Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(this);
    }

    private void registerUser() {
        String id = userId.getText().toString();
        String pass = password.getText().toString();
        String passAgain = passwordAgain.getText().toString();

        if (id.isEmpty()){
            userId.setError(getResources().getString(R.string.enterEmail));
            userId.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(id).matches()) {
            userId.setError(getResources().getString(R.string.enterValidEmail));
            userId.requestFocus();
            return;
        }

        if (pass.isEmpty()){
            password.setError(getResources().getString(R.string.enterPassword));
            password.requestFocus();
            return;
        }

        if(pass.length()<6){
            password.setError(getResources().getString(R.string.passwordShouldBe));
            password.requestFocus();
            return;
        }

        if (!pass.equals(passAgain)){
            password.setText("");
            password.requestFocus();
            passwordAgain.setText("");
            return;
        }

        mAuth.createUserWithEmailAndPassword(id,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {
                if (task.isSuccessful()){
                   Toast.makeText(getApplicationContext(), getResources().getString(R.string.userRegistered), Toast.LENGTH_SHORT).show();
                } else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.userAlreadyRegistered), Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


    private void openLogin(){
        Intent intent = new Intent (this, MainActivity.class);
        startActivity(intent);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loginButton:
                registerUser();
                break;
            case R.id.backButton:
                openLogin();
                break;
        }

    }
}
