package com.mplu.julifit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText userId,password;

    private Button loginButton;
    private Button registrationButton;
    private CheckBox rememberMeCheckBox;

    SharedPreferences sharedPreferences;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find buttons
        registrationButton = (Button) findViewById(R.id.registrationButton);
        registrationButton.setOnClickListener(this);
        loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(this);

        // instantiate firebase
        mAuth = FirebaseAuth.getInstance();

        // remember me setup
        rememberMeCheckBox = (CheckBox) findViewById(R.id.rememberMeCheckbox);

        // entry EditText fields
        userId=(EditText) findViewById(R.id.userIdLogin);
        password=(EditText) findViewById(R.id.userIdPassword);

        // load preferences
        loadPreferences();
    }

    // Open registration
    private void openRegistration() {
        Intent intent = new Intent (this, SignUpActivity.class);
        startActivity(intent);
    };

    // user login
    private void userLogin() {

        String pass = password.getText().toString().trim();

        if (EmailReader().isEmpty()) {
            userId.setError(getResources().getString(R.string.enterEmail));
            userId.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(EmailReader()).matches()) {
            userId.setError(getResources().getString(R.string.enterValidEmail));
            userId.requestFocus();
            return;
        }

        if (pass.isEmpty()) {
            password.setError(getResources().getString(R.string.enterPassword));
            password.requestFocus();
            return;
        }

        if (pass.length() < 6) {
            password.setError(getResources().getString(R.string.passwordShouldBe));
            password.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(EmailReader(), pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    if (rememberMeCheckBox.isChecked()==true){
                        saveUserPass();
                    }
                    DbConnection UserCheckDb = new DbConnection("users",EmailReader());
                    UserCheckDb.getDocRef().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (!document.exists()) {
                                    // initialize db
                                    DbConnection UserSaveDb = new DbConnection("users",EmailReader());
                                    User user = new User(
                                            EmailReader(),
                                            EmailReader(),
                                            getResources().getString(R.string.standardHomeText),
                                            false
                                    );
                                UserSaveDb.setDocument(user);
                                }
                            }
                        }
                    });

                    // close the current activity
                    Intent intent = new Intent(MainActivity.this, NavigationActivity.class);
                    // clear activities, so back doesn't return to login screen
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    finish();
                    startActivity(intent);

                } else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    String EmailReader(){
        return userId.getText().toString().trim();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.registrationButton:
                Toast.makeText(getApplicationContext(),getResources().getString(R.string.registerPushed),Toast.LENGTH_SHORT).show();
                openRegistration();
                break;

            case R.id.loginButton:
                Toast.makeText(getApplicationContext(),getResources().getString(R.string.signingIn),Toast.LENGTH_SHORT).show();
                userLogin();
                break;
        }
    }

    // saving preferences
    private void saveUserPass() {
        sharedPreferences = getSharedPreferences("userInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userId", userId.getText().toString());
        editor.putString("pass", password.getText().toString());
        editor.apply();
        Toast.makeText(this,getResources().getString(R.string.userSaved),Toast.LENGTH_SHORT).show();
    }

    // loading preferences
    private void loadPreferences() {
        try{
            SharedPreferences sharedPreferences = getSharedPreferences("userInfo",MODE_PRIVATE);
            String savedUser = sharedPreferences.getString("userId","");
            String savedPassword = sharedPreferences.getString("pass","");
            userId.setText(savedUser);
            password.setText(savedPassword);
            if (!savedUser.equals("") || !savedPassword.equals("")) {
                Toast.makeText(this, getResources().getString(R.string.userLoaded), Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e){
        }
    }
}