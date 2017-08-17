package com.nsy209.nicedriver.model.objects;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Sébastien on 16/08/2017.
 */
@Entity(tableName = "Locations", primaryKeys = {"latitude", "longitude", "altitude", "heading", "satellites", "date"})
public class Location {
    @ColumnInfo(name = "latitude")
    @SerializedName("latitude")
    private double mLatitude;
    @ColumnInfo(name = "longitude")
    @SerializedName("longitude")
    private double mLongitude;
    @ColumnInfo(name = "altitude")
    @SerializedName("altitude")
    private double mAltitude;
    @ColumnInfo(name = "heading")
    @SerializedName("heading")
    private double mHeading;
    @ColumnInfo(name = "satellites")
    @SerializedName("satellites")
    private int mSatellites;
    @ColumnInfo(name = "date")
    @SerializedName("date")
    private Date mDate;

    public double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(double latitude) {
        mLatitude = latitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(double longitude) {
        mLongitude = longitude;
    }

    public double getAltitude() {
        return mAltitude;
    }

    public void setAltitude(double altitude) {
        mAltitude = altitude;
    }

    public double getHeading() {
        return mHeading;
    }

    public void setHeading(double heading) {
        mHeading = heading;
    }

    public int getSatellites() {
        return mSatellites;
    }

    public void setSatellites(int satellites) {
        mSatellites = satellites;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }


    public static List<Location> convertFromXee(List<com.xee.api.entity.Location> items) {
        List<Location> list = new ArrayList<>();
        for (com.xee.api.entity.Location s : items) {
            Location location = new Location();
            location.setLatitude(s.getLatitude());
            location.setLongitude(s.getLongitude());
            location.setAltitude(s.getAltitude());
            location.setHeading(s.getHeading());
            location.setSatellites(s.getSatellites());
            location.setDate(s.getDate());
            list.add(location);
        }
        return list;
    }
}
