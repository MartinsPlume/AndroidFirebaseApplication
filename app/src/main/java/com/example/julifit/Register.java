package com.example.julifit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    DatabaseHelper db;
    EditText userId,password,passwordAgain;
    private Button loginButton;
    private Button backButton;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_register);

            db = new DatabaseHelper(this);

            userId=(EditText) findViewById(R.id.userIdLogin);
            password=(EditText) findViewById(R.id.userIdPassword);
            passwordAgain = (EditText) findViewById(R.id.userIdPassword2);

            backButton = (Button) findViewById(R.id.backButton);
            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(),"Atpakaļ ...",Toast.LENGTH_SHORT).show();
                    openLogin();
                }
            });

            loginButton=(Button) findViewById(R.id.loginButton);
            loginButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    String id = userId.getText().toString();
                    String pass = password.getText().toString();
                    String passAgain = passwordAgain.getText().toString();
                    if(userId.equals("")|| password.equals("") || passwordAgain.equals("")){
                        Toast.makeText(getApplicationContext(), "Ievadiet lietotāja id un paroli", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if (pass.equals(passAgain)) {
                            Boolean checkUser = db.checkuser(id);
                            if (checkUser == true) {
                                Boolean insert = db.insert(id, pass);
                                if (insert == true) {
                                    Toast.makeText(getApplicationContext(), "Reģistrācija veiksmīga!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Reģistrācija neveiksmīga", Toast.LENGTH_SHORT).show();

                                }
                            }
                        }
                    }
                }
            });

        }

    private void openLogin() {
        Intent intent = new Intent (this, MainActivity.class);
        startActivity(intent);
    }
}
