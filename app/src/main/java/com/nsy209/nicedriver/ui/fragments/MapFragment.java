package com.nsy209.nicedriver.ui.fragments;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.nsy209.nicedriver.R;
import com.nsy209.nicedriver.model.AppDatabase;
import com.nsy209.nicedriver.model.objects.Location;
import com.nsy209.nicedriver.model.objects.PointCalcul;
import com.nsy209.nicedriver.model.objects.Trip;
import com.nsy209.nicedriver.services.ApiSingleton;
import com.nsy209.nicedriver.ui.activities.LoginActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nsy209.nicedriver.ui.activities.MainActivity.TYPE_DRIVER;
import static com.nsy209.nicedriver.ui.activities.MainActivity.USER_TYPE;

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
    @BindView(R.id.filter_signals)
    fr.ganfra.materialspinner.MaterialSpinner mSpinner;

    private GoogleMap googleMap;
    private Polyline mPolyline;
    private List<PointCalcul> mPointsCalculated;
    private ArrayList<Marker> mMarkers;
    private ArrayAdapter<String> mSpinnerAdapter;

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
                        PreferenceManager.getDefaultSharedPreferences(getContext()).edit().remove(USER_TYPE).commit();
                        getActivity().finish();
                        Intent intent = new Intent(getContext(), LoginActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });
        List<String> types = AppDatabase.getAppDatabase(getContext()).signalDao().getTypes();
        types.add(0, getString(R.string.signal_type));
        mSpinnerAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, types);
        mSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(mSpinnerAdapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
//                    Toast.makeText(getContext(), "Item clicked " + mSpinnerAdapter.getItem(i), Toast.LENGTH_SHORT).show();
                    // make the query either on database if user connected or on cloud if not
                    showPointsSignal(mSpinnerAdapter.getItem(i));
                } else {
                    removePoints();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return view;
    }

    private void removePoints() {
        if (mMarkers != null && !mMarkers.isEmpty()) {
            for (Marker p : mMarkers) {
                p.remove();
            }
            mMarkers.clear();
        }
    }

    private void showPointsSignal(String item) {
        if (PreferenceManager.getDefaultSharedPreferences(getContext()).getString(USER_TYPE, TYPE_DRIVER).equals(TYPE_DRIVER)) {
            // get signals in database
            List<PointCalcul> pointCalculs = AppDatabase.getAppDatabase(getContext()).pointCalculDao().getPoints(item);
            addMarkers(pointCalculs);
        } else {
            // get signals from the cloud
            final Call<List<PointCalcul>> call = ApiSingleton.getNiceDriverInstance().getCalculatedPoints(item);

            call.enqueue(new Callback<List<PointCalcul>>() {
                @Override
                public void onResponse(Call<List<PointCalcul>> call, Response<List<PointCalcul>> response) {
                    Log.d("getSignalAdmin", "ok");
                    try {
                        List<PointCalcul> points = response.body();
                        addMarkers(points);
                        Log.d("getSignalAdmin", "Points : " + points);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<List<PointCalcul>> call, Throwable t) {
                    Log.d("getSignalAdmin", "fail");
                }
            });
        }
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
                    // CNAM
//                    LatLng point = new LatLng(43.556606, 1.465789);
                    LatLng point = new LatLng(50.681887, 3.12807);

                    // For zooming automatically to the location of the marker
                    CameraPosition cameraPosition = new CameraPosition.Builder().target(point).zoom(11).build();
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                    googleMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
                        @Override
                        public void onCameraIdle() {
                            if (mPointsCalculated != null) {
                                addMarkers(mPointsCalculated);
                            }
                        }
                    });

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

    public void resetSpinnerAdapter() {
        if (mSpinnerAdapter != null && mSpinnerAdapter.getCount() <= 1) {
            List<String> types = AppDatabase.getAppDatabase(getContext()).signalDao().getTypes();
            types.add(0, getString(R.string.signal_type));
            mSpinnerAdapter.clear();
            mSpinnerAdapter.addAll(types);
            mSpinnerAdapter.notifyDataSetChanged();
        }
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


        // Drawing polyline in the Google Map
        mPolyline = googleMap.addPolyline(lineOptions);

        LatLng start = new LatLng(list.get(0).getLatitude(), list.get(0).getLongitude());
        CameraPosition cameraPosition = new CameraPosition.Builder().target(start).zoom(12).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(start, 15));  //move camera to location
    }

    private void addMarkers(List<PointCalcul> pointsCalculated) {
        mPointsCalculated = pointsCalculated;
        final LatLngBounds screenBounds = googleMap.getProjection().getVisibleRegion().latLngBounds;
        BitmapDescriptor icon = getMarkerIconFromDrawable(ContextCompat.getDrawable(getContext(),
                R.drawable.custom_marker));
        if (mMarkers != null && !mMarkers.isEmpty()) {
            for (Marker p : mMarkers) {
                p.remove();
            }
        }
        mMarkers = new ArrayList<>();
        for (PointCalcul point : pointsCalculated) {
            LatLng latLng = new LatLng(point.getLatitude(), point.getLongitude());
            if (screenBounds.contains(latLng)) {
                mMarkers.add(googleMap.addMarker(new MarkerOptions().position(latLng).icon(icon)));
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
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

    public void showTestPoints(List<Location> locations) {
        final LatLngBounds screenBounds = googleMap.getProjection().getVisibleRegion().latLngBounds;
        BitmapDescriptor icon = getMarkerIconFromDrawable(ContextCompat.getDrawable(getContext(),
                R.drawable.custom_marker));
        for (Location point : locations) {
            LatLng latLng = new LatLng(point.getLatitude(), point.getLongitude());
            if (screenBounds.contains(latLng)) {
                googleMap.addMarker(new MarkerOptions().position(latLng).icon(icon));
            }
        }
    }

    private BitmapDescriptor getMarkerIconFromDrawable(Drawable drawable) {
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}
