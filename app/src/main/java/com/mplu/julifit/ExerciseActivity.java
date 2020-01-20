package com.mplu.julifit;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

public class ExerciseActivity extends AppCompatActivity{

    private DbConnection getExercises = new DbConnection("exercises");
    private String type;
    private TextView textViewTitle;

    private ExerciseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        textViewTitle = findViewById(R.id.textViewRecyclerTitle);
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        setUpTitle(type);
        setUpRecyclerView();
    }

    private void setUpTitle(String type) {
        switch (type){
            case "Legs":
                textViewTitle.setText(R.string.LegExercises);
                break;
            case "Back":
                textViewTitle.setText(R.string.BackExercises);
                break;
            case "Core":
                textViewTitle.setText(R.string.CoreExercises);
                break;
            case "Chest":
                textViewTitle.setText(R.string.ChestExercises);
                break;
            case "Shoulders":
                textViewTitle.setText(R.string.ShouldersExercises);
                break;
            case "Arms":
                textViewTitle.setText(R.string.ArmsExercises);
                break;
            case "Other":
                textViewTitle.setText(R.string.OtherExercises);
                break;
            default:
                break;
        }
    }

    private void setUpRecyclerView() {
        Query query = getExercises.getColRef()
                .whereEqualTo("type",type);
        FirestoreRecyclerOptions<Exercise> options = new FirestoreRecyclerOptions.Builder<Exercise>()
                .setQuery(query,Exercise.class)
                .build();

        adapter = new ExerciseAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new ExerciseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Intent intent = new Intent(ExerciseActivity.this, ViewExerciseActivity.class);
                intent.putExtra("id",documentSnapshot.getId());
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
