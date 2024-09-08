package com.keylogs.enefortion.activity.investigatingmotion;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.keylogs.enefortion.R;
import com.keylogs.enefortion.fragments.motion.distance.DistancePage1Fragment;

public class DistanceActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distance);

        fragmentManager = getSupportFragmentManager();

        if (savedInstanceState == null) {
            // Display the DistancePage1Fragment when the app starts
            showFragment(new DistancePage1Fragment());
        }
    }

    public void showFragment(Fragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.distanceMain, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
