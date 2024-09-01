package com.keylogs.enefortion.activity.investigatingmotion;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.keylogs.enefortion.R;
import com.keylogs.enefortion.fragments.motion.Speed.SpeedPage1Fragment;
import com.keylogs.enefortion.fragments.motion.Speed.SpeedPage2Fragment;
import com.keylogs.enefortion.fragments.motion.Speed.SpeedPage3Fragment;
import com.keylogs.enefortion.fragments.motion.Speed.SpeedPage4Fragment;
import com.keylogs.enefortion.fragments.motion.distance.DistancePage1Fragment;
import com.keylogs.enefortion.fragments.motion.distance.DistancePage2Fragment;
import com.keylogs.enefortion.fragments.motion.distance.DistancePage3Fragment;
import com.keylogs.enefortion.fragments.motion.distance.DistancePage4Fragment;

public class SpeedActivity extends FragmentActivity {

    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static final int NUM_PAGES = 4;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager2 viewPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private FragmentStateAdapter pagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distance);

        // Instantiate a ViewPager2 and a PagerAdapter.
        viewPager = findViewById(R.id.main);
        pagerAdapter = new SpeedActivity.ScreenSlidePagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);
    }
    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }
    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        public ScreenSlidePagerAdapter(FragmentActivity fa) {
            super(fa);
        }

        @Override
//        public Fragment createFragment(int position) {
//            return new ScreenSlidePage();
//        }
        @NonNull
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return SpeedPage1Fragment.newInstance("param1", "param2");
                case 1:
                    return SpeedPage2Fragment.newInstance("param1", "param2");
                // Return the second fragment here
                case 2:
                    return SpeedPage3Fragment.newInstance("param1", "param2");
                // Return the third fragment here
                case 3:
                    return SpeedPage4Fragment.newInstance("param1", "param2");
                // Return the fourth fragment here
//                case 4:
//                    return Page5Fragment.newInstance("param1", "param2");
//                    // Return the fifth fragment here
                default:
                    return DistancePage1Fragment.newInstance("param1", "param2");
            }
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }
}