package com.nsy209.nicedriver.model.objects;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

/**
 * Created by Sébastien on 21/07/2017.
 */
@Entity(tableName = "Cars")
public class Car {
    @PrimaryKey
    @ColumnInfo(name = "id")
    private int mId;
    @ColumnInfo(name = "name")
    private String mName;
    @ColumnInfo(name = "make")
    private String mMake;
    @ColumnInfo(name = "model")
    private String mModel;
    @ColumnInfo(name = "year")
    private int mYear;
    @ColumnInfo(name = "numberPlate")
    private String mNumberPlate;
    @ColumnInfo(name = "deviceId")
    private String mDeviceId;
    @ColumnInfo(name = "cardbId")
    private int mCardbId;
    @ColumnInfo(name = "creationDate")
    private Date mCreationDate;
    @ColumnInfo(name = "lastUpdateDate")
    private Date mLastUpdateDate;
    @ColumnInfo(name = "userId")
    private int mUserId;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getMake() {
        return mMake;
    }

    public void setMake(String make) {
        mMake = make;
    }

    public String getModel() {
        return mModel;
    }

    public void setModel(String model) {
        mModel = model;
    }

    public int getYear() {
        return mYear;
    }

    public void setYear(int year) {
        mYear = year;
    }

    public String getNumberPlate() {
        return mNumberPlate;
    }

    public void setNumberPlate(String numberPlate) {
        mNumberPlate = numberPlate;
    }

    public String getDeviceId() {
        return mDeviceId;
    }

    public void setDeviceId(String deviceId) {
        mDeviceId = deviceId;
    }

    public int getCardbId() {
        return mCardbId;
    }

    public void setCardbId(int cardbId) {
        mCardbId = cardbId;
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

    public int getUserId() {
        return mUserId;
    }

    public void setUserId(int userId) {
        mUserId = userId;
    }
}
