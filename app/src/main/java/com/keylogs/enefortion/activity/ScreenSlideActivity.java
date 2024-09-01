package com.keylogs.enefortion.activity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.keylogs.enefortion.R;
import com.keylogs.enefortion.fragments.motion.Page1Fragment;
import com.keylogs.enefortion.fragments.motion.Page2Fragment;
import com.keylogs.enefortion.fragments.motion.Page3Fragment;
import com.keylogs.enefortion.fragments.motion.Page4Fragment;
import com.keylogs.enefortion.fragments.motion.Page5Fragment;

public class ScreenSlideActivity extends FragmentActivity {

    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static final int NUM_PAGES = 5;

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
        setContentView(R.layout.activity_screen_slide);

        // Instantiate a ViewPager2 and a PagerAdapter.
        viewPager = findViewById(R.id.main);
        pagerAdapter = new ScreenSlidePagerAdapter(this);
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

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
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
                    return Page1Fragment.newInstance("param1", "param2");
                case 1:
                    return Page2Fragment.newInstance("param1", "param2");
                    // Return the second fragment here
                case 2:
                    return Page3Fragment.newInstance("param1", "param2");
                    // Return the third fragment here
                case 3:
                    return Page4Fragment.newInstance("param1", "param2");
                    // Return the fourth fragment here
                case 4:
                    return Page5Fragment.newInstance("param1", "param2");
                    // Return the fifth fragment here
                default:
                    return Page1Fragment.newInstance("param1", "param2");
            }
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }
}