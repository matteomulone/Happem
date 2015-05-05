package com.happem.happem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

/**
 * 带滑动标题的ViewPager
 */
public class MainActivity extends FragmentActivity {
    private PagerSlidingTabStrip pagerSlidingTabStrip1;
	private ViewPager viewPager1;
    private ImageButton profile;

    static final int NUM_ITEMS = 2;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.happem.happem.R.layout.main_activity);
        pagerSlidingTabStrip1 = (PagerSlidingTabStrip) findViewById(com.happem.happem.R.id.slidingTabStrip_1);
        viewPager1 = (ViewPager) findViewById(com.happem.happem.R.id.viewPager_1);

        init(0, pagerSlidingTabStrip1, viewPager1);

        profile = (ImageButton) findViewById(com.happem.happem.R.id.profile_button);

        profile.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                finish();
            }

        });

    }

    private void init(int index, PagerSlidingTabStrip pagerSlidingTabStrip, ViewPager viewPager){
        int length = pagerSlidingTabStrip.getTabCount();
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        viewPager.setCurrentItem(index < length ? index : length);
        pagerSlidingTabStrip.setViewPager(viewPager);
    }

    public static class MyPagerAdapter extends FragmentStatePagerAdapter {
        private static int NUM_ITEMS = 2;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    return new HomeFragment();
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    return new NotificationFragment();
                default:
                    return null;
            }
        }

    }

}