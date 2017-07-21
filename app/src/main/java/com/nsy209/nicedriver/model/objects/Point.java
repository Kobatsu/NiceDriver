package com.nsy209.nicedriver.model.objects;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Sébastien on 21/07/2017.
 */
@Entity(tableName = "Points")
public class Point {
    @PrimaryKey
    @ColumnInfo(name = "id")
    private long mId;
    @ColumnInfo(name = "latitude")
    private float mLatitude;
    @ColumnInfo(name = "longitude")
    private float mLongitude;
    @ColumnInfo(name = "name")
    private String mName;
    @ColumnInfo(name = "value")
    private String mValue;

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public float getLatitude() {
        return mLatitude;
    }

    public void setLatitude(float latitude) {
        mLatitude = latitude;
    }

    public float getLongitude() {
        return mLongitude;
    }

    public void setLongitude(float longitude) {
        mLongitude = longitude;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getValue() {
        return mValue;
    }

    public void setValue(String value) {
        mValue = value;
    }
}
