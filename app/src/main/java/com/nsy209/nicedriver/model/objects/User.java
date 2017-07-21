package com.nsy209.nicedriver.model.objects;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

/**
 * Created by Sébastien on 21/07/2017.
 */
@Entity(tableName = "Users")
public class User {
    @PrimaryKey
    @ColumnInfo(name = "id")
    private int mId;
    @ColumnInfo(name = "role")
    private String mRole;
    @ColumnInfo(name = "firstName")
    private String mFirstName;
    @ColumnInfo(name = "lastName")
    private String mLastName;
    @ColumnInfo(name = "nickName")
    private String mNickName;
    @ColumnInfo(name = "gender")
    private String mGender;
    @ColumnInfo(name = "isLocationEnabled")
    private boolean mIsLocationEnabled;
    @ColumnInfo(name = "birthDate")
    private Date mBirthDate;
    @ColumnInfo(name = "licenseDeliveryDate")
    private Date mLicenseDeliveryDate;
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

    public String getRole() {
        return mRole;
    }

    public void setRole(String role) {
        mRole = role;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        mLastName = lastName;
    }

    public String getNickName() {
        return mNickName;
    }

    public void setNickName(String nickName) {
        mNickName = nickName;
    }

    public String getGender() {
        return mGender;
    }

    public void setGender(String gender) {
        mGender = gender;
    }

    public boolean isLocationEnabled() {
        return mIsLocationEnabled;
    }

    public void setIsLocationEnabled(boolean locationEnabled) {
        mIsLocationEnabled = locationEnabled;
    }

    public Date getBirthDate() {
        return mBirthDate;
    }

    public void setBirthDate(Date birthDate) {
        mBirthDate = birthDate;
    }

    public Date getLicenseDeliveryDate() {
        return mLicenseDeliveryDate;
    }

    public void setLicenseDeliveryDate(Date licenseDeliveryDate) {
        mLicenseDeliveryDate = licenseDeliveryDate;
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
