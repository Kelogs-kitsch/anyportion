package com.keylogs.enefortion;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            // Display the SplashScreenFragment when the app starts
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main, new SplashScreenFragment())
                    .commit();
        }
    }
    public void switchFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main, fragment);
        transaction.addToBackStack(null); // Optional: add to back stack if needed
        transaction.commit();
    }
//    public void showLoginFragment() {
//        // Method to transition to the LoginFragment
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.fragment_container, new LoginFragment());
//        transaction.addToBackStack(null); // Optional: add to back stack if needed
//        transaction.commit();
//    }
}
