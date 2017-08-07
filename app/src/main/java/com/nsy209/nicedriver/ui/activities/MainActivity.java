package com.nsy209.nicedriver.ui.activities;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.nsy209.nicedriver.R;
import com.nsy209.nicedriver.model.AppDatabase;
import com.nsy209.nicedriver.ui.fragments.ListPathFragment;
import com.nsy209.nicedriver.ui.fragments.MapFragment;
import com.nsy209.nicedriver.ui.fragments.SettingsFragment;
import com.xee.api.Xee;
import com.xee.core.XeeEnv;
import com.xee.core.entity.OAuth2Client;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    private static final int NB_FRAGMENTS = 3;
    private static final int FRAGMENT_MAP = 0;
    private static final int FRAGMENT_LIST = 1;
    private static final int FRAGMENT_SETTINGS = 2;
    private static final String TAG = MainActivity.class.getName().substring(MainActivity.class.getName().lastIndexOf("."));

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mViewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_dashboard:
                    mViewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_notifications:
                    mViewPager.setCurrentItem(2);
                    return true;
            }
            return false;
        }
    };
    @BindView(R.id.navigation)
    BottomNavigationView mBottomNavigation;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    private ViewPagerAdapter mViewPagerAdapter;
    private MenuItem mPrevMenuItem;
    private Xee mXeeApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ButterKnife.bind(this);
        mBottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (mPrevMenuItem != null) {
                    mPrevMenuItem.setChecked(false);
                } else {
                    mBottomNavigation.getMenu().getItem(0).setChecked(false);
                }

                mPrevMenuItem = mBottomNavigation.getMenu().getItem(i);
                mPrevMenuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        initXee();
    }

    private void initXee() {
        XeeEnv xeeEnv = new XeeEnv(this,
                new OAuth2Client("gQkYPQj7BrL11P1i9vms",
                        "41HO4u9scRp2gI8UgRTL",
                        "http://localhost"),
                60, 60, XeeEnv.SANDBOX);
        mXeeApi = new Xee(xeeEnv);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppDatabase.exportDatabase(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MapFragment.REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    ((MapFragment) mViewPagerAdapter.getItem(0)).initMap();
                } else {
                    Toast.makeText(this, getString(R.string.access_location_needed), Toast.LENGTH_SHORT).show();
                }
            }
        }


    }

    public Xee getXeeApi() {
        return mXeeApi;
    }

    /**
     * A simple pager adapter that represents 3 fragments corresponding to each button of the
     * bottomNavigationView
     */
    private class ViewPagerAdapter extends FragmentStatePagerAdapter {
        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case FRAGMENT_MAP:
                    return MapFragment.newInstance();
                case FRAGMENT_LIST:
                    return ListPathFragment.newInstance();
                case FRAGMENT_SETTINGS:
                    return SettingsFragment.newInstance();
            }
            return null;
        }

        @Override
        public int getCount() {
            return NB_FRAGMENTS;
        }
    }
}