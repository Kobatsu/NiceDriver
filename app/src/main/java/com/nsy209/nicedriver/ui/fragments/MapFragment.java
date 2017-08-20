package com.nsy209.nicedriver.ui.fragments;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.nsy209.nicedriver.R;
import com.nsy209.nicedriver.model.AppDatabase;
import com.nsy209.nicedriver.model.objects.Location;
import com.nsy209.nicedriver.model.objects.Trip;
import com.nsy209.nicedriver.ui.activities.LoginActivity;
import com.nsy209.nicedriver.ui.activities.MainActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment {

    public static final int REQUEST_LOCATION = 1230;
    private Unbinder unbinder;
    @BindView(R.id.mapView)
    MapView mMapView;
    @BindView(R.id.floating_search_view)
    FloatingSearchView mFloatingSearchView;
    private GoogleMap googleMap;
    private Polyline mPolyline;

    public MapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MapFragment.
     */
    public static MapFragment newInstance() {
        MapFragment fragment = new MapFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        unbinder = ButterKnife.bind(this, view);

        mMapView.onCreate(savedInstanceState);

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        initMap();
        mFloatingSearchView.setOnMenuItemClickListener(new FloatingSearchView.OnMenuItemClickListener() {
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

        return view;
    }

    public void initMap() {
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                // For showing a move to my location button
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        &&
                        ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                            REQUEST_LOCATION);

                } else {
                    googleMap.setMyLocationEnabled(true);

                    // For dropping a marker at a point on the Map
                    LatLng sydney = new LatLng(43.556606, 1.465789);
                    googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description"));

                    // For zooming automatically to the location of the marker
//                    CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
//                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

//                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15));  //move camera to location
                    Marker hamburg = googleMap.addMarker(new MarkerOptions().position(sydney));

                    mMapView.onResume(); // needed to get the map to display immediately
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    private void drawLine(List<Location> list) {
        if (mPolyline != null) {
            mPolyline.remove();
        }
        List<LatLng> points = null;
        PolylineOptions lineOptions = null;
        MarkerOptions markerOptions = new MarkerOptions();

        // Traversing through all the routes
        points = new ArrayList<LatLng>();

        lineOptions = new PolylineOptions();

        for (Location l : list) {

            double lat = l.getLatitude();
            double lng = l.getLongitude();
            LatLng position = new LatLng(lat, lng);

            points.add(position);
        }
        // Adding all the points in the route to LineOptions
        lineOptions.addAll(points);
        lineOptions.width(8);
        lineOptions.color(Color.RED);


        // Drawing polyline in the Google Map for the i-th route
        mPolyline = googleMap.addPolyline(lineOptions);

        LatLng start = new LatLng(list.get(0).getLatitude(), list.get(0).getLongitude());
        CameraPosition cameraPosition = new CameraPosition.Builder().target(start).zoom(12).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(start, 15));  //move camera to location

    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onDestroyView() {
        mMapView.onDestroy();
        unbinder.unbind();
        super.onDestroyView();
    }

    public void drawTrip(final Trip trip) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final List<Location> locations = AppDatabase.getAppDatabase(getContext()).locationDao()
                        .getByTrip(trip.getBeginDate().getTime(), trip.getEndDate().getTime());
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        drawLine(locations);
                    }
                });
            }
        }).start();
    }
}
