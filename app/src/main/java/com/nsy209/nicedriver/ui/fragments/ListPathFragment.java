package com.nsy209.nicedriver.ui.fragments;


import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.google.android.gms.maps.model.LatLng;
import com.nsy209.nicedriver.R;
import com.nsy209.nicedriver.model.AppDatabase;
import com.nsy209.nicedriver.model.objects.Path;
import com.nsy209.nicedriver.model.objects.Signal;
import com.nsy209.nicedriver.services.ApiSingleton;
import com.nsy209.nicedriver.ui.activities.LoginActivity;
import com.nsy209.nicedriver.ui.activities.MainActivity;
import com.nsy209.nicedriver.ui.adapters.ListPathAdapter;
import com.nsy209.nicedriver.utils.GeoUtils;
import com.xee.api.entity.Location;
import com.xee.api.entity.Trip;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListPathFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListPathFragment extends Fragment {

    private Unbinder unbinder;
    @BindView(R.id.floating_search_view)
    FloatingSearchView mSearchView;

    @BindView(R.id.list_path)
    RecyclerView mRecyclerView;

    @BindView(R.id.fab)
    FloatingActionButton mFab;
    private ListPathAdapter mAdapter;

    public ListPathFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ListPathFragment.
     */
    public static ListPathFragment newInstance() {
        ListPathFragment fragment = new ListPathFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_path, container, false);
        unbinder = ButterKnife.bind(this, view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter = new ListPathAdapter((MainActivity) getActivity(), new ArrayList<Path>()));
        updateList();

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDataXee();
                updateList();
            }
        });


        mSearchView.setOnMenuItemClickListener(new FloatingSearchView.OnMenuItemClickListener() {
            @Override
            public void onActionMenuItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_disconnect:
                        PreferenceManager.getDefaultSharedPreferences(getContext()).edit().remove(MainActivity.USER_TYPE).commit();
                        getActivity().finish();
                        Intent intent = new Intent(getContext(), LoginActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });
        mSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, final String newQuery) {

//                //get suggestions based on newQuery
//
//                //pass them on to the search view
//                mSearchView.swapSuggestions(newSuggestions);
            }
        });
        return view;
    }

    private void updateDataXee() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final Call<List<Location>> call = ApiSingleton.getNiceDriverInstance().getLocations();
                    AppDatabase.getAppDatabase(getContext()).locationDao()
                            .insertAll(com.nsy209.nicedriver.model.objects.Location.convertFromXee(call.execute().body()));
                } catch (IOException e) {
                    e.printStackTrace();
                }


                try {
                    final Call<List<Trip>> call = ApiSingleton.getNiceDriverInstance().getTrips();
                    AppDatabase.getAppDatabase(getContext()).tripDao()
                            .insertAll(com.nsy209.nicedriver.model.objects.Trip.convertFromXee(call.execute().body()));
                } catch (IOException e) {
                    e.printStackTrace();
                }


                try {
                    List<Signal> listTem = ApiSingleton.getNiceDriverInstance().getSignals()
                            .execute().body();
                    Log.d("ListTemp",
                            "ListTemp : " + listTem + (listTem == null ? 0 : listTem.size()));
                    AppDatabase.getAppDatabase(getContext()).signalDao()
                            .insertAll(listTem);
                    Log.d("InsertedInDb", "Signals : " + AppDatabase.getAppDatabase(getContext()).signalDao().getAll());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    private void updateList() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                final List<Path> list = new ArrayList<>();
                List<com.nsy209.nicedriver.model.objects.Trip> trips = AppDatabase.getAppDatabase(getContext()).tripDao().getAll();
                for (com.nsy209.nicedriver.model.objects.Trip trip : trips) {
                    Path path = new Path(trip);

                    try {
                        List<Address> addresses = geocoder.getFromLocation(trip.getPointBeginLatitude(), trip.getPointBeginLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                        path.setStartAddress(addresses.get(0));
                        Log.d("Address", "start " + path.getStartAddress());
                        addresses = geocoder.getFromLocation(trip.getPointEndLatitude(), trip.getPointEndLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                        path.setEndAddress(addresses.get(0));
                        Log.d("Address", "end " + path.getEndAddress());

                        path.setDistance(GeoUtils.distanceInMeters(new LatLng(trip.getPointBeginLatitude(), trip.getPointBeginLongitude()),
                                trip.getPointBeginAltitude(),
                                new LatLng(trip.getPointEndLatitude(), trip.getPointEndLongitude()), trip.getPointEndAltitude()));

                        path.setStartDate(trip.getBeginDate());
                        path.setEndDate(trip.getEndDate());
                        path.setTime(GeoUtils.differenceInTime(path.getStartDate(), path.getEndDate()));
                        list.add(path);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.updateList(list);
                    }
                });
            }
        }).start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
