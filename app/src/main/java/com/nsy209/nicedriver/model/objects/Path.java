package com.nsy209.nicedriver.model.objects;

import android.location.Address;

import java.util.Date;

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
}
