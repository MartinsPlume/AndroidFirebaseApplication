package com.mplu.julifit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;




import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class NavigationActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    String currentScreen="h";

    Fragment selectedFragment = null;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if(currentScreen=="h"){
                        Intent intent = new Intent(NavigationActivity.this, MainActivity.class);
                        // clear activities, so back doesn't return to login screen
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        finish();
                        startActivity(intent);
                        break;
                    }
                    else {
                        selectedFragment = new HomeFragment();
                        currentScreen = "h";
                    }
                    break;
                case R.id.navigation_exercises:
                    currentScreen="e";
                    selectedFragment = new ExerciseFragment();
                    break;
                case R.id.navigation_settings:
                    currentScreen="s";
                    selectedFragment = new SettingsFragment();
                    break;
            }
            if (selectedFragment!=null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_location,
                        selectedFragment).commit();
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // firebase
        mAuth = FirebaseAuth.getInstance();
        selectedFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_location,
                selectedFragment).commit();


    }

}
