package com.mplu.julifit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.squareup.picasso.Picasso;

public class ViewExerciseActivity extends AppCompatActivity {

    private String id;
    private DbConnection getExercise;

    private TextView textViewExerciseViewTitle,
            textViewExerciseType,
            textViewExerciseSecondaryType,
            textViewExerciseDescription;
    private ImageView imageViewExercise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_exercise);


        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        getExercise = new DbConnection("exercises", id);

        textViewExerciseViewTitle = (TextView) findViewById(R.id.textViewExerciseViewTitle);
        textViewExerciseType = (TextView) findViewById(R.id.textViewExerciseType);
        textViewExerciseSecondaryType = (TextView) findViewById(R.id.textViewExerciseSecondaryType);
        textViewExerciseDescription = (TextView) findViewById(R.id.textViewExerciseDescription);
        imageViewExercise = (ImageView) findViewById(R.id.imageViewExercise);

        getExercise.getDocRef().get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        if (documentSnapshot.exists()){
                            textViewExerciseViewTitle.setText(documentSnapshot.getString("title"));
                            textViewExerciseType.setText(documentSnapshot.getString("type"));
                            textViewExerciseSecondaryType.setText(documentSnapshot.getString("secondaryType"));
                            textViewExerciseDescription.setText(documentSnapshot.getString("description"));
                            Picasso.get().load(documentSnapshot.getString("exerciseImage")).into(imageViewExercise);
                        }
                        else{
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.error), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}


