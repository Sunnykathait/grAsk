package com.example.grask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeApp extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_app);

        userLoggedOK();

        bottomNavigationView = findViewById(R.id.bottom_navigation2);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                int id = item.getItemId();

                if(id == R.id.navigation_home){
                    selectedFragment = new HomeFragment();
                }else if(id == R.id.navigation_post){
                    selectedFragment = new PostFragment();
                }else if(id == R.id.navigation_setting){
                    selectedFragment = new SettingFragment();
                }

                if (selectedFragment != null) {
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.fragment_container, selectedFragment);
                    transaction.commit();
                }

                return true;
            }
        });

        // Set the default fragment when the app starts
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);


    }

    void userLoggedOK(){
        SharedCustomeClass sharedPreferencesManager = SharedCustomeClass.getInstance(getApplicationContext());
        sharedPreferencesManager.setBoolean("isLoggedIn", true);
    }
}