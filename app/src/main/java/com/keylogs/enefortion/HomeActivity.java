package com.keylogs.enefortion;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.keylogs.enefortion.fragments.HomeFragment;
import com.keylogs.enefortion.fragments.LeaderboardsFragment;
import com.keylogs.enefortion.fragments.SettingsFragment;

public class HomeActivity extends AppCompatActivity {

    private final FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        if (savedInstanceState == null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment, new HomeFragment())
                    .commit();
        }
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment selectFragment = null;
            int itemId = item.getItemId();  // Get item ID here

            if (itemId == R.id.navigation_home) {
                selectFragment = new HomeFragment();
            } else if (itemId == R.id.navigation_leaderboards) {
                selectFragment = new LeaderboardsFragment();
            } else if (itemId == R.id.navigation_settings) {
                selectFragment = new SettingsFragment();
            }
//            else if (itemId == R.id.navigation_about) {
//                selectFragment = new AboutFragment();
//            }

            if (selectFragment != null) {
                fragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment, selectFragment)
                        .commit();
            }
            updateIcons(bottomNavigationView, itemId);  // Corrected method call
            return true;
        });
        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new HomeFragment()).commit();
        }
    }

    private void updateIcons(BottomNavigationView bottomNavigationView, int selectedId) {
        int[] itemIds = {
                R.id.navigation_home,
                R.id.navigation_leaderboards,
                R.id.navigation_settings,
//                R.id.navigation_about
        };

        for (int id : itemIds) {
            MenuItem menuItem = bottomNavigationView.getMenu().findItem(id);
            if (menuItem != null) {
                int icon = id == selectedId ? getIconFilled(id) : getIconOutlined(id);
                menuItem.setIcon(ContextCompat.getDrawable(this, icon));
            }
        }
    }


    private int getIconFilled(int itemId) {
        if (itemId == R.id.navigation_home) {
            return R.drawable.baseline_home_24;
        } else if (itemId == R.id.navigation_leaderboards) {
            return R.drawable.baseline_leaderboard_24;
        } else if (itemId == R.id.navigation_settings) {
            return R.drawable.baseline_settings_24;
        }
//        else if (itemId == R.id.navigation_about) {
//            return R.drawable.baseline_info_24;
//        }
        else {
            return R.drawable.baseline_home_24; // Default icon if none match
        }
    }

    private int getIconOutlined(int itemId) {
        if (itemId == R.id.navigation_home) {
            return R.drawable.outline_home_24;
        } else if (itemId == R.id.navigation_leaderboards) {
            return R.drawable.outline_leaderboard_24;
        } else if (itemId == R.id.navigation_settings) {
            return R.drawable.outline_settings_24;
        }
//        else if (itemId == R.id.navigation_about) {
//            return R.drawable.outline_info_24;
//        }
        else {
            return R.drawable.outline_home_24; // Default icon if none match
        }
    }
}