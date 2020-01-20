package com.mplu.julifit;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ExerciseFragment extends Fragment implements View.OnClickListener {

    private FloatingActionButton buttonLegs, buttonBack,buttonCore,buttonChest,buttonShoulders,buttonArms,buttonOther;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View ExerciseView = inflater.inflate(R.layout.fragment_exercise,container,false);
        buttonLegs = (FloatingActionButton) ExerciseView.findViewById(R.id.buttonLegs);
        buttonLegs.setOnClickListener(this);
        buttonBack = (FloatingActionButton) ExerciseView.findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(this);
        buttonCore = (FloatingActionButton) ExerciseView.findViewById(R.id.buttonCore);
        buttonCore.setOnClickListener(this);
        buttonChest = (FloatingActionButton) ExerciseView.findViewById(R.id.buttonChest);
        buttonChest.setOnClickListener(this);
        buttonShoulders = (FloatingActionButton) ExerciseView.findViewById(R.id.buttonShoulders);
        buttonShoulders.setOnClickListener(this);
        buttonArms = (FloatingActionButton) ExerciseView.findViewById(R.id.buttonArms);
        buttonArms.setOnClickListener(this);
        buttonOther = (FloatingActionButton) ExerciseView.findViewById(R.id.buttonOther);
        buttonOther.setOnClickListener(this);
        return ExerciseView;
    }

    @Override
    public void onClick(View v) {
        String type;
        switch (v.getId()) {
            case R.id.buttonLegs:
                type="Legs";
                break;
            case R.id.buttonBack:
                type="Back";
                break;
            case R.id.buttonCore:
                type="Core";
                break;
            case R.id.buttonChest:
                type="Chest";
                break;
            case R.id.buttonShoulders:
                type="Shoulders";
                break;
            case R.id.buttonArms:
                type="Arms";
                break;
            case R.id.buttonOther:
                type="Other";
                break;
                default:
                    type=getResources().getString(R.string.error);
                    break;
        }
        Toast.makeText(getActivity(), type, Toast.LENGTH_SHORT).show();
        if (!type.equals("Encountered error!")){
            openExercise(type);
        }
    }

    private void openExercise(String type){
        Intent intent = new Intent(getActivity(),ExerciseActivity.class);
        intent.putExtra("type",type);
        startActivity(intent);
    }
}
