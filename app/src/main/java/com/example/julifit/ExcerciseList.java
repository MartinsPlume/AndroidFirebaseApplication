package com.example.julifit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;


public class ExcerciseList extends AppCompatActivity {

    private TextView excerciseTitleText;
    private TextView firstExcercise;
    private TextView secondExcercise;
    private TextView thirdExcercise;

    private Button homeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excercise_list);
        excerciseTitleText = (TextView) findViewById(R.id.excerciseTitleText);
        firstExcercise = (TextView) findViewById(R.id.firstExcercise);
        secondExcercise = (TextView) findViewById(R.id.secondExcercise);
        thirdExcercise = (TextView) findViewById(R.id.thirdExcercise);

        homeButton = (Button) findViewById(R.id.homeButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openExcercise();
            }
        });

        Intent intent = getIntent();
        String excercise = intent.getStringExtra("grupa");

        switch(excercise){
            case "leg":
                excerciseTitleText.setText("Kāju vingrojumi");
                firstExcercise.setText("Pirmais kāju vingrojums");
                secondExcercise.setText("Otrais kāju vingrojums");
                thirdExcercise.setText("Trešais kāju vingrojums");
                break;
            case "back":
                excerciseTitleText.setText("Muguras vingrojumi");
                firstExcercise.setText("Pirmais muguras vingrojums");
                secondExcercise.setText("Otrais muguras vingrojums");
                thirdExcercise.setText("Trešais muguras vingrojums");
                break;
            case "chest":
                excerciseTitleText.setText("Krūšu vingrojumi");
                firstExcercise.setText("Pirmais krūšu vingrojums");
                secondExcercise.setText("Otrais krūšu vingrojums");
                thirdExcercise.setText("Trešais krūšu vingrojums");
                break;
            case "shoulder":
                excerciseTitleText.setText("Plecu vingrojumi");
                firstExcercise.setText("Pirmais plecu vingrojums");
                secondExcercise.setText("Otrais plecu vingrojums");
                thirdExcercise.setText("Trešais plecu vingrojums");
                break;
            case "arms":
                excerciseTitleText.setText("Rokas vingrojumi");
                firstExcercise.setText("Pirmais roku vingrojums");
                secondExcercise.setText("Otrais roku vingrojums");
                thirdExcercise.setText("Trešais roku vingrojums");
                break;
        }


    }
    private void openExcercise() {
        Intent intent = new Intent(this,ExcerciseActivity.class);
        startActivity(intent);
    }
}
