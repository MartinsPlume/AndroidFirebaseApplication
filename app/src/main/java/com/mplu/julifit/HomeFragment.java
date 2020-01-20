package com.mplu.julifit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;

public class HomeFragment extends Fragment {

    private TextView homeText;

    FirebaseUser user;

    DbConnection homeTextConnection;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = FirebaseAuth.getInstance().getCurrentUser();
        homeTextConnection=new DbConnection("users",user.getEmail());
        setUpText();
    }

    private void setUpText() {
        homeTextConnection.getDocRef().get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        homeText.setText(documentSnapshot.getString("home"));
                    }
                });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View HomeView = inflater.inflate(R.layout.fragment_home,container,false);
        homeText = HomeView.findViewById(R.id.textViewHome);
        return HomeView;
    }
}
