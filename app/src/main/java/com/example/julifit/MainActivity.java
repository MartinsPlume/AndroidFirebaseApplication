package com.example.julifit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    DatabaseHelper db;
    EditText userId,password;
    private Button loginButton;
    private Button registrationButton;
    private CheckBox rememberMeCheckBox;

    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);

        userId=(EditText) findViewById(R.id.userIdLogin);
        password=(EditText) findViewById(R.id.userIdPassword);
        loadPreferences();


        rememberMeCheckBox = (CheckBox) findViewById(R.id.rememberMeCheckbox); // neaizmirsti pievienot login aktivitāti shared preferences
        registrationButton = (Button) findViewById(R.id.registrationButton);
        registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Toast.makeText(getApplicationContext(),"Reģistrācija ...",Toast.LENGTH_SHORT).show();
                    openRegistration();
            }
        });
        loginButton=(Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = userId.getText().toString();
                String pass = password.getText().toString();
                Boolean userpass = db.userpass(id,pass);
                if(userpass==true){
                    if (rememberMeCheckBox.isChecked()==true){
                        saveUserPass();
                    }
                    Toast.makeText(getApplicationContext(),"Pierakstīšanās veiksmīga",Toast.LENGTH_SHORT).show();
                    openHome();
                }else{
                    Toast.makeText(getApplicationContext(),"Pierakstīšanās neveiksmīga",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void loadPreferences() {
        try{
            SharedPreferences sharedPreferences = getSharedPreferences("userInfo",MODE_PRIVATE);
            String savedUser = sharedPreferences.getString("userId","");
            String savedPassword = sharedPreferences.getString("pass","");
            userId.setText(savedUser);
            password.setText(savedPassword);
            Toast.makeText(this, "Lietotājs ielādēts...", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
        }
    }

    private void saveUserPass() {
        sharedPreferences = getSharedPreferences("userInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userId", userId.getText().toString());
        editor.putString("pass", password.getText().toString());
        editor.apply();
        Toast.makeText(this,"Lietotājs saglabāts...",Toast.LENGTH_SHORT).show();
    }

    private void openRegistration() {
        Intent intent = new Intent (this, Register.class);
        startActivity(intent);
    };

    private void openHome(){
        Intent intent = new Intent (this,HomeActivity.class);
        startActivity(intent);
    }

}
