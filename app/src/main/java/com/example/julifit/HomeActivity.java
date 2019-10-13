package com.example.julifit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    private Button excerciseButton;
    private Button trainingButton;
    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        logoutButton = (Button) findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });

        excerciseButton = (Button) findViewById(R.id.excerciseButton);
        excerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openExcercise();
            }
        });

        trainingButton = (Button) findViewById(R.id.trainingButton);
        trainingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTraining();
            }
        });

    }

    private void openTraining() {
        Toast.makeText(getApplicationContext(),"Izvēlies treniņu . . .",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,TrainingActivity.class);
        startActivity(intent);
    }

    private void openExcercise() {
        Toast.makeText(getApplicationContext(),"Izvēlies vingrojumu . . .",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,ExcerciseActivity.class);
        startActivity(intent);
    }



    private void openMainActivity() {
        Toast.makeText(getApplicationContext(),"Iziešana . . .",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
