package com.nsy209.nicedriver.model.objects;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Sébastien on 21/07/2017.
 */
@Entity(tableName = "Referentiels")
public class Referentiel {
    @PrimaryKey
    @ColumnInfo(name = "id")
    private int mId;
    @ColumnInfo(name = "name")
    private String mName;
    @ColumnInfo(name = "value")
    private float mValue;
    @ColumnInfo(name = "color")
    private String mColor;
    @ColumnInfo(name = "idMarker")
    private int mIdMarker;

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

    public float getValue() {
        return mValue;
    }

    public void setValue(float value) {
        mValue = value;
    }

    public String getColor() {
        return mColor;
    }

    public void setColor(String color) {
        mColor = color;
    }

    public int getIdMarker() {
        return mIdMarker;
    }

    public void setIdMarker(int idMarker) {
        mIdMarker = idMarker;
    }
}
