package com.nsy209.nicedriver.ui.activities;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.nsy209.nicedriver.R;
import com.nsy209.nicedriver.model.AppDatabase;
import com.nsy209.nicedriver.model.objects.BodyPointsAndSignal;
import com.nsy209.nicedriver.model.objects.PointCalcul;
import com.nsy209.nicedriver.model.objects.Trip;
import com.nsy209.nicedriver.services.ApiSingleton;
import com.nsy209.nicedriver.ui.fragments.ListPathFragment;
import com.nsy209.nicedriver.ui.fragments.MapFragment;
import com.xee.api.Xee;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    private static final int FRAGMENT_MAP = 0;
    private static final int FRAGMENT_LIST = 1;
    private static final String TAG = MainActivity.class.getName().substring(MainActivity.class.getName().lastIndexOf("."));
    public static final String USER_TYPE = "USER_TYPE";
    public static final String TYPE_DRIVER = "TYPE_DRIVER";
    public static final String TYPE_ADMIN = "TYPE_ADMIN";

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
    private String mTypeUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTypeUser = PreferenceManager.getDefaultSharedPreferences(this).getString(USER_TYPE, TYPE_DRIVER);

        ButterKnife.bind(this);
        mBottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (mTypeUser.equals(TYPE_ADMIN)) {
            mBottomNavigation.setVisibility(View.GONE);
        } else {
            mBottomNavigation.setVisibility(View.VISIBLE);
        }

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

    }

    private void testRestCall() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Call<List<PointCalcul>> call = ApiSingleton.getNiceDriverInstance().getCalculatedPoints(
                        new BodyPointsAndSignal(AppDatabase.getAppDatabase(MainActivity.this).locationDao().getAll(),
                                AppDatabase.getAppDatabase(MainActivity.this).signalDao().getAll()));

                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        call.enqueue(new Callback<List<PointCalcul>>() {
                            @Override
                            public void onResponse(Call<List<PointCalcul>> call, Response<List<PointCalcul>> response) {
                                Log.d("RestCALL", "ok");
                                try {
                                    List<PointCalcul> points = response.body();
                                    Log.d("RestCALL","Points : " + points);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(Call<List<PointCalcul>> call, Throwable t) {
                                Log.d("RestCALL", "fail");
                            }
                        });
                    }
                });
            }
        }).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        testRestCall();
    }

    public void displayTrip(Trip trip) {
        ((MapFragment) mViewPagerAdapter.getActiveFragment(mViewPager, FRAGMENT_MAP)).drawTrip(trip);
        mViewPager.setCurrentItem(FRAGMENT_MAP);
    }


    /**
     * A simple pager adapter that represents 3 fragments corresponding to each button of the
     * bottomNavigationView
     */
    private class ViewPagerAdapter extends FragmentPagerAdapter {
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
            }
            return null;
        }

        @Override
        public int getCount() {
            if (mTypeUser.equals(TYPE_DRIVER)) {
                return 2;
            } else {
                return 1;
            }
        }

        public Fragment getActiveFragment(ViewPager container, int position) {
            String name = makeFragmentName(container.getId(), position);
            return getSupportFragmentManager().findFragmentByTag(name);
        }

        private String makeFragmentName(int viewId, int index) {
            return "android:switcher:" + viewId + ":" + index;
        }
    }
}