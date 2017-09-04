package com.nsy209.nicedriver.model.objects;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.nsy209.nicedriver.model.AppDatabase;
import com.nsy209.nicedriver.utils.GeoUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Sébastien on 13/07/2017.
 */

public class Path {
    private final Trip mTrip;
    private Address mStartAddress;
    private Address mEndAddress;
    private double mStartAltitude;
    private double mEndAltitude;
    private double mDistance;
    private Date mStartDate;
    private Date mEndDate;
    private long mTime;

    public Path(Trip trip) {
        mTrip = trip;
    }

    public Address getStartAddress() {
        return mStartAddress;
    }

    public void setStartAddress(Address startAddress) {
        mStartAddress = startAddress;
    }

    public Address getEndAddress() {
        return mEndAddress;
    }

    public void setEndAddress(Address endAddress) {
        mEndAddress = endAddress;
    }

    public double getStartAltitude() {
        return mStartAltitude;
    }

    public void setStartAltitude(double startAltitude) {
        mStartAltitude = startAltitude;
    }

    public double getEndAltitude() {
        return mEndAltitude;
    }

    public void setEndAltitude(double endAltitude) {
        mEndAltitude = endAltitude;
    }

    public double getDistance() {
        return mDistance;
    }

    public void setDistance(double distance) {
        mDistance = distance;
    }

    public Date getStartDate() {
        return mStartDate;
    }

    public void setStartDate(Date startDate) {
        mStartDate = startDate;
    }

    public Date getEndDate() {
        return mEndDate;
    }

    public void setEndDate(Date endDate) {
        mEndDate = endDate;
    }

    public long getTime() {
        return mTime;
    }

    public void setTime(long time) {
        mTime = time;
    }

    public Trip getTrip() {
        return mTrip;
    }

    @Override
    public String toString() {
        return "Path{" +
                "mStartAddress=" + mStartAddress +
                ", mEndAddress=" + mEndAddress +
                ", mStartAltitude=" + mStartAltitude +
                ", mEndAltitude=" + mEndAltitude +
                ", mDistance=" + mDistance +
                ", mStartDate=" + mStartDate +
                ", mEndDate=" + mEndDate +
                ", mTime=" + mTime +
                '}';
    }

    public static List<Path> getList(Context context) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        final List<Path> list = new ArrayList<>();
        List<com.nsy209.nicedriver.model.objects.Trip> trips = AppDatabase.getAppDatabase(context).tripDao().getAll();
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
        return list;
    }
}
