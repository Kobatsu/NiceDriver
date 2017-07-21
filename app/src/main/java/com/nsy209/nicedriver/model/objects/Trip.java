package com.nsy209.nicedriver.model.objects;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

/**
 * Created by Sébastien on 21/07/2017.
 */
@Entity(tableName = "Trips")
public class Trip {
    @PrimaryKey
    @ColumnInfo(name = "id")
    private int mId;
    @ColumnInfo(name = "idBeginLocation")
    private int mIdBeginLocation;
    @ColumnInfo(name = "idEndLocation")
    private int mIdEndLocation;
    @ColumnInfo(name = "startDate")
    private Date mStartDate;
    @ColumnInfo(name = "endDate")
    private Date mEndDate;
    @ColumnInfo(name = "stopDate")
    private Date mStopDate;
    @ColumnInfo(name = "creationDate")
    private Date mCreationDate;
    @ColumnInfo(name = "lastUpdateDate")
    private Date mLastUpdateDate;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public int getIdBeginLocation() {
        return mIdBeginLocation;
    }

    public void setIdBeginLocation(int idBeginLocation) {
        mIdBeginLocation = idBeginLocation;
    }

    public int getIdEndLocation() {
        return mIdEndLocation;
    }

    public void setIdEndLocation(int idEndLocation) {
        mIdEndLocation = idEndLocation;
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
}
