package com.nsy209.nicedriver.model.objects;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Sébastien on 21/07/2017.
 */
@Entity(tableName = "Trips")
public class Trip {
    @PrimaryKey
    @ColumnInfo(name = "id")
        private String mId;

    @ColumnInfo(name = "pointBeginLatitude")
    private double mPointBeginLatitude;
    @ColumnInfo(name = "pointBeginLongitude")
    private double mPointBeginLongitude;
    @ColumnInfo(name = "pointBeginAltitude")
    private double mPointBeginAltitude;
    @ColumnInfo(name = "pointBeginHeading")
    private double mPointBeginHeading;
    @ColumnInfo(name = "pointBeginSatellites")
    private int mPointBeginSatellites;
    @ColumnInfo(name = "pointBeginDate")
    private Date mPointBeginDate;


    @ColumnInfo(name = "pointEndLatitude")
    private double mPointEndLatitude;
    @ColumnInfo(name = "pointEndLongitude")
    private double mPointEndLongitude;
    @ColumnInfo(name = "pointEndAltitude")
    private double mPointEndAltitude;
    @ColumnInfo(name = "pointEndHeading")
    private double mPointEndHeading;
    @ColumnInfo(name = "pointEndSatellites")
    private int mPointEndSatellites;
    @ColumnInfo(name = "pointEndDate")
    private Date mPointEndDate;


    @ColumnInfo(name = "beginDate")
    private Date mBeginDate;
    @ColumnInfo(name = "endDate")
    private Date mEndDate;
    @ColumnInfo(name = "stopDate")
    private Date mStopDate;
    @ColumnInfo(name = "creationDate")
    private Date mCreationDate;
    @ColumnInfo(name = "lastUpdateDate")
    private Date mLastUpdateDate;
    @ColumnInfo(name = "isSent")
    private boolean mIsSent;


    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public double getPointBeginLatitude() {
        return mPointBeginLatitude;
    }

    public void setPointBeginLatitude(double pointBeginLatitude) {
        mPointBeginLatitude = pointBeginLatitude;
    }

    public double getPointBeginLongitude() {
        return mPointBeginLongitude;
    }

    public void setPointBeginLongitude(double pointBeginLongitude) {
        mPointBeginLongitude = pointBeginLongitude;
    }

    public double getPointBeginAltitude() {
        return mPointBeginAltitude;
    }

    public void setPointBeginAltitude(double pointBeginAltitude) {
        mPointBeginAltitude = pointBeginAltitude;
    }

    public double getPointBeginHeading() {
        return mPointBeginHeading;
    }

    public void setPointBeginHeading(double pointBeginHeading) {
        mPointBeginHeading = pointBeginHeading;
    }

    public int getPointBeginSatellites() {
        return mPointBeginSatellites;
    }

    public void setPointBeginSatellites(int pointBeginSatellites) {
        mPointBeginSatellites = pointBeginSatellites;
    }

    public Date getPointBeginDate() {
        return mPointBeginDate;
    }

    public void setPointBeginDate(Date pointBeginDate) {
        mPointBeginDate = pointBeginDate;
    }

    public double getPointEndLatitude() {
        return mPointEndLatitude;
    }

    public void setPointEndLatitude(double pointEndLatitude) {
        mPointEndLatitude = pointEndLatitude;
    }

    public double getPointEndLongitude() {
        return mPointEndLongitude;
    }

    public void setPointEndLongitude(double pointEndLongitude) {
        mPointEndLongitude = pointEndLongitude;
    }

    public double getPointEndAltitude() {
        return mPointEndAltitude;
    }

    public void setPointEndAltitude(double pointEndAltitude) {
        mPointEndAltitude = pointEndAltitude;
    }

    public double getPointEndHeading() {
        return mPointEndHeading;
    }

    public void setPointEndHeading(double pointEndHeading) {
        mPointEndHeading = pointEndHeading;
    }

    public int getPointEndSatellites() {
        return mPointEndSatellites;
    }

    public void setPointEndSatellites(int pointEndSatellites) {
        mPointEndSatellites = pointEndSatellites;
    }

    public Date getPointEndDate() {
        return mPointEndDate;
    }

    public void setPointEndDate(Date pointEndDate) {
        mPointEndDate = pointEndDate;
    }

    public Date getBeginDate() {
        return mBeginDate;
    }

    public void setBeginDate(Date beginDate) {
        mBeginDate = beginDate;
    }

    public Date getEndDate() {
        return mEndDate;
    }

    public void setEndDate(Date endDate) {
        mEndDate = endDate;
    }

    public Date getStopDate() {
        return mStopDate;
    }

    public void setStopDate(Date stopDate) {
        mStopDate = stopDate;
    }

    public Date getCreationDate() {
        return mCreationDate;
    }

    public void setCreationDate(Date creationDate) {
        mCreationDate = creationDate;
    }

    public Date getLastUpdateDate() {
        return mLastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        mLastUpdateDate = lastUpdateDate;
    }

    public boolean isSent() {
        return mIsSent;
    }

    public void setIsSent(boolean sent) {
        mIsSent = sent;
    }

    public static List<Trip> convertFromXee(List<com.xee.api.entity.Trip> xee) {
        List<Trip> list = new ArrayList<>();
        for (com.xee.api.entity.Trip tripXee : xee) {
            Trip trip = new Trip();
            trip.setId(tripXee.getId());
            trip.setPointBeginLatitude(tripXee.getBeginLocation().getLatitude());
            trip.setPointBeginLongitude(tripXee.getBeginLocation().getLongitude());
            trip.setPointBeginAltitude(tripXee.getBeginLocation().getAltitude());
            trip.setPointBeginHeading(tripXee.getBeginLocation().getHeading());
            trip.setPointBeginSatellites(tripXee.getBeginLocation().getSatellites());
            trip.setPointBeginDate(tripXee.getBeginLocation().getDate());
            trip.setPointEndLatitude(tripXee.getEndLocation().getLatitude());
            trip.setPointEndLongitude(tripXee.getEndLocation().getLongitude());
            trip.setPointEndAltitude(tripXee.getEndLocation().getAltitude());
            trip.setPointEndHeading(tripXee.getEndLocation().getHeading());
            trip.setPointEndSatellites(tripXee.getEndLocation().getSatellites());
            trip.setPointEndDate(tripXee.getEndLocation().getDate());
            trip.setBeginDate(tripXee.getBeginDate());
            trip.setEndDate(tripXee.getEndDate());
            trip.setCreationDate(tripXee.getCreationDate());
            trip.setLastUpdateDate(tripXee.getLastUpdateDate());
            list.add(trip);
        }
        return list;
    }
}
