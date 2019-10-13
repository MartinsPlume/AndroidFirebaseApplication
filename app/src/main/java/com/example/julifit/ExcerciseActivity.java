package com.example.julifit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ExcerciseActivity extends AppCompatActivity {

    private Button legButton;
    private Button backButton;
    private Button chestButton;
    private Button shoulderButton;
    private Button armsButton;

    private Button homeButton;

    String excercise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excercise);

        homeButton = (Button) findViewById(R.id.homeButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHomeActivity();
            }
        });

        legButton = (Button) findViewById(R.id.legButton);
        legButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                excercise="leg";
                openExcercise(excercise);
            }
        });
        backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                excercise="back";
                openExcercise(excercise);
            }
        });
        chestButton = (Button) findViewById(R.id.chestButton);
        chestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                excercise="chest";
                openExcercise(excercise);
            }
        });
        shoulderButton = (Button) findViewById(R.id.shouldersButton);
        shoulderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                excercise="shoulder";
                openExcercise(excercise);
            }
        });
        armsButton = (Button) findViewById(R.id.armsButton);
        armsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                excercise="arms";
                openExcercise(excercise);
            }
        });



    }

    private void openHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    private void openExcercise(String excercise) {
        Intent intent = new Intent(this,ExcerciseList.class);
        intent.putExtra("grupa",excercise);
        startActivity(intent);
    }


}
