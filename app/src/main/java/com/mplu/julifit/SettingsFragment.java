package com.mplu.julifit;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.DocumentSnapshot;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class SettingsFragment extends Fragment implements View.OnClickListener {

    // elements

    private TextView checkBoxTitle;
    private EditText userName,userEmail,userIdPassword,userNewPassword,userNewPassword2;
    private CheckBox changePassword;
    private Button buttonSave;

    private boolean changePasswordBoolean = false;

    FirebaseUser user;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = FirebaseAuth.getInstance().getCurrentUser();
        getUserName();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View SettingsView = inflater.inflate(R.layout.fragment_settings,container,false);
        // set up elements
        checkBoxTitle = (TextView) SettingsView.findViewById(R.id.changePasswordTextView);
        userName = (EditText) SettingsView.findViewById(R.id.userName);
        userEmail = (EditText) SettingsView.findViewById(R.id.userIdLogin);
        userIdPassword = (EditText) SettingsView.findViewById(R.id.userIdPassword);
        userNewPassword = (EditText) SettingsView.findViewById(R.id.userNewPassword);
        userNewPassword2 = (EditText) SettingsView.findViewById(R.id.userNewPassword2);
        changePassword = (CheckBox) SettingsView.findViewById(R.id.changePasswordCheckbox);
        buttonSave = (Button) SettingsView.findViewById(R.id.buttonSave);

        // set listener on Checkbox

        changePassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    changePasswordBoolean=true;
                    userEmail.setVisibility(View.VISIBLE);
                    userEmail.setText(user.getEmail());
                    userIdPassword.setVisibility(View.VISIBLE);
                    userNewPassword.setVisibility(View.VISIBLE);
                    userNewPassword2.setVisibility(View.VISIBLE);
                    userName.setVisibility(View.GONE);
                    changePassword.setVisibility(View.GONE);
                    checkBoxTitle.setVisibility(View.GONE);
                }
            }
        });

        buttonSave.setOnClickListener(this);
        return SettingsView;
    }


    private void getUserName(){

        String email = "User Name";
        if (user != null) {
            for (UserInfo profile : user.getProviderData()) {
                email = profile.getEmail();
            }
        }
        DbConnection getUserName = new DbConnection("users",email);
        getUserName.getDocRef().get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        userName.setText(documentSnapshot.getString("name"));
                    }
                });
    }


    @Override
    public void onClick(View v) {
        if (changePasswordBoolean){
            if (userNewPassword.getText().toString().trim().equals(userNewPassword2.getText().toString().trim()) &
                    userNewPassword.getText().toString().trim().length()>5) {
                AuthCredential credential = EmailAuthProvider
                        .getCredential(user.getEmail(), userIdPassword.getText().toString().trim());
                user.reauthenticate(credential)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    user.updatePassword(userNewPassword.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getActivity(), getResources().getString(R.string.passwordUpdated), Toast.LENGTH_SHORT).show();
                                            } else {
                                                Log.d(TAG, "Error password not updated");
                                            }
                                        }
                                    });
                                } else {
                                    Log.d(TAG, "Error auth failed");
                                }
                            }
                        });
            }
            else{
                Toast.makeText(getActivity(), getResources().getString(R.string.passwordsNotMatching), Toast.LENGTH_SHORT).show();
            }
        }
        else{
            DbConnection setUserName = new DbConnection("users",user.getEmail());
            String newName = userName.getText().toString().trim();
            setUserName.docRef.update("name",newName)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getActivity(), getResources().getString(R.string.nameWasUpdated), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), getResources().getString(R.string.nameWasNotUpdated), Toast.LENGTH_SHORT).show();
                        }
                    });

        }


    }
}
