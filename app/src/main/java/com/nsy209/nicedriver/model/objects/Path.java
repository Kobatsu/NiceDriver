package com.nsy209.nicedriver.model.objects;

/**
 * Created by Sébastien on 13/07/2017.
 */

public class Path {
    private String mStartAdress;
    private String mStartDate;
    private String mEndAdress;
    private String mEndDate;
    private String mDistance;
    private String mTime;

    public Path(String mStartAdress, String mStartDate, String mEndAdress, String mEndDate, String mDistance, String mTime) {
        this.mStartAdress = mStartAdress;
        this.mStartDate = mStartDate;
        this.mEndAdress = mEndAdress;
        this.mEndDate = mEndDate;
        this.mDistance = mDistance;
        this.mTime = mTime;
    }

    public String getStartAdress() {
        return mStartAdress;
    }

    public void setStartAdress(String startAdress) {
        mStartAdress = startAdress;
    }

    public String getStartDate() {
        return mStartDate;
    }

    public void setStartDate(String startDate) {
        mStartDate = startDate;
    }

    public String getEndAdress() {
        return mEndAdress;
    }

    public void setEndAdress(String endAdress) {
        mEndAdress = endAdress;
    }

    public String getEndDate() {
        return mEndDate;
    }

    public void setEndDate(String endDate) {
        mEndDate = endDate;
    }

    public String getDistance() {
        return mDistance;
    }

    public void setDistance(String distance) {
        mDistance = distance;
    }

    public String getTime() {
        return mTime;
    }

    public void setTime(String time) {
        mTime = time;
    }

    @Override
    public String toString() {
        return "Path{" +
                "mStartAdress='" + mStartAdress + '\'' +
                ", mStartDate='" + mStartDate + '\'' +
                ", mEndAdress='" + mEndAdress + '\'' +
                ", mEndDate='" + mEndDate + '\'' +
                ", mDistance='" + mDistance + '\'' +
                ", mTime='" + mTime + '\'' +
                '}';
    }
}
